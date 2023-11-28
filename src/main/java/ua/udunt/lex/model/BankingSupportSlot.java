package ua.udunt.lex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.udunt.lex.config.MessageConfig;
import ua.udunt.lex.exception.IllegalSlotException;
import ua.udunt.lex.util.LibUtil;

import java.util.Map;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BankingSupportSlot extends AbstractSlot {

    private String bankName;
    private String supportPhone;

    public BankingSupportSlot(Map<String, LexEvent.Slot> slots) {
        this.bankName = Optional.ofNullable(slots.get("bankName"))
                .map(s -> s.getValue().getInterpretedValue())
                .orElse(null);
        this.supportPhone = MessageConfig.getInstance()
                .getMessage("BankingSupportIntent", "banks", bankName);

    }

    @Override
    public void validate() throws IllegalSlotException {
        if (LibUtil.isNullOrEmpty(bankName)) {
            throw new IllegalSlotException("bankName", "Bank name cannot be null");
        }
        if (LibUtil.isNullOrEmpty(supportPhone)) {
            throw new IllegalSlotException("bankName", "Unsupported bank name");
        }
    }

}
