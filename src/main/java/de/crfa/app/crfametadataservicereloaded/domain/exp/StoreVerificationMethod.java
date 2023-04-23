package de.crfa.app.crfametadataservicereloaded.domain.exp;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@ToString
public class StoreVerificationMethod {

    @NotBlank
    private Method method;

    public enum Method {
        DISCORD, TWITTER, PHONE
    }

}
