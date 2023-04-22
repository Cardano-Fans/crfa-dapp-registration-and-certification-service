package de.crfa.app.crfametadataservicereloaded;

import com.bloxbean.cardano.yaci.store.metadata.domain.TxMetadataLabel;
import com.bloxbean.cardano.yaci.store.metadata.storage.TxMetadataStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.setl.json.jackson.CanonicalFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableJpaRepositories("de.crfa.app.crfametadataservicereloaded.repository")
@EntityScan(basePackages = "de.crfa.app.crfametadataservicereloaded.domain")
@ComponentScan(basePackages = "de.crfa.app.crfametadataservicereloaded.service")
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@Slf4j
public class CrfaMetadataServiceReloadedApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrfaMetadataServiceReloadedApplication.class, args);
	}

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
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
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper(new CanonicalFactory());
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

