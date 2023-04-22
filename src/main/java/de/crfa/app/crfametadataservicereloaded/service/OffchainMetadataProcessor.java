package de.crfa.app.crfametadataservicereloaded.service;

import com.bloxbean.cardano.client.crypto.Blake2bUtil;
import com.bloxbean.cardano.client.util.HexUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.crfa.app.crfametadataservicereloaded.domain.MetadataUrl;
import de.crfa.app.crfametadataservicereloaded.domain.OnChainDappRegistrationEvent;
import de.crfa.app.crfametadataservicereloaded.domain.RawOffchainDApp;
import de.crfa.app.crfametadataservicereloaded.repository.RawOffchainDAppRepository;
import io.setl.json.jackson.CanonicalFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class OffchainMetadataProcessor {

    @Autowired
    private OffChainDataCrawler offChainDataCrawler;

    @Autowired
    private RawOffchainDAppRepository rawOffchainDAppRepository;

    @Async("threadPoolTaskExecutor")
    public void process(OnChainDappRegistrationEvent onChainDappRegistrationEvent) {
        for (MetadataUrl metadataUrl : onChainDappRegistrationEvent.getMetadataUrls()) {
            if (metadataUrl.getType() == MetadataUrl.MetadataType.HTTP) {
                try {
                    RawOffchainDApp rawOffchainDApp = new RawOffchainDApp();
                    rawOffchainDApp.setSubject(onChainDappRegistrationEvent.getId().getSubject());
                    rawOffchainDApp.setMetadataUrl(metadataUrl.getUrl());

                    String body = offChainDataCrawler.crawl(metadataUrl.getUrl());

                    rawOffchainDApp.setBody(body);

                    ObjectMapper objectMapper = new ObjectMapper(new CanonicalFactory());
                    String canonicalJson = objectMapper.writeValueAsString(objectMapper.readTree(body));
                    rawOffchainDApp.setCanonicalBody(canonicalJson);

                    byte[] blakeHash = Blake2bUtil.blake2bHash256(canonicalJson.getBytes(StandardCharsets.UTF_8));
                    String blakeHashHex = HexUtil.encodeHexString(blakeHash);

                    boolean hashMatch = blakeHashHex.equalsIgnoreCase(onChainDappRegistrationEvent.getRootHash());
                    rawOffchainDApp.setRootHashMatch(hashMatch);

                    rawOffchainDAppRepository.save(rawOffchainDApp);
                } catch (Exception e) {
                    log.warn("Crawling error, url:{}", metadataUrl.getUrl(), e);
                }
            }
        }
    }

}
