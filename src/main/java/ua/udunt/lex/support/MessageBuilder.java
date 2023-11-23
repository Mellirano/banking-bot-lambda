package ua.udunt.lex.support;

import net.datafaker.Faker;
import ua.udunt.lex.config.MessageConfig;
import ua.udunt.lex.faker.LexFaker;
import ua.udunt.lex.model.BankingSupportSlot;
import ua.udunt.lex.model.CardBalanceSlot;
import ua.udunt.lex.model.ClientDataSlot;
import ua.udunt.lex.model.ExchangeInfo;
import ua.udunt.lex.model.ExchangeSlot;
import ua.udunt.lex.model.TransactionHistorySlot;
import ua.udunt.lex.util.LibUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

public class MessageBuilder {

    private static final MessageConfig MESSAGE_CONFIG = MessageConfig.getInstance();

    public static String build(ExchangeSlot slot, ExchangeInfo exchangeInfo) {
        String message = MESSAGE_CONFIG.getMessage("ProcessCurrentExchangeIntent", "message");
        if (!LibUtil.isNullOrEmpty(message)) {
            return message
                    .replaceFirst("\\$EXCHANGE_TYPE", slot.getExchangeType().toLowerCase())
                    .replaceFirst("\\$EXCHANGE_CURRENCY", slot.getExchangeCurrency())
                    .replaceFirst("\\$PURCHASE", exchangeInfo.getBuy())
                    .replaceFirst("\\$SALE", exchangeInfo.getSale());
        }
        return null;
    }

    public static String build(ClientDataSlot slot) {
        String message = MESSAGE_CONFIG.getMessage("ClientDataIntent", "message");
        if (!LibUtil.isNullOrEmpty(message)) {
            LexFaker faker = new LexFaker();
            Date currentDate = new Date();
            Date initialDate = Date.from(LocalDateTime.now()
                    .minusYears(5)
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            String sex = faker.demographic().sex();
            return message
                    .replaceFirst("\\$FULL_NAME", faker.ukranian().fullName(sex))
                    .replaceFirst("\\$ADDRESS", faker.ukranian().address())
                    .replaceFirst("\\$DATE_BIRTH", formatter.format(faker.date()
                            .birthday()))
                    .replaceFirst("\\$SEX", sex)
                    .replaceFirst("\\$PHONE", slot.getPhone())
                    .replaceFirst("\\$SSN", faker.idNumber().ssnValid())
                    .replaceFirst("\\$BANK_DATE", formatter.format(faker.date()
                            .between(initialDate, currentDate)));
        }
        return null;
    }

    public static String build(TransactionHistorySlot slot) {
        String title = MESSAGE_CONFIG.getMessage("TransactionHistoryIntent", "title");
        String message = MESSAGE_CONFIG.getMessage("TransactionHistoryIntent", "message");
        if (!LibUtil.isNullOrEmptyOneOf(title, message)) {
            LexFaker faker = new LexFaker();
            title = title.replaceFirst("\\$TRANSACTION_DATE", slot.getTransactionDate());
            StringBuilder finalMessage = new StringBuilder(title);
            int transactionCount = faker.number().numberBetween(1, 15);
            for (int i = 0; i < transactionCount; i++) {
                finalMessage.append("\n")
                        .append(message
                                .replaceFirst("\\$MERCHANT", faker.ukranian().merchant())
                                .replaceFirst("\\$AMOUNT", faker.commerce().price())
                                .replaceFirst("\\$CURRENCY", faker.expression("#{regexify '(UAH|USD|EUR)'}")));
            }
            return finalMessage.toString();
        }
        return null;
    }

    public static String build(CardBalanceSlot slot) {
        String message = MESSAGE_CONFIG.getMessage("CardBalanceIntent", "message");
        if (!LibUtil.isNullOrEmpty(message)) {
            Faker faker = new Faker();
            return message
                    .replaceFirst("\\$ACCOUNT_NUMBER", slot.getAccountNumber())
                    .replaceFirst("\\$AMOUNT", faker.commerce().price())
                    .replaceFirst("\\$CURRENCY", faker.expression("#{regexify '(UAH|USD|EUR)'}"));
        }
        return null;
    }

    public static String build(BankingSupportSlot slot) {
        String message = MESSAGE_CONFIG.getMessage("BankingSupportIntent", "message");
        if (!LibUtil.isNullOrEmpty(message)) {
            Faker faker = new Faker();
            return message
                    .replaceFirst("\\$BANK", slot.getBankName())
                    .replaceFirst("\\$SUPPORT_HOURS", faker.expression("#{regexify '(8/00|9/00|10/00)'}"))
                    .replaceFirst("\\$PHONE", slot.getSupportPhone());
        }
        return null;
    }

    //TODO Finalize

    //    public static String build(CheckAccountNumberSlot slot) {
//
//    }

    public static String build(String intentName, String errorSlot) {
        if (!LibUtil.isNullOrEmptyOneOf(intentName, errorSlot)) {
            return MESSAGE_CONFIG.getMessage(intentName, "invalid"
                    + Pattern.compile("^.")
                    .matcher(errorSlot)
                    .replaceFirst(m -> m.group()
                            .toUpperCase()));
        }
        return null;
    }

}
