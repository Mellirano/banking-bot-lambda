package ua.udunt.lex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.udunt.lex.exception.IllegalSlotException;
import ua.udunt.lex.util.LibUtil;

import java.util.Map;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExchangeSlot extends AbstractSlot {

    private String exchangeType;
    private String exchangeCurrency;
    private String exchangeId;

    public ExchangeSlot(Map<String, LexEvent.Slot> slots) {
        this.exchangeType = Optional.ofNullable(slots.get("exchangeType"))
                .map(s -> s.getValue().getInterpretedValue())
                .orElse(null);
        this.exchangeId = Optional.ofNullable(exchangeType)
                .map(t -> "cash".equalsIgnoreCase(t) ? "5" : "11")
                .orElse(null);
        this.exchangeCurrency = Optional.ofNullable(slots.get("exchangeCurrency"))
                .map(s -> s.getValue().getInterpretedValue())
                .orElse(null);
    }

    @Override
    public void validate() throws IllegalSlotException {
        if (LibUtil.isNullOrEmpty(exchangeType)) {
            throw new IllegalSlotException("exchangeType", "ExchangeType cannot be null");
        }
        if (LibUtil.isNullOrEmptyOneOf(exchangeCurrency)) {
            throw new IllegalSlotException("exchangeCurrency", "ExchangeCurrency cannot be null");
        }
        if (!exchangeType.matches("[nN]on-cash|[cC]ash")) {
            throw new IllegalSlotException("exchangeType", "Unsupported exchangeType");
        }
        if (!exchangeCurrency.matches("[eE][uU][rR]|[uU][sS][dD]")) {
            throw new IllegalSlotException("exchangeCurrency", "Unsupported exchangeCurrency");
        }
    }

}
