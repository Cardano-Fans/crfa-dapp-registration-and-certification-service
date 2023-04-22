package de.crfa.app.crfametadataservicereloaded.domain;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OnChainDappRegistrationEventId implements Serializable {

    private long slot;

    private String blockHash;

    private String subject;

}
