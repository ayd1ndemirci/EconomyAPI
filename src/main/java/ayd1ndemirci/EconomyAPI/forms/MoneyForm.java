package ayd1ndemirci.EconomyAPI.forms;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;

public class MoneyForm {

    public static void openForm(Player player) {
        FormWindowSimple form = new FormWindowSimple("Para Menüsü", "");
        form.addButton(new ElementButton("Parana Bak"));
        form.addButton(new ElementButton("Başkasının Parasına Bak"));
        form.addButton(new ElementButton("Para Gönder"));
        form.addButton(new ElementButton("Zenginler"));
        player.showFormWindow(form, 0);
    }
}