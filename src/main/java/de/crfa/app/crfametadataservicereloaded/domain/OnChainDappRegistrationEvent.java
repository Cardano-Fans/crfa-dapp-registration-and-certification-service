package de.crfa.app.crfametadataservicereloaded.domain;


import de.crfa.app.crfametadataservicereloaded.utils.Patterns;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OnchainDappRegistrationEvent {

    @Id
    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "root_hash")
    @Pattern(regexp = Patterns.HEX)
    @NotBlank
    private String rootHash;

    @ElementCollection(targetClass = MetadataUrl.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "metadata_urls", joinColumns = @JoinColumn(name = "subject"))
    @Column(name = "metadata_url")
    private List<MetadataUrl> metadataUrls = new ArrayList<>();

    @NotNull
    @Column(name = "action_type")
    private ActionType actionType;

    @NotBlank
    @Column(name = "release_number")
    @Pattern(regexp = Patterns.RELEASE_NAME)
    private String releaseNumber;

    @Column(name = "release_name")
    private String releaseName;

    @Column(name = "signature_r")
    @NotBlank
    @Pattern(regexp = Patterns.HEX)
    private String signatureR;

    @Column(name = "signature_s")
    @NotBlank
    @Pattern(regexp = Patterns.HEX)
    private String signatureS;

    @Column(name = "signature_algo")
    @NotBlank
    @Pattern(regexp = "Ed25519−EdDSA")
    private String signatureAlgo;

    @Column(name = "signature_pub")
    @NotBlank
    @Pattern(regexp = Patterns.HEX)
    private String signaturePub;

}
