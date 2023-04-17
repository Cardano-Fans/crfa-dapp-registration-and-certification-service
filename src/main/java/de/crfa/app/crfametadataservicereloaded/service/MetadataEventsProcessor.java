package de.crfa.app.crfametadataservicereloaded.service;


import com.bloxbean.cardano.yaci.store.metadata.domain.TxMetadataEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MetadataEventsProcessor {

    @EventListener
    public void onApplicationEvent(TxMetadataEvent event) {
        log.info("TxMetadataEvent: {}", event);
    }

}
