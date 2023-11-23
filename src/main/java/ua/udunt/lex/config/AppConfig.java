package ua.udunt.lex.config;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Slf4j
@SuppressWarnings("unchecked")
public final class AppConfig {

    private Map<String, Object> config;

    private static AppConfig instance;

    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }

    private AppConfig() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("application.yml");
            Yaml yaml = new Yaml();
            config = yaml.load(is);
            log.info("Application config initialized");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getParam(String path) {
        String[] sub = path.split("\\.");
        String param = null;
        int i = 0;
        for (String s : sub) {
            i++;
            Object o = config.get(s);
            if (o instanceof Map) {
                config = (Map<String, Object>) o;
            } else {
                if (i == sub.length && o != null) {
                    param = o.toString();
                }
                break;
            }
        }
        return param;
    }


}
