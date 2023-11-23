package ua.udunt.lex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.udunt.lex.exception.IllegalSlotException;
import ua.udunt.lex.util.PanUtil;

import java.util.Map;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CardBalanceSlot extends AbstractSlot {

    private String accountNumber;

    public CardBalanceSlot(Map<String, LexEvent.Slot> slots) {
        this.accountNumber = Optional.ofNullable(slots.get("accountNumber"))
                .map(s -> s.getValue().getOriginalValue())
                .orElse(null);
    }

    @Override
    public void validate() throws IllegalSlotException {
        if (!PanUtil.isValidPan(accountNumber)) {
            throw new IllegalSlotException("accountNumber", "Invalid account number");
        }
    }

}
