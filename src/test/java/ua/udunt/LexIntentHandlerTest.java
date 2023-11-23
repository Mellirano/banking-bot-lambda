package ua.udunt;

import org.junit.Test;
import ua.udunt.lex.handler.LexIntentHandler;
import ua.udunt.lex.model.LexEvent;
import ua.udunt.lex.util.ObjectUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LexIntentHandlerTest {

    public LexEvent initializeEvent(String eventName) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(eventName + ".json");
        if (is != null) {
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return ObjectUtil.fromJSON(json, LexEvent.class);
        } else {
            throw new IllegalStateException("Event not found");
        }
    }

    @Test
    public void processCurrentExchangeIntentTest() throws IOException {
        LexEvent event = initializeEvent("processCurrentExchangeIntent");
        LexIntentHandler handler = new LexIntentHandler();
        handler.handleRequest(event, null);
    }

}
