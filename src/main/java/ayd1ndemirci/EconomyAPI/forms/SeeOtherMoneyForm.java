package ayd1ndemirci.EconomyAPI.forms;

import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;

public class SeeOtherMoneyForm implements Listener {

    public static void openForm(Player player) {
        FormWindowCustom form = new FormWindowCustom("Para Menüsü");
        form.addElement(new ElementInput("Oyuncu İsmi:", player.getName()));
        player.showFormWindow(form, 3);
    }
}
