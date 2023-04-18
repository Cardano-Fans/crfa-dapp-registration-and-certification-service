package de.crfa.app.crfametadataservicereloaded.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Dapp {

    private OnChainDapp onChain;

    private OffChainDapp offChain;

}
