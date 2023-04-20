package de.crfa.app.crfametadataservicereloaded.repository;

import de.crfa.app.crfametadataservicereloaded.domain.OnChainDappRegistrationEventFailure;
import de.crfa.app.crfametadataservicereloaded.domain.OnChainDappRegistrationFailureEventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DappRegistrationEventFailureRepository extends JpaRepository<OnChainDappRegistrationEventFailure, OnChainDappRegistrationFailureEventId> {

}
