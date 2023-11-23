package ua.udunt.lex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.udunt.lex.exception.IllegalSlotException;
import ua.udunt.lex.util.LibUtil;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TransactionHistorySlot extends AbstractSlot {

    private String transactionDate;

    public TransactionHistorySlot(Map<String, LexEvent.Slot> slots) {
        this.transactionDate = Optional.ofNullable(slots.get("transactionDate"))
                .map(s -> s.getValue().getOriginalValue())
                .orElse(null);
    }

    @Override
    public void validate() throws IllegalSlotException {
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
