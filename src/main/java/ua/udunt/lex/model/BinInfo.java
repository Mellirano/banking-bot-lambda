package ua.udunt.lex.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.udunt.lex.util.ObjectUtil;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class BinInfo implements Serializable {

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Scheme")
    private String scheme;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Issuer")
    private String issuer;

    @JsonProperty("CardTier")
    private String cardTier;

    @JsonProperty("Luhn")
    private Boolean isLuhn;

    @JsonProperty("Country")
    private Country country;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static class Country implements Serializable {

        @JsonProperty("A2")
        private String a2;

        @JsonProperty("A3")
        private String a3;

        @JsonProperty("N3")
        private String n3;

        @JsonProperty("ISD")
        private String isd;

        @JsonProperty("Name")
        private String name;

        @JsonProperty("Count")
        private String cont;

    }

    @Override
    public String toString() {
        return ObjectUtil.toJSON(this);
    }

}
