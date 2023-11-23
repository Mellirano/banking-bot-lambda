package ua.udunt.lex.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.udunt.lex.util.ObjectUtil;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeInfo {

    private String ccy;

    @JsonProperty("base_ccy")
    private String baseCcy;

    private String buy;

    private String sale;

    @Override
    public String toString() {
        return ObjectUtil.toJSON(this);
    }

}
