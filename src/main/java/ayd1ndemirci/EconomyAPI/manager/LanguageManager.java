package ayd1ndemirci.EconomyAPI.manager;

import cn.nukkit.utils.Config;

import java.io.File;
import java.util.*;

public class LanguageManager {
    private static final Map<String, String> translations = new HashMap<>();

    public static void init(Config config) {
        String currentLanguage = config.getString("Language", "eng");
        loadLanguage(currentLanguage);
    }

    private static void loadLanguage(String language) {
        File languageFile = new File("lang/" + language + ".yml");
        if (languageFile.exists()) {
            Config langConfig = new Config(languageFile, Config.YAML);
            translations.clear();
            for (String key : langConfig.getKeys(false)) {
                translations.put(key, langConfig.getString(key));
            }
        } else loadLanguage("eng");
    }

    public static String getTranslation(String key) {
        return translations.getOrDefault(key, key);
    }
}
