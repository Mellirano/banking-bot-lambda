package ua.udunt.lex.support;

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

    public static LexResponse closeError(LexEvent event) {
        return close(event, "Failed", null);
    }

    //TODO Add reilicit
    //'sessionState': {
    //            'activeContexts':[{
    //                'name': 'intentContext',
    //                'contextAttributes': active_contexts,
    //                'timeToLive': {
    //                    'timeToLiveInSeconds': 600,
    //                    'turnsToLive': 1
    //                }
    //            }],
    //            'sessionAttributes': session_attributes,
    //            'dialogAction': {
    //                'type': 'ElicitSlot',
    //                'slotToElicit': slot_to_elicit
    //            },
    //            'intent': intent,
    //        }

}
