package de.crfa.app.crfametadataservicereloaded.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VerifiedSignature {

    @Id
    @Column(name = "signature_pub")
    private String signaturePub;

    @Column(name = "verified_by")
    private String verifiedBy;

    @ElementCollection(targetClass = OffchainVerificationMethod.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "offchain_verification_method", joinColumns = @JoinColumn(name = "signature_pub"))
    @Column(name = "verified_methods")
    @NotBlank
    private Collection<OffchainVerificationMethod> verifiedMethods;

}
