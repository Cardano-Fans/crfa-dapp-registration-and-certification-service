package de.crfa.app.crfametadataservicereloaded.service;

import de.crfa.app.crfametadataservicereloaded.domain.OnChainDappRegistrationEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OffchainMetadataProcessor {

    @Async("threadPoolTaskExecutor")
    public void process(OnChainDappRegistrationEvent onChainDappRegistrationEvent) {

    }

}
