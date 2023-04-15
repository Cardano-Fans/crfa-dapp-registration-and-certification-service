package de.crfa.app.crfametadataservicereloaded;

import com.google.common.hash.Hashing;
import de.crfa.app.crfametadataservicereloaded.domain.ActionType;
import de.crfa.app.crfametadataservicereloaded.domain.DappRegistration;
import de.crfa.app.crfametadataservicereloaded.domain.MetadataUrl;
import de.crfa.app.crfametadataservicereloaded.repository.DappRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

@SpringBootApplication
@Slf4j
public class CrfaMetadataServiceReloadedApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrfaMetadataServiceReloadedApplication.class, args);
	}

    @Bean
    public CommandLineRunner demo(DappRegistrationRepository repository) {
        return (args) -> {

            repository.save(new DappRegistration(
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    List.of(MetadataUrl.of("https://test.com")),
                    ActionType.REGISTER,
                            "1.0.0",
                            "V1",
                    Hashing.sha256().hashString("1", UTF_8).toString(),
                    Hashing.sha256().hashString("2", UTF_8).toString(),
                    "Ed25519−EdDSA",
                    Hashing.sha256().hashString("3", UTF_8).toString()
            ));

            log.info("Example found with findAll():");
            log.info("-------------------------------");
            for (DappRegistration dappRegistration : repository.findAll()) {
                log.info(dappRegistration.toString());
            }
            log.info("");
        };
    }

}
