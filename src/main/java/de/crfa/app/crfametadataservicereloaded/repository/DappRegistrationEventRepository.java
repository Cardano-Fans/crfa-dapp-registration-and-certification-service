package de.crfa.app.crfametadataservicereloaded.repository;

import de.crfa.app.crfametadataservicereloaded.domain.OnchainDappRegistrationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DappRegistrationEventRepository  extends JpaRepository<OnchainDappRegistrationEvent, String> {



}
