package ayd1ndemirci.EconomyAPI.forms;

import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ayd1ndemirci.EconomyAPI.api.EconomyAPI;
import ayd1ndemirci.EconomyAPI.manager.DatabaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RichesForm implements Listener {

    public static void openForm(Player player, int page) {
        FormWindowSimple form = new FormWindowSimple("Zenginler", "");

        Map<String, Object> allMoney = DatabaseManager.getAllMoney();
        List<Map.Entry<String, Object>> sortedEntries = new ArrayList<>(allMoney.entrySet());
        sortedEntries.sort((o1, o2) -> Integer.compare((int) o2.getValue(), (int) o1.getValue()));

        int totalPages = (int) Math.ceil(sortedEntries.size() / 10.0);
        page = Math.max(1, Math.min(page, totalPages));

        List<Map.Entry<String, Object>> currentPageData = sortedEntries.subList((page - 1) * 10,
                Math.min(page * 10, sortedEntries.size()));

        StringBuilder content = new StringBuilder();
        int rank = (page - 1) * 10 + 1;
        for (Map.Entry<String, Object> entry : currentPageData) {
            String playerName = entry.getKey();
            int money = (int) entry.getValue();
            content.append("§b")
                    .append(rank++)
                    .append(". §2")
                    .append(playerName)
                    .append(" §a» §2")
                    .append(EconomyAPI.formatMoney(money))
                    .append("\n");
        }

        int playerRank = sortedEntries.stream()
                .map(Map.Entry::getKey)
                .toList()
                .indexOf(player.getName().toLowerCase()) + 1;

        content.append("\n§aSenin Sıran §7=> §2").append(playerRank);

        form.setContent(content.toString());

        form.addButton(new ElementButton("§cGeri"));
        player.showFormWindow(form, 4);
    }
}
