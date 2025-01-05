package erila.EconomyAPI.forms;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import erila.EconomyAPI.manager.LanguageManager;
import erila.EconomyAPI.api.EconomyAPI;
import erila.EconomyAPI.manager.DatabaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RichesForm {

    public static void openForm(Player player, int page) {
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
            content.append(LanguageManager.translate("riches.player_rank", "rank", String.valueOf(rank++), "player", playerName, "money", EconomyAPI.formatMoney(money)))
                    .append("\n");
        }

        int playerRank = sortedEntries.stream()
                .map(Map.Entry::getKey)
                .toList()
                .indexOf(player.getName().toLowerCase()) + 1;

        content.append("\n")
                .append(LanguageManager.translate("riches.your_rank", "rank", String.valueOf(playerRank)));

        FormWindowSimple form = new FormWindowSimple(
                LanguageManager.translate("riches.title"),
                content.toString()
        );
        form.addButton(new ElementButton(LanguageManager.translate("riches.back_button")));
        player.showFormWindow(form, 4);
    }
}
