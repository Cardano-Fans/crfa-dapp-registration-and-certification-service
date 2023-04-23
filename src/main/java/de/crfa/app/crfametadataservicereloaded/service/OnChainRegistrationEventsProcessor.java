package de.crfa.app.crfametadataservicereloaded.service;


import com.bloxbean.cardano.yaci.store.metadata.domain.TxMetadataEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import de.crfa.app.crfametadataservicereloaded.domain.*;
import de.crfa.app.crfametadataservicereloaded.repository.DappRegistrationEventFailureRepository;
import de.crfa.app.crfametadataservicereloaded.repository.DappRegistrationEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static de.crfa.app.crfametadataservicereloaded.domain.MetadataUrl.MetadataType.HTTP_FAMILY;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Slf4j
public class OnChainRegistrationEventsProcessor {

    private static final String DAPP_REGISTRATION_METADATA_LABEL = "1666";

    @Autowired
    private DappRegistrationEventRepository dappRegistrationEventRepository;

    @Autowired
    private DappRegistrationEventFailureRepository dappRegistrationEventFailureRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OffchainMetadataProcessor offchainMetadataProcessor;

    @Value("classpath:schema/cip72_dapp_registration_schema.json")
    private Resource dappRegistrationSchema;

    @EventListener
    public void onTxMetadataEvent(TxMetadataEvent event) {
        //log.info("TxMetadataEvent: {}", event);

        event.getTxMetadataList().forEach(txMetadataLabel -> {

//            if (txMetadataLabel.getLabel().equalsIgnoreCase("674")) {
//                log.info("Label:" + txMetadataLabel.getBody());
//            }

            if (txMetadataLabel.getLabel().equalsIgnoreCase(DAPP_REGISTRATION_METADATA_LABEL)) {
                var body = txMetadataLabel.getBody();

                try {
                    String blockHash = event.getEventMetadata().getBlockHash();
                    long slot = event.getEventMetadata().getSlot();
                    JsonNode root = objectMapper.readTree(body);

                    if (validateSchema(body)) {
                        String subject = root.get("subject").asText();

                        var onChainDappRegistrationEvent = new OnChainDappRegistrationEvent();
                        var id = new OnChainDappRegistrationEventId(slot, blockHash, subject);

                        onChainDappRegistrationEvent.setId(id);
                        onChainDappRegistrationEvent.setRootHash(root.get("rootHash").asText());

                        JsonNode typeNode = root.get("type");

                        onChainDappRegistrationEvent.setActionType(ActionType.valueOf(typeNode.get("action").asText()));

                        if (typeNode.has("releaseNumber")) {
                            onChainDappRegistrationEvent.setReleaseNumber(typeNode.get("releaseNumber").asText());
                        }
                        if (typeNode.has("releaseName")) {
                            onChainDappRegistrationEvent.setReleaseName(typeNode.get("releaseName").asText());
                        }

                        JsonNode signature = root.get("signature");

                        onChainDappRegistrationEvent.setSignatureS(signature.get("s").asText());
                        onChainDappRegistrationEvent.setSignatureR(signature.get("r").asText());
                        onChainDappRegistrationEvent.setSignaturePub(signature.get("pub").asText());
                        onChainDappRegistrationEvent.setMetadataUrls(convertUrls(root));

                        dappRegistrationEventRepository.saveAndFlush(onChainDappRegistrationEvent);

                        // fire up async off-chain crawler
                        offchainMetadataProcessor.process(onChainDappRegistrationEvent);
                    } else {
                        var id = new OnChainDappRegistrationFailureEventId(slot, blockHash);
                        var onChainDappRegistrationFailureEvent = new OnChainDappRegistrationEventFailure();
                        onChainDappRegistrationFailureEvent.setId(id);
                        onChainDappRegistrationFailureEvent.setBody(body);

                        dappRegistrationEventFailureRepository.saveAndFlush(onChainDappRegistrationFailureEvent);

                        log.warn("Dapp registration doesn't conform to the schema, logging failure, entry: {}", onChainDappRegistrationFailureEvent);
                    }
                } catch (JsonProcessingException e) {
                    log.error("JSON processing error", e);
                }
            }
        });
    }

    private boolean validateSchema(String body) {
        try {
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);

            JsonSchema jsonSchema = factory.getSchema(
                    dappRegistrationSchema.getContentAsString(UTF_8));

            JsonNode jsonNode = objectMapper.readTree(body);

            Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);

            return errors.isEmpty();
        } catch (IOException e) {
            log.warn("Error parsing dApp body:{}", body);

            return false;
        }
    }

    private List<MetadataUrl> convertUrls(JsonNode root) {
        ArrayNode metadataNode = (ArrayNode) root.get("metadata");

        List<MetadataUrl> urls = new ArrayList<>();

        if (detectArrayItem(metadataNode)) {
            StringBuilder sb = new StringBuilder();
            for (JsonNode urlPartJsonNode : metadataNode) {
                String urlPart = urlPartJsonNode.asText();
                sb.append(urlPart);
            }
            String fullUrl = sb.toString();
            if (isHttpAlike(fullUrl)) {
                urls.add(MetadataUrl.of(fullUrl, HTTP_FAMILY));
            }
        }
        if (detectStringItem(metadataNode)) {
            for (JsonNode urlNode : metadataNode) {
                String url = urlNode.asText();
                if (isHttpAlike(url)) {
                    urls.add(MetadataUrl.of(url, HTTP_FAMILY));
                }
            }
        }

        return urls;
    }

    private static boolean isHttpAlike(String fullUrl) {
        return fullUrl.startsWith("http") || fullUrl.startsWith("https");
    }

    private boolean detectArrayItem(ArrayNode metadataNode) {
        if (metadataNode.isEmpty()) {
            return false;
        }

        return metadataNode.iterator().next().isArray();
    }

    private boolean detectStringItem(ArrayNode metadataNode) {
        if (metadataNode.isEmpty()) {
            return false;
        }

        return metadataNode.iterator().next().isTextual();
    }

}
