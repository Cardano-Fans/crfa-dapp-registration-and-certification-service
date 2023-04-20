package de.crfa.app.crfametadataservicereloaded.domain;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OnChainDappRegistrationEventFailure {

    @EmbeddedId
    private OnChainDappRegistrationFailureEventId id;

    @Column(name = "body")
    @NotNull
    private String body;

}
