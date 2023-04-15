package de.crfa.app.crfametadataservicereloaded;

import de.crfa.app.crfametadataservicereloaded.domain.Example;
import de.crfa.app.crfametadataservicereloaded.repository.ExampleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class CrfaMetadataServiceReloadedApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrfaMetadataServiceReloadedApplication.class, args);
	}

    @Bean
    public CommandLineRunner demo(ExampleRepository repository) {
        return (args) -> {

            repository.save(new Example("Jack", "Bauer"));
            repository.save(new Example("Chloe", "O'Brian"));
            repository.save(new Example("Kim", "Bauer"));
            repository.save(new Example("David", "Palmer"));
            repository.save(new Example("Michelle", "Dessler"));

            log.info("Example found with findAll():");
            log.info("-------------------------------");
            for (Example example : repository.findAll()) {
                log.info(example.toString());
            }
            log.info("");

            Example example = repository.findById(1L);
            log.info("Example found with findById(1L):");
            log.info("--------------------------------");
            log.info(example.toString());
            log.info("");

            log.info("Example found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");

            repository.findByLastname("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });

            log.info("");
        };
    }

}
