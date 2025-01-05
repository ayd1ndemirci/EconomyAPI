package erila.EconomyAPI.forms;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import erila.EconomyAPI.manager.LanguageManager;

public class MoneyForm {

    public static void openForm(Player player) {
        FormWindowSimple form = new FormWindowSimple(
                LanguageManager.translate("money.menu.title"),
                ""
        );
        form.addButton(new ElementButton(LanguageManager.translate("money.menu.check_balance")));
        form.addButton(new ElementButton(LanguageManager.translate("money.menu.check_others_balance")));
        form.addButton(new ElementButton(LanguageManager.translate("money.menu.send_money")));
        form.addButton(new ElementButton(LanguageManager.translate("money.menu.riches")));
        player.showFormWindow(form, 0);
    }
}
