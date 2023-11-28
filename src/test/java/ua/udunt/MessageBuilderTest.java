package ua.udunt;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import ua.udunt.lex.model.BankingSupportSlot;
import ua.udunt.lex.model.BinInfo;
import ua.udunt.lex.model.CardBalanceSlot;
import ua.udunt.lex.model.CheckAccountNumberSlot;
import ua.udunt.lex.model.ClientDataSlot;
import ua.udunt.lex.model.ExchangeInfo;
import ua.udunt.lex.model.ExchangeSlot;
import ua.udunt.lex.model.TransactionHistorySlot;
import ua.udunt.lex.service.BinInfoService;
import ua.udunt.lex.support.MessageBuilder;

@Slf4j
public class MessageBuilderTest {

    @Test
    public void exchangeSlotMessageTest() {
        ExchangeSlot exchangeSlot = ExchangeSlot.builder()
                .exchangeCurrency("USD")
                .exchangeType("Cash")
                .build();

        ExchangeInfo exchangeInfo = ExchangeInfo.builder()
                .ccy("USD")
                .buy("37.00")
                .sale("38.00")
                .build();

        log.info("Message: {}", MessageBuilder.build(exchangeSlot, exchangeInfo));
    }

    @Test
    public void clientDataSlotMessageTest() {
        ClientDataSlot clientDataSlot = ClientDataSlot.builder()
                .phone("+380964956232")
                .build();

        log.info("Message: {}", MessageBuilder.build(clientDataSlot));
    }

    @Test
    public void transactionHistorySlotMessageTest() {
        TransactionHistorySlot transactionHistorySlot = TransactionHistorySlot.builder()
                .accountNumber("4111111111111111")
                .transactionDate("21.12.2023")
                .build();

        log.info("Message: {}", MessageBuilder.build(transactionHistorySlot));
    }

    @Test
    public void cardBalanceSlotMessageTest() {
        CardBalanceSlot cardBalanceSlot = CardBalanceSlot.builder()
                .accountNumber("4111111111111111")
                .build();

        log.info("Message: {}", MessageBuilder.build(cardBalanceSlot));
    }

    @Test
    public void bankingSupportSlotMessageTest() {
        BankingSupportSlot bankingSupportSlot = BankingSupportSlot.builder()
                .bankName("PrivatBank")
                .supportPhone("3700")
                .build();
        log.info("Message: {}", MessageBuilder.build(bankingSupportSlot));
    }

    @Test
    public void checkAccountNumberSlotMessageTest() {
        String accountNumber = "5168752021610608";
        CheckAccountNumberSlot checkAccountNumberSlot = CheckAccountNumberSlot.builder()
                .accountNumber(accountNumber)
                .build();

        BinInfoService binInfoService = BinInfoService.getInstance();
        BinInfo binInfo = binInfoService.getBinInfo(accountNumber);
        log.info("Message: {}", MessageBuilder.build(checkAccountNumberSlot, binInfo));
    }

    @Test
    public void errorSlotMessageTest() {
        log.info("Message: {}", MessageBuilder.build("TransactionHistoryIntent", "transactionDate"));
    }

}
