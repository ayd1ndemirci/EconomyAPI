package erila.EconomyAPI.forms;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import erila.EconomyAPI.manager.LanguageManager;
import erila.EconomyAPI.api.EconomyAPI;

public class SeeMoneyForm {

    public static void openForm(Player player) {
        FormWindowSimple form = new FormWindowSimple(
                LanguageManager.translate("menu.title"),
                LanguageManager.translate("menu.content", "player", player.getName(), "money", EconomyAPI.formatMoney(EconomyAPI.getMoney(player.getName())))
        );
        form.addButton(new ElementButton(LanguageManager.translate("menu.back")));
        player.showFormWindow(form, 1);
    }
}
