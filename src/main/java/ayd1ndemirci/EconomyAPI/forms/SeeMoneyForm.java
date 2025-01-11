package ayd1ndemirci.EconomyAPI.forms;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ayd1ndemirci.EconomyAPI.api.EconomyAPI;

public class SeeMoneyForm {

    public static void openForm(Player player) {
        FormWindowSimple form = new FormWindowSimple("Para Menüsü", "");
        form.setContent("§bParan: §f" + EconomyAPI.formatMoney(EconomyAPI.getMoney(player.getName())));
        form.addButton(new ElementButton("<- Geri Dön"));
        player.showFormWindow(form, 1);
    }
}
