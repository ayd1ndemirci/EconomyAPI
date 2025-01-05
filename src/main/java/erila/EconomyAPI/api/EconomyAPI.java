package erila.EconomyAPI.api;

import erila.EconomyAPI.manager.DatabaseManager;

import java.text.NumberFormat;
import java.util.Locale;

public class EconomyAPI {

    public static int getMoney(String playerName) {
        return DatabaseManager.getValue(playerName.toLowerCase());
    }

    public static void setMoney(String playerName, int money) {
        DatabaseManager.setValue(playerName.toLowerCase(), money);
    }

    public static boolean takeMoney(String playerName, int money) {
        int currentMoney = getMoney(playerName);
        if (currentMoney >= money) {
            setMoney(playerName.toLowerCase(), currentMoney - money);
            return true;
        }
        return false;
    }

    public static boolean addMoney(String playerName, int money) {
        int currentMoney = getMoney(playerName);
        int newMoney = currentMoney + money;
        if (newMoney >= DatabaseManager.getMoneyLimit()) newMoney = DatabaseManager.getMoneyLimit();
        setMoney(playerName.toLowerCase(), newMoney);
        return true;
    }

    public static String formatMoney(int money) {
        Locale locale = new Locale.Builder().setLanguage("tr").setRegion("TR").build();
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(money) + " TL";
    }
}