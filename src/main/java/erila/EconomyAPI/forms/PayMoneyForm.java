package erila.EconomyAPI.forms;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import erila.EconomyAPI.manager.LanguageManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PayMoneyForm {

    public static void openForm(Player player) {
        List<String> playerNames = new ArrayList<>();
        for (Player onlinePlayer : player.getServer().getOnlinePlayers().values()) {
            if (!onlinePlayer.getName().equalsIgnoreCase(player.getName())) {
                playerNames.add(onlinePlayer.getName());
            }
        }
        Collections.sort(playerNames);

        if (playerNames.isEmpty()) {
            player.sendMessage(LanguageManager.translate("paymoney.no_players"));
            return;
        }

        FormWindowCustom form = new FormWindowCustom(LanguageManager.translate("paymoney.title"));
        form.addElement(new ElementDropdown(LanguageManager.translate("paymoney.select_player"), playerNames));
        form.addElement(new ElementInput(LanguageManager.translate("paymoney.enter_amount"), LanguageManager.translate("paymoney.enter_amount.placeholder")));
        player.showFormWindow(form, 2);
    }
}
