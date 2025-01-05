package erila.EconomyAPI;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import erila.EconomyAPI.listener.FormListener;
import erila.EconomyAPI.listener.PlayerListener;
import erila.EconomyAPI.manager.LanguageManager;
import erila.EconomyAPI.command.*;

import java.io.File;

public class Main extends PluginBase {

    public static Config config, playersConfig;

    @Override
    public void onEnable() {
        initConfigs();
        createLangFolder();
        saveDefaultLangFiles();
        loadLanguage();
        registerCommands();
        registerEvents();
        getLogger().info("Plugin aktif edildi.");
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new FormListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void registerCommands() {
        getServer().getCommandMap().register("para", new MoneyCommand());
        getServer().getCommandMap().register("seemoney", new SeeMoneyCommand());
        getServer().getCommandMap().register("addmoney", new AddMoneyCommand());
        getServer().getCommandMap().register("takemoney", new TakeMoneyCommand());
        getServer().getCommandMap().register("setmoney", new SetMoneyCommand());
    }

    public void initConfigs() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        config = new Config(configFile, Config.YAML);

        if (!config.exists("lang")) {
            config.set("lang", "eng");
        }
        if (!config.exists("MoneyLimit")) {
            config.set("MoneyLimit", 1000000);
        }
        if (!config.exists("StarterMoney")) {
            config.set("StarterMoney", 5000);
        }
        config.save();

        File playersFile = new File(getDataFolder(), "players.yml");
        if (!playersFile.exists()) {
            playersConfig = new Config(playersFile, Config.YAML);
            playersConfig.save();
        } else {
            playersConfig = new Config(playersFile, Config.YAML);
        }
    }

    private void createLangFolder() {
        File langFolder = new File(getDataFolder(), "lang");
        if (!langFolder.exists()) {
            if (langFolder.mkdirs()) {
                getLogger().info("lang klasörü oluşturuldu.");
            } else {
                getLogger().warning("lang klasörü oluşturulamadı.");
            }
        }
    }

    private void saveDefaultLangFiles() {
        String[] langFiles = {"lang/eng.json", "lang/tr.json"};
        for (String langFile : langFiles) {
            File file = new File(getDataFolder(), langFile);
            if (!file.exists()) {
                saveResource(langFile, false);
                getLogger().info(langFile + " dosyası oluşturuldu.");
            }
        }
    }

    private void loadLanguage() {
        String lang = config.getString("lang", "eng");
        File langFile = new File(getDataFolder() + "/lang", lang + ".json");

        if (!langFile.exists()) {
            getLogger().warning("Dil dosyası bulunamadı: " + lang + ".json. Varsayılan dil kullanılacak.");
            langFile = new File(getDataFolder() + "/lang", "eng.json");
        }

        Config langConfig = new Config(langFile, Config.JSON);
        LanguageManager.setLangConfig(langConfig);
    }
}
