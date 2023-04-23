package de.crfa.app.crfametadataservicereloaded.domain;


import de.crfa.app.crfametadataservicereloaded.utils.Patterns;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OnChainDappRegistrationEvent extends AbstractTimestampEntity {

    @EmbeddedId
    private OnChainDappRegistrationEventId id;

    @Column(name = "root_hash")
    @Pattern(regexp = Patterns.HEX_32_64)
    @NotBlank
    private String rootHash;

    @ElementCollection(targetClass = MetadataUrl.class, fetch = EAGER)
    @CollectionTable(name = "metadata_urls", joinColumns = { @JoinColumn(name = "slot"), @JoinColumn(name = "block_hash"), @JoinColumn(name = "subject") } )
    @Column(name = "metadata_url")
    private List<MetadataUrl> metadataUrls = new ArrayList<>();

    @NotNull
    @Column(name = "action_type")
    private ActionType actionType;

    @Column(name = "release_number")
    @Pattern(regexp = Patterns.RELEASE_VERSION)
    @Nullable
    private String releaseNumber;

    @Column(name = "release_name")
    @Nullable
    private String releaseName;

    @Column(name = "signature_r")
    @NotBlank
    @Pattern(regexp = Patterns.HEX_32_64)
    private String signatureR;

    @Column(name = "signature_s")
    @NotBlank
    @Pattern(regexp = Patterns.HEX_32_64)
    private String signatureS;

    @Column(name = "signature_algo")
    @NotBlank
    @Pattern(regexp = "Ed25519−EdDSA")
    private String signatureAlgo;

    @Column(name = "signature_pub")
    @NotBlank
    @Pattern(regexp = Patterns.HEX_32_64)
    private String signaturePub;

}
