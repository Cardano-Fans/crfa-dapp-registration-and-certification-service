package de.crfa.app.crfametadataservicereloaded.service;


import com.bloxbean.cardano.yaci.store.metadata.domain.TxMetadataEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import de.crfa.app.crfametadataservicereloaded.domain.DappRegistration;
import de.crfa.app.crfametadataservicereloaded.repository.DappRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;

@Component
@Slf4j
public class MetadataEventsProcessor {

    @Autowired
    private DappRegistrationRepository dappRegistrationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("classpath:schema/cip72_dapp_registration_schema.json")
    private Resource dappRegistrationSchema;

    @EventListener
    public void onTxMetadataEvent(TxMetadataEvent event) {
        log.info("TxMetadataEvent: {}", event);

        event.getTxMetadataList().forEach(txMetadataLabel -> {

            if (txMetadataLabel.getLabel().equalsIgnoreCase("1666")) {
                var body = txMetadataLabel.getBody();

                try {
                    if (validateSchema(body)) {
                        var dappRegistration = objectMapper.readValue(body, DappRegistration.class);

                        dappRegistrationRepository.save(dappRegistration);
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
                    dappRegistrationSchema.getContentAsString(Charset.defaultCharset()));

            JsonNode jsonNode = objectMapper.readTree(body);

            Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);

            return errors.isEmpty();
        } catch (IOException e) {
            log.warn("Error parsing dApp body:{}", body);

            return false;
        }
    }

}
