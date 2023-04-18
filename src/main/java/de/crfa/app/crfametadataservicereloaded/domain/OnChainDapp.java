package de.crfa.app.crfametadataservicereloaded.domain;

import de.crfa.app.crfametadataservicereloaded.utils.Patterns;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OnChainDapp {

    @Id
    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "release_version", nullable = false)
    @Pattern(regexp = Patterns.RELEASE_NAME)
    @NotBlank
    private String releaseVersion;

    // on chain dapp data

}
