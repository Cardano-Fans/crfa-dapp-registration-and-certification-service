package de.crfa.app.crfametadataservicereloaded.repository;

import de.crfa.app.crfametadataservicereloaded.domain.OnChainDappRegistrationEvent;
import de.crfa.app.crfametadataservicereloaded.domain.OnChainDappRegistrationEventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DappRegistrationEventRepository  extends JpaRepository<OnChainDappRegistrationEvent, OnChainDappRegistrationEventId> {

}
