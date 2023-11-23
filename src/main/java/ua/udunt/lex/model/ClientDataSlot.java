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
@EqualsAndHashCode(callSuper = false)
public class ClientDataSlot extends AbstractSlot {

    private String phone;

    public ClientDataSlot(Map<String, LexEvent.Slot> slots) {
        this.phone = Optional.ofNullable(slots.get("phone"))
                .map(s -> s.getValue().getOriginalValue())
                .orElse(null);
    }

    @Override
    public void validate() throws IllegalSlotException {
        if (LibUtil.isNullOrEmpty(phone)) {
            throw new IllegalSlotException("phone", "Phone cannot be null");
        }
        if (!phone.matches("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$")) {
            throw new IllegalSlotException("phone", "Invalid phone number");
        }
    }

}
