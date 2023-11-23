package ua.udunt.lex.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class IllegalSlotException extends Exception {

    private String slotName;

    public IllegalSlotException(String slotName, String message) {
        super(message);
        this.slotName = slotName;
    }

}
