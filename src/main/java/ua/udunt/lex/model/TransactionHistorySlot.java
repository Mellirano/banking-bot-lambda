package ua.udunt.lex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.udunt.lex.exception.IllegalSlotException;
import ua.udunt.lex.util.LibUtil;
import ua.udunt.lex.util.PanUtil;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TransactionHistorySlot extends AbstractSlot {

    private String accountNumber;
    private String transactionDate;

    public TransactionHistorySlot(Map<String, LexEvent.Slot> slots) {
        this.accountNumber = Optional.ofNullable(slots.get("accountNumber"))
                .map(s -> s.getValue().getOriginalValue())
                .orElse(null);
        this.transactionDate = Optional.ofNullable(slots.get("transactionDate"))
                .map(s -> s.getValue().getInterpretedValue())
                .orElse(null);
    }

    @Override
    public void validate() throws IllegalSlotException {
        if (!PanUtil.isValidPan(accountNumber)) {
            throw new IllegalSlotException("accountNumber", "Invalid account number");
        }
        if (LibUtil.isNullOrEmpty(transactionDate)) {
            throw new IllegalSlotException("transactionDate", "Transaction date cannot be null");
        }
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(transactionDate);
        } catch (Exception e) {
            throw new IllegalSlotException("transactionDate", "Invalid transaction date");
        }
        if (localDate.isAfter(LocalDate.now())) {
            throw new IllegalSlotException("transactionDate", "Transaction date cannot be in future");
        }
    }

}
