package de.crfa.app.crfametadataservicereloaded.domain;

import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dapp {

    @EmbeddedId
    private DappId dappId;

}
