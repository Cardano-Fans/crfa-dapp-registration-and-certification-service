package de.crfa.app.crfametadataservicereloaded.repository;

import de.crfa.app.crfametadataservicereloaded.domain.Dapp;
import de.crfa.app.crfametadataservicereloaded.domain.DappId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DappRepository extends JpaRepository<Dapp, DappId> {

}
