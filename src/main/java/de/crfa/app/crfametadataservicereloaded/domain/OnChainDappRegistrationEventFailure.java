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

    @Column(name = "failure_type")
    @NotNull
    private FailureType failureType;

    public enum FailureType {
        JSON_PARSING_ERROR, SCHEMA_ERROR
    }

}
