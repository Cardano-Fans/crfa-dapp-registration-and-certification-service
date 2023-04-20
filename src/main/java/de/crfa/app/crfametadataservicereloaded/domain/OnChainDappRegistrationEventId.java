package de.crfa.app.crfametadataservicereloaded.domain;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OnChainDappRegistrationEventId implements Serializable {

    private long slot;

    private String blockHash;

    private String subject;

}
