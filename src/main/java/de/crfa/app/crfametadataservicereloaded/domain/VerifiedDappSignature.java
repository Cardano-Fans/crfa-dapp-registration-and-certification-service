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
    @Column(name = "signature")
    private String signature;

    @Column(name = "pub_key")
    private String pubKey;

    // we verify that blake2b signature is valid or not
    // this process is fully auttmated
    @Column(name = "automated_check_pass")
    private Boolean automatedCheckPass;

    // we checked that pub_key indeed belongs to the dApp claiming it
    @Column(name = "manual_check_pass")
    private Boolean manualCheckPass;

    @Column(name = "verified_by")
    private String verifiedBy;

    @ElementCollection(targetClass = OffChainVerificationMethod.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "offchain_verification_method", joinColumns = @JoinColumn(name = "signature_pub"))
    @Column(name = "verified_methods")
    @NotBlank
    private Collection<OffChainVerificationMethod> verifiedMethods;

}
