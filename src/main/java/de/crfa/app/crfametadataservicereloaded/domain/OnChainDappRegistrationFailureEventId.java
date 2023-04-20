package de.crfa.app.crfametadataservicereloaded.domain;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OnChainDappRegistrationFailureEventId implements Serializable {

    private long slot;

    private String blockHash;

}
