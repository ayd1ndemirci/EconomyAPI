package ayd1ndemirci.EconomyAPI.manager;

import ayd1ndemirci.EconomyAPI.Main;

import java.util.Map;

public class DatabaseManager {

    public static boolean existsKey(String key) {
        return Main.playersConfig.exists(key.toLowerCase());
    }

    public static int getValue(String key) {
        return existsKey(key.toLowerCase()) ? Main.playersConfig.getInt(key.toLowerCase()) : 0;
    }

    public static boolean addKey(String key, int value) {
        if (existsKey(key.toLowerCase())) return false;
        Main.playersConfig.set(key.toLowerCase(), value);
        Main.playersConfig.save();
        return true;
    }


    public static boolean removeKey(String key) {
        if (existsKey(key.toLowerCase())) return false;
        Main.playersConfig.remove(key.toLowerCase());
        Main.playersConfig.save();
        return true;
    }

    public static boolean setValue(String key, int value) {
        if (!existsKey(key.toLowerCase())) {
            return addKey(key, value);
        }
        Main.playersConfig.set(key.toLowerCase(), value);
        Main.playersConfig.save();
        return true;
    }

    public static int getMoneyLimit() {
        return Main.config.getInt("MoneyLimit");
    }

    public static int getStarterMoney() {
        return Main.config.getInt("StarterMoney");
    }

    public static Map<String, Object> getAllMoney() {
        return Main.playersConfig.getAll();
    }
}