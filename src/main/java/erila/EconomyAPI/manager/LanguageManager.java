package erila.EconomyAPI.manager;

import cn.nukkit.utils.Config;

public class LanguageManager {

    private static Config langConfig;
    public static void setLangConfig(Config config) {
        langConfig = config;
    }
    public static String translate(String key, String... placeholders) {
        if (langConfig == null) {
            return key;
        }
        String message = langConfig.getString(key, key);
        for (int i = 0; i < placeholders.length; i += 2) {
            message = message.replace("{" + placeholders[i] + "}", placeholders[i + 1]);
        }
        return message;
    }
}
