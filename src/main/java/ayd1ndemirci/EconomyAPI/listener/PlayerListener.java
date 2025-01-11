package ayd1ndemirci.EconomyAPI.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import ayd1ndemirci.EconomyAPI.api.EconomyAPI;
import ayd1ndemirci.EconomyAPI.manager.DatabaseManager;

public class PlayerListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (!DatabaseManager.existsKey(player.getName())) {
            System.out.println(player.getName() + " doesn't exist");
            EconomyAPI.setMoney(player.getName(), DatabaseManager.getStarterMoney());
        }
    }
}