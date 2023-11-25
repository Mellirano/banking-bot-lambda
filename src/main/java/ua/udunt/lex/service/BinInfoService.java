package ua.udunt.lex.service;

import lombok.extern.slf4j.Slf4j;
import ua.udunt.lex.config.AppConfig;
import ua.udunt.lex.model.BinInfo;
import ua.udunt.lex.util.LibUtil;
import ua.udunt.lex.util.ObjectUtil;
import ua.udunt.lex.util.PanUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
public final class BinInfoService {

    private final String baseUrl;
    private static BinInfoService instance;
    private static AppConfig appConfig = AppConfig.getInstance();

    public static BinInfoService getInstance() {
        if (instance == null) {
            synchronized (BinInfoService.class) {
                if (instance == null) {
                    instance = new BinInfoService();
                }
            }
        }
        return instance;
    }

    private BinInfoService() {
        this.baseUrl = appConfig.getParam("services.bin.url");
    }

    public BinInfo getBinInfo(String accountNumber) {
        if (LibUtil.isNullOrEmpty(accountNumber)) {
            return null;
        }
        try {
            String bin = PanUtil.getPanBin(accountNumber);
            String url = baseUrl + "/bin/" + bin;
            log.info("REQUEST >> [Bin: {}, Endpoint: {}]", bin, url);
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
                        return ObjectUtil.fromJSON(httpResponse.body(), BinInfo.class);
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

}
