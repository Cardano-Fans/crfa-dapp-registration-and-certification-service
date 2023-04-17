package de.crfa.app.crfametadataservicereloaded.repository;

import de.crfa.app.crfametadataservicereloaded.domain.VerifiedSignature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface VerifiedSignatureRepository extends JpaRepository<VerifiedSignature, String> {

}
