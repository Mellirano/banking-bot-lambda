package ua.udunt.lex.support;

import ua.udunt.lex.exception.IllegalSlotException;
import ua.udunt.lex.model.LexEvent;
import ua.udunt.lex.model.LexResponse;

public class LexResponseBuilder {

    public static LexResponse close(LexEvent event, String state, LexResponse.Message[] messages) {
        LexEvent.SessionState sessionState = event.getSessionState();
        LexEvent.Intent intent = sessionState.getIntent();
        intent.setState(state);
        return LexResponse.builder()
                .withSessionState(LexEvent.SessionState.builder()
                        .withDialogAction(LexEvent.DialogAction.builder()
                                .withType("Close")
                                .build())
                        .withSessionAttributes(sessionState.getSessionAttributes())
                        .withIntent(intent)
                        .build())
                .withMessages(messages)
                .build();

    }

    public static LexResponse closeSuccess(LexEvent event, String state, String message) {
        return close(event, state, new LexResponse.Message[]{
                LexResponse.Message.builder()
                        .withContentType("PlainText")
                        .withContent(message)
                        .build()
        });
    }

    public static LexResponse elicit(LexEvent event, IllegalSlotException e) {
        LexEvent.SessionState sessionState = event.getSessionState();
        LexEvent.Intent intent = sessionState.getIntent();
        return LexResponse.builder()
                .withSessionState(LexEvent.SessionState.builder()
                        .withDialogAction(LexEvent.DialogAction.builder()
                                .withType("ElicitSlot")
                                .withSlotToElicit(e.getSlotName())
                                .build())
                        .withIntent(LexEvent.Intent.builder()
                                .withName(intent.getName())
                                .build())
                        .build())
                .build();
    }

    public static LexResponse closeError(LexEvent event) {
        return close(event, "Failed", null);
    }

}
