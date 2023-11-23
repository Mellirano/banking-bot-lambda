package ua.udunt.lex.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import ua.udunt.lex.model.ExchangeInfo;
import ua.udunt.lex.model.ExchangeSlot;
import ua.udunt.lex.model.LexEvent;
import ua.udunt.lex.model.LexResponse;
import ua.udunt.lex.service.ExchangeInfoService;
import ua.udunt.lex.support.LexResponseBuilder;
import ua.udunt.lex.support.MessageBuilder;
import ua.udunt.lex.util.LibUtil;

import java.util.Map;

@Slf4j
public class LexIntentHandler implements RequestHandler<LexEvent, LexResponse> {

    //https://github.com/aws-samples/amazon-lex-v2-lambdahook-for-booktripbot/blob/main/lambda_function.py

    private final ExchangeInfoService exchangeInfoService = ExchangeInfoService.getInstance();

    @Override
    public LexResponse handleRequest(LexEvent event, Context context) {
        try {
            log.info("LEX EVENT : >> {}", event);
            LexEvent.Intent currentIntent = event.getSessionState().getIntent();
            String intentName = currentIntent.getName();
            if (!LibUtil.isNullOrEmpty(intentName)) {
                LexResponse response = null;
                switch (intentName) {
                    case "CheckAccountNumberIntent" -> {
                        //TODO In progress (bin info)
                    }
                    case "BankingSupportIntent" -> {
                        //TODO In progress (support number and hours) +
                    }
                    case "ClientDataIntent" -> {
                        //TODO In progress (return random data about client by phone number) +
                    }
                    case "TransactionHistoryIntent" -> {
                        //TODO In progress (generate random transaction history for date) (datafaker) +
                    }
                    case "CardBalanceIntent" -> {
                        //TODO In progress (return random balance for card) +
                    }
                    case "ProcessCurrentExchangeIntent" -> {
                        response = getExchangeRate(event);
                    }
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

    private LexResponse getExchangeRate(LexEvent event) {
        try {
            LexEvent.Intent currentIntent = event.getSessionState().getIntent();
            Map<String, LexEvent.Slot> slots = currentIntent.getSlots();
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
        } catch (Exception e) {
            log.error("", e);
            //TODO Validation processing (elicit)
        }
        return LexResponseBuilder.closeError(event);
    }

//    private LexResponse getClientData(LexEvent event) {
//
//    }

}
