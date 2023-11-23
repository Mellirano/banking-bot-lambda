package ua.udunt.lex.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import ua.udunt.lex.config.AppConfig;
import ua.udunt.lex.model.ExchangeInfo;
import ua.udunt.lex.model.ExchangeSlot;
import ua.udunt.lex.util.LibUtil;
import ua.udunt.lex.util.ObjectUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class ExchangeInfoService {

    private final String baseUrl;
    private static ExchangeInfoService instance;
    private static AppConfig appConfig = AppConfig.getInstance();

    public static ExchangeInfoService getInstance() {
        if (instance == null) {
            synchronized (ExchangeInfoService.class) {
                if (instance == null) {
                    instance = new ExchangeInfoService();
                }
            }
        }
        return instance;
    }

    private ExchangeInfoService() {
        this.baseUrl = appConfig.getParam("services.exchange.url");
    }

    //5 - In deps
    //11 - Non-cash course
    public ExchangeInfo getExchange(ExchangeSlot slot) {
        List<ExchangeInfo> exchanges = getExchanges(slot.getExchangeId());
        if (!LibUtil.isNullOrEmpty(exchanges)) {
            return exchanges.stream()
                    .filter(p -> slot.getExchangeCurrency().equals(p.getCcy()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public List<ExchangeInfo> getExchanges(String exchangeId) {
        if (LibUtil.isNullOrEmpty(exchangeId)
                && !("5".equals(exchangeId)
                || "11".equals(exchangeId))) {
            return null;
        }
        try {
            String path = "/p24api/pubinfo?exchange&coursid=" + exchangeId;
            String url = baseUrl + path;
            log.info("REQUEST >> [ExchangeId: {}, Endpoint: {}]", exchangeId, url);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .timeout(Duration.ofMillis(5000))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = null;
            try {
                httpResponse = HttpClient.newBuilder()
                        .build()
                        .send(httpRequest, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                log.error("HTTP Request was interrupted");
            }
            if (httpResponse != null) {
                if (httpResponse.statusCode() / 100 == 2) {
                    if (httpResponse.body() != null) {
                        log.info("RESPONSE << [Body: {}, Endpoint: {}]", httpResponse.body(), url);
                        List<ExchangeInfo> exchanges = new ArrayList<>(2);
                        JSONArray ja = new JSONArray(httpResponse.body());
                        ja.forEach(jo ->
                                exchanges.add(ObjectUtil.fromJSON(jo.toString(), ExchangeInfo.class))
                        );
                        return exchanges;
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

}
