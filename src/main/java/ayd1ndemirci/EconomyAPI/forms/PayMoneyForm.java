package ayd1ndemirci.EconomyAPI.forms;

import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PayMoneyForm implements Listener {

    public static void openForm(Player player) {
        FormWindowCustom form = new FormWindowCustom("Para Gönderme Formu");
        List<String> playerNames = new ArrayList<>();
        for (Player onlinePlayer : player.getServer().getOnlinePlayers().values()) {
            if (!onlinePlayer.getName().equalsIgnoreCase(player.getName())) {
                playerNames.add(onlinePlayer.getName());
            }
        }
        Collections.sort(playerNames);

        if (playerNames.isEmpty()) {
            player.sendMessage("§c» Sunucuda başka bir oyuncu yok!");
            return;
        }
        form.addElement(new ElementDropdown("Oyuncu Seç:", playerNames));

        form.addElement(new ElementInput("Miktar Gir:", "§7Örn; 200"));
        player.showFormWindow(form, 2);
    }
}
