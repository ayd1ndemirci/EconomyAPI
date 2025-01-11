package ayd1ndemirci.EconomyAPI;

import ayd1ndemirci.EconomyAPI.command.*;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import ayd1ndemirci.EconomyAPI.listener.FormListener;
import ayd1ndemirci.EconomyAPI.listener.PlayerListener;

import java.io.File;

public class Main extends PluginBase {

    public static Config config, playersConfig;

    @Override
    public void onEnable() {
        initConfigs();
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
}
