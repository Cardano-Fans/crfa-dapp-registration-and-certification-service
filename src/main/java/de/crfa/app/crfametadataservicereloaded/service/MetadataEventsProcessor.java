package de.crfa.app.crfametadataservicereloaded.service;


import com.bloxbean.cardano.yaci.store.metadata.domain.TxMetadataEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import de.crfa.app.crfametadataservicereloaded.domain.OnchainDappRegistrationEvent;
import de.crfa.app.crfametadataservicereloaded.repository.DappRegistrationEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Component
@Slf4j
public class MetadataEventsProcessor {

    @Autowired
    private DappRegistrationEventRepository dappRegistrationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("classpath:schema/cip72_dapp_registration_schema.json")
    private Resource dappRegistrationSchema;

    @EventListener
    public void onTxMetadataEvent(TxMetadataEvent event) {
        //log.info("TxMetadataEvent: {}", event);

        event.getTxMetadataList().forEach(txMetadataLabel -> {

            if (txMetadataLabel.getLabel().equalsIgnoreCase("674")) {
                log.info("Label:" + txMetadataLabel.getBody());
            }

            if (txMetadataLabel.getLabel().equalsIgnoreCase("1666")) {
                var body = txMetadataLabel.getBody();

                try {
                    if (validateSchema(body)) {
                        var dappRegistration = objectMapper.readValue(body, OnchainDappRegistrationEvent.class);

                        dappRegistrationRepository.save(dappRegistration);
                    } else {
                        log.warn("dapp doesn't conform to the schema!");
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private boolean validateSchema(String body) {
        try {
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);

            JsonSchema jsonSchema = factory.getSchema(
                    dappRegistrationSchema.getContentAsString(StandardCharsets.UTF_8));

            JsonNode jsonNode = objectMapper.readTree(body);

            Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);

            return errors.isEmpty();
        } catch (IOException e) {
            log.warn("Error parsing dApp body:{}", body);

            return false;
        }
    }

}
