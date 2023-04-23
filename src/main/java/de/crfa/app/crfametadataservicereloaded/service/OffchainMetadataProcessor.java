package de.crfa.app.crfametadataservicereloaded.service;

import com.bloxbean.cardano.client.crypto.Blake2bUtil;
import com.bloxbean.cardano.client.util.HexUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.crfa.app.crfametadataservicereloaded.domain.MetadataUrl;
import de.crfa.app.crfametadataservicereloaded.domain.OnChainDappRegistrationEvent;
import de.crfa.app.crfametadataservicereloaded.domain.RawOffchainDApp;
import de.crfa.app.crfametadataservicereloaded.repository.RawOffchainDAppRepository;
import io.setl.json.jackson.CanonicalFactory;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Slf4j
public class OffchainMetadataProcessor {

    @Autowired
    private OffChainDataCrawler offChainDataCrawler;

    @Autowired
    private RawOffchainDAppRepository rawOffchainDAppRepository;
    private ObjectMapper canonicalObjectMapper;

    @PostConstruct
    public void init() {
        this.canonicalObjectMapper = canonicalMapper();
    }

    @Async("threadPoolTaskExecutor")
    public void process(OnChainDappRegistrationEvent onChainDappRegistrationEvent) {

        for (MetadataUrl metadataUrl : onChainDappRegistrationEvent.getMetadataUrls()) {
            if (metadataUrl.getType() == MetadataUrl.MetadataType.HTTP_FAMILY) {
                try {
                    RawOffchainDApp rawOffchainDApp = new RawOffchainDApp();

                    String body = offChainDataCrawler.crawl(metadataUrl.getUrl());

                    String canonicalJson = canonicalObjectMapper.writeValueAsString(canonicalObjectMapper.readTree(body));

                    byte[] blakeHash = Blake2bUtil.blake2bHash256(canonicalJson.getBytes(UTF_8));
                    String blakeHashHex = HexUtil.encodeHexString(blakeHash);

                    boolean hashMatch = blakeHashHex.equalsIgnoreCase(onChainDappRegistrationEvent.getRootHash());

                    rawOffchainDApp.setSubject(onChainDappRegistrationEvent.getId().getSubject());
                    rawOffchainDApp.setMetadataUrl(metadataUrl.getUrl());
                    rawOffchainDApp.setRootHashMatch(hashMatch);
                    rawOffchainDApp.setBody(body);
                    rawOffchainDApp.setCanonicalBody(canonicalJson);

                    rawOffchainDAppRepository.save(rawOffchainDApp);
                } catch (Exception e) {
                    log.warn("Crawling error, url:{}", metadataUrl.getUrl(), e);
                }
            }
        }
    }

    private ObjectMapper canonicalMapper() {
        return new ObjectMapper(new CanonicalFactory());
    }

}
