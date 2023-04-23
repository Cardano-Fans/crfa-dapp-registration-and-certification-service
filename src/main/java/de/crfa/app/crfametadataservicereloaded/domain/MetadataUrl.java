package de.crfa.app.crfametadataservicereloaded.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class MetadataUrl {

    @NotBlank
    @Column(name = "url")
    private String url;

    @NotBlank
    @Column(name = "type")
    private MetadataType type;

    public enum MetadataType {
        HTTP_FAMILY, IPFS_FAMILY
    }

}
