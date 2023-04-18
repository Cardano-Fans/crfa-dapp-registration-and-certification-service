package de.crfa.app.crfametadataservicereloaded;

import com.bloxbean.cardano.yaci.store.metadata.domain.TxMetadataLabel;
import com.bloxbean.cardano.yaci.store.metadata.storage.TxMetadataStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories("de.crfa.app.crfametadataservicereloaded.repository")
@EntityScan(basePackages = "de.crfa.app.crfametadataservicereloaded.domain")
@ComponentScan(basePackages = "de.crfa.app.crfametadataservicereloaded.service")
@EnableTransactionManagement
@Slf4j
public class CrfaMetadataServiceReloadedApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrfaMetadataServiceReloadedApplication.class, args);
	}

    // we are not interested in storing metadata by yaci-store
    @Bean
    public TxMetadataStorage txMetadataStorage() {
        return new TxMetadataStorage() {
            @Override
            public List<TxMetadataLabel> saveAll(List<TxMetadataLabel> txMetadataLabelList) {
                return List.of();
            }

            @Override
            public List<TxMetadataLabel> findByTxHash(String txHash) {
                return List.of();
            }
        };
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
            .connectTimeout(Duration.ofMinutes(1))
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();
    }

//    @Bean
//    public CommandLineRunner demo(DappRegistrationRepository repository) {
//        return (args) -> {
//
//            repository.save(new DappRegistration(
//                    UUID.randomUUID().toString(),
//                    UUID.randomUUID().toString(),
//                    List.of(MetadataUrl.of("https://test.com")),
//                    ActionType.REGISTER,
//                            "1.0.0",
//                            "V1",
//                    Hashing.sha256().hashString("1", UTF_8).toString(),
//                    Hashing.sha256().hashString("2", UTF_8).toString(),
//                    "Ed25519−EdDSA",
//                    Hashing.sha256().hashString("3", UTF_8).toString()
//            ));
//
//            log.info("Example found with findAll():");
//            log.info("-------------------------------");
//            for (DappRegistration dappRegistration : repository.findAll()) {
//                log.info(dappRegistration.toString());
//            }
//            log.info("");
//        };
//    }

}

