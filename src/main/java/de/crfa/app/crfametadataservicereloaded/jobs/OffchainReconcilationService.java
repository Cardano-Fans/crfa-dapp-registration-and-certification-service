package de.crfa.app.crfametadataservicereloaded.jobs;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class OffchainReconcilationService {

    @Scheduled(fixedDelayString = "10s")
    public void crawlOffchain() {
        log.info("The time is now {}", new Date());

        // using repository check where we failed
        // to query offchain data and
        // only for those that we failed
        // try to query again

        // question: how many times should we try to query a given dapp registration / version
        // before we stop


    }

}
