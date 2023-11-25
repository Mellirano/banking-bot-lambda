package ua.udunt.lex.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import ua.udunt.lex.exception.IllegalSlotException;
import ua.udunt.lex.model.BankingSupportSlot;
import ua.udunt.lex.model.BinInfo;
import ua.udunt.lex.model.CardBalanceSlot;
import ua.udunt.lex.model.CheckAccountNumberSlot;
import ua.udunt.lex.model.ClientDataSlot;
import ua.udunt.lex.model.ExchangeInfo;
import ua.udunt.lex.model.ExchangeSlot;
import ua.udunt.lex.model.LexEvent;
import ua.udunt.lex.support.LexProcessor;
import ua.udunt.lex.model.LexResponse;
import ua.udunt.lex.model.TransactionHistorySlot;
import ua.udunt.lex.service.BinInfoService;
import ua.udunt.lex.service.ExchangeInfoService;
import ua.udunt.lex.support.LexResponseBuilder;
import ua.udunt.lex.support.MessageBuilder;
import ua.udunt.lex.util.LibUtil;

import java.util.Map;

@Slf4j
public class LexIntentHandler implements RequestHandler<LexEvent, LexResponse> {

    private final ExchangeInfoService exchangeInfoService = ExchangeInfoService.getInstance();
    private final BinInfoService binInfoService = BinInfoService.getInstance();

    @Override
    public LexResponse handleRequest(LexEvent event, Context context) {
        try {
            log.info("LEX EVENT : >> {}", event);
            LexEvent.Intent currentIntent = event.getSessionState().getIntent();
            String intentName = currentIntent.getName();
            if (!LibUtil.isNullOrEmpty(intentName)) {
                LexResponse response = null;
                switch (intentName) {
                    case "CheckAccountNumberIntent" -> response = checkAccountNumber(event);
                    case "BankingSupportIntent" -> response = getBankingSupport(event);
                    case "ClientDataIntent" -> response = getClientData(event);
                    case "TransactionHistoryIntent" -> response = getTransactionHistory(event);
                    case "CardBalanceIntent" -> response = getCardBalance(event);
                    case "ProcessCurrentExchangeIntent" -> response = getExchangeRate(event);
                    default -> {
                    }
                }
                if (!LibUtil.isNullOrEmpty(response)) {
                    log.info("LEX RESPONSE : << {}", response);
                    return response;
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return LexResponseBuilder.closeError(event);
    }

    private LexResponse checkAccountNumber(LexEvent event) {
        return processLexEvent(event, slots -> {
            CheckAccountNumberSlot checkAccountNumberSlot = new CheckAccountNumberSlot(slots);
            checkAccountNumberSlot.validate();
            BinInfo binInfo = binInfoService.getBinInfo(checkAccountNumberSlot.getAccountNumber());
            if (!LibUtil.isNullOrEmpty(binInfo)) {
                return LexResponseBuilder.closeSuccess(event,
                        "Fulfilled",
                        MessageBuilder.build(checkAccountNumberSlot, binInfo));
            } else {
                log.info("Cannot obtain bin info");
            }
            return LexResponseBuilder.closeError(event);
        });
    }

    private LexResponse getBankingSupport(LexEvent event) {
        return processLexEvent(event, slots -> {
            BankingSupportSlot bankingSupportSlot = new BankingSupportSlot(slots);
            bankingSupportSlot.validate();
            return LexResponseBuilder.closeSuccess(event,
                    "Fulfilled",
                    MessageBuilder.build(bankingSupportSlot));
        });
    }

    private LexResponse getClientData(LexEvent event) {
        return processLexEvent(event, slots -> {
            ClientDataSlot clientDataSlot = new ClientDataSlot(slots);
            clientDataSlot.validate();
            return LexResponseBuilder.closeSuccess(event,
                    "Fulfilled",
                    MessageBuilder.build(clientDataSlot));
        });
    }

    private LexResponse getTransactionHistory(LexEvent event) {
        return processLexEvent(event, slots -> {
            TransactionHistorySlot transactionHistorySlot = new TransactionHistorySlot(slots);
            transactionHistorySlot.validate();
            return LexResponseBuilder.closeSuccess(event,
                    "Fulfilled",
                    MessageBuilder.build(transactionHistorySlot));
        });
    }

    private LexResponse getCardBalance(LexEvent event) {
        return processLexEvent(event, slots -> {
            CardBalanceSlot cardBalanceSlot = new CardBalanceSlot(slots);
            cardBalanceSlot.validate();
            return LexResponseBuilder.closeSuccess(event,
                    "Fulfilled",
                    MessageBuilder.build(cardBalanceSlot));
        });
    }

    private LexResponse getExchangeRate(LexEvent event) {
        return processLexEvent(event, slots -> {
            ExchangeSlot exchangeSlot = new ExchangeSlot(slots);
            exchangeSlot.validate();
            ExchangeInfo exchangeInfo = exchangeInfoService.getExchange(exchangeSlot);
            if (!LibUtil.isNullOrEmpty(exchangeInfo)) {
                return LexResponseBuilder.closeSuccess(event,
                        "Fulfilled",
                        MessageBuilder.build(exchangeSlot, exchangeInfo));
            } else {
                log.info("Cannot obtain exchange info");
            }
            return LexResponseBuilder.closeError(event);
        });
    }

    private LexResponse processLexEvent(LexEvent event, LexProcessor<Map<String, LexEvent.Slot>, LexResponse> processor) {
        try {
            LexEvent.Intent currentIntent = event.getSessionState().getIntent();
            Map<String, LexEvent.Slot> slots = currentIntent.getSlots();
            return processor.process(slots);
        } catch (IllegalSlotException es) {
            log.error("{}", es.getMessage());
            return LexResponseBuilder.elicit(event, es);
        } catch (Exception e) {
            log.error("", e);
        }
        return LexResponseBuilder.closeError(event);
    }

}
