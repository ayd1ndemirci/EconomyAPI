package erila.EconomyAPI.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import erila.EconomyAPI.api.EconomyAPI;
import erila.EconomyAPI.manager.DatabaseManager;

public class PlayerListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (!DatabaseManager.existsKey(player.getName())) {
            EconomyAPI.setMoney(player.getName(), DatabaseManager.getStarterMoney());
        }
    }
}