package de.crfa.app.crfametadataservicereloaded.repository;

import de.crfa.app.crfametadataservicereloaded.domain.DappRegistration;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DappRegistrationRepository extends CrudRepository<DappRegistration, String> {

    Optional<DappRegistration> findById(String subject);

}
