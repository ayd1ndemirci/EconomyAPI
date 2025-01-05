package erila.EconomyAPI.forms;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindowCustom;
import erila.EconomyAPI.api.EconomyAPI;

public class SeeOtherMoneyForm implements Listener {

    public static void openForm(Player player) {
        FormWindowCustom form = new FormWindowCustom("Para Menüsü");
        form.addElement(new ElementInput("Oyuncu İsmi:", player.getName()));
        player.showFormWindow(form, 3);
    }
}
