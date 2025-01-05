package erila.EconomyAPI.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import erila.EconomyAPI.api.EconomyAPI;
import erila.EconomyAPI.forms.*;
import erila.EconomyAPI.manager.DatabaseManager;
import erila.EconomyAPI.manager.Manager;
import erila.EconomyAPI.manager.LanguageManager;

public class FormListener implements Listener {

    @EventHandler
    public void onFormEvent(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        int id = event.getFormID();

        if (id == Manager.MONEY_FORM_ID) {
            if (event.getWindow() instanceof FormWindowSimple) {
                if (event.getResponse() != null) {
                    if (event.getResponse() instanceof FormResponseSimple response) {
                        int buttonId = response.getClickedButtonId();

                        switch (buttonId) {
                            case 0 -> SeeMoneyForm.openForm(player);
                            case 1 -> SeeOtherMoneyForm.openForm(player);
                            case 2 -> PayMoneyForm.openForm(player);
                            case 3 -> RichesForm.openForm(player, 1);
                            default -> player.sendMessage(LanguageManager.translate("error.invalid_option"));
                        }
                    }
                }
            }
        }

        if (id == Manager.SEE_OTHER_MONEY_FORM_ID) {
            if (event.getWindow() instanceof FormWindowCustom) {
                if (event.getResponse() != null) {
                    FormResponseCustom response = (FormResponseCustom) event.getResponse();
                    String inputName = response.getInputResponse(0);
                    if (inputName.isEmpty()) {
                        player.sendMessage(LanguageManager.translate("error.enter_player_name"));
                        return;
                    }
                    if (player.getServer().getPlayer(inputName) != null) {
                        inputName = player.getServer().getPlayer(inputName).getName().toLowerCase();
                    }
                    int money = EconomyAPI.getMoney(inputName.toLowerCase());
                    player.sendMessage(LanguageManager.translate("info.player_money",
                            "player", inputName,
                            "money", EconomyAPI.formatMoney(money)
                    ));
                }
            }
        }

        if (id == Manager.PAY_MONEY_FORM_ID) {
            if (event.getWindow() instanceof FormWindowCustom) {
                if (event.getResponse() != null) {
                    FormResponseCustom response = (FormResponseCustom) event.getResponse();
                    String targetName = response.getInputResponse(0);
                    String moneyInput = response.getInputResponse(1);

                    if (targetName == null || targetName.isEmpty()) {
                        player.sendMessage(LanguageManager.translate("error.enter_player_name"));
                        return;
                    }
                    if (moneyInput == null || moneyInput.isEmpty()) {
                        player.sendMessage(LanguageManager.translate("error.enter_amount"));
                        return;
                    }
                    int amount;
                    try {
                        amount = Integer.parseInt(moneyInput);
                        if (amount <= 0) {
                            player.sendMessage(LanguageManager.translate("error.positive_amount"));
                            return;
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage(LanguageManager.translate("error.invalid_amount"));
                        return;
                    }

                    if (EconomyAPI.getMoney(player.getName().toLowerCase()) < amount) {
                        player.sendMessage(LanguageManager.translate("error.not_enough_money"));
                        return;
                    }

                    if (!DatabaseManager.existsKey(targetName.toLowerCase())) {
                        player.sendMessage(LanguageManager.translate("error.player_not_found"));
                        return;
                    }

                    EconomyAPI.takeMoney(player.getName().toLowerCase(), amount);
                    EconomyAPI.addMoney(targetName.toLowerCase(), amount);

                    player.sendMessage(LanguageManager.translate("success.money_sent",
                            "player", targetName,
                            "amount", String.valueOf(amount)
                    ));

                    Player targetPlayer = player.getServer().getPlayer(targetName);
                    if (targetPlayer != null && targetPlayer.isOnline()) {
                        targetPlayer.sendMessage(LanguageManager.translate("info.money_received",
                                "player", player.getName(),
                                "amount", String.valueOf(amount)
                        ));
                    }
                }
            }
        }
    }
}
