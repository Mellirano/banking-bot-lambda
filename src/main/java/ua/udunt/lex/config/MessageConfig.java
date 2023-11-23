package ua.udunt.lex.config;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public final class MessageConfig {

    private final JSONObject messages;

    private static MessageConfig instance;

    public static MessageConfig getInstance() {
        if (instance == null) {
            synchronized (MessageConfig.class) {
                if (instance == null) {
                    instance = new MessageConfig();
                }
            }
        }
        return instance;
    }

    private MessageConfig() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("messages.json");
            if (is != null) {
                String rawMessages = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                messages = new JSONObject(rawMessages);
                log.info("Messages initialized");
            } else {
                throw new RuntimeException("Lex fulfill messages not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getMessage(String intentName, String messageKey) {
        JSONObject jo = messages.getJSONObject(intentName);
        if (jo != null && jo.has(messageKey)) {
            return jo.getString(messageKey);
        }
        return null;
    }

    public String getMessage(String intentName, String arrayMessageKey, String messageKey) {
        JSONObject jo = messages.getJSONObject(intentName);
        if (jo != null && jo.has(arrayMessageKey)) {
            JSONArray ja = jo.getJSONArray(arrayMessageKey);
            for (Object j : ja) {
                if (j instanceof JSONObject job) {
                    if (job.has(messageKey)) {
                        return job.getString(messageKey);
                    }
                }
            }
        }
        return null;
    }

}
