package ayd1ndemirci.EconomyAPI.listener;

import ayd1ndemirci.EconomyAPI.forms.*;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import ayd1ndemirci.EconomyAPI.api.EconomyAPI;
import ayd1ndemirci.EconomyAPI.forms.*;
import ayd1ndemirci.EconomyAPI.manager.DatabaseManager;
import ayd1ndemirci.EconomyAPI.manager.Manager;

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
                            default -> player.sendMessage("Geçersiz seçenek!");
                        }
                    }
                }
            }
        }

        if (id == Manager.SEE_MONEY_FORM_ID) {
            if (event.getWindow() instanceof FormWindowSimple) {
                if (event.getResponse() != null) {
                    if (event.getResponse() instanceof FormResponseSimple response) {
                        int buttonId = response.getClickedButtonId();
                        if (buttonId == 0) MoneyForm.openForm(player);
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
                        player.sendMessage("§c» Lütfen bir oyuncu ismi girin.");
                        return;
                    }
                    if (player.getServer().getPlayer(inputName) != null) inputName = player.getServer().getPlayer(inputName).getName().toLowerCase();
                    int money = EconomyAPI.getMoney(inputName.toLowerCase());
                    player.sendMessage(String.format("§2» %s §aisimli oyuncunun parası: §2%s", inputName, EconomyAPI.formatMoney(money)));
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
                        player.sendMessage("§c» Lütfen bir oyuncu adı girin.");
                        return;
                    }
                    if (moneyInput == null || moneyInput.isEmpty()) {
                        player.sendMessage("§c» Lütfen göndermek istediğiniz miktarı girin.");
                        return;
                    }
                    int amount;
                    try {
                        amount = Integer.parseInt(moneyInput);
                        if (amount <= 0) {
                            player.sendMessage("§c» Göndermek istediğiniz miktar pozitif bir sayı olmalıdır.");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage("§c» Lütfen geçerli bir miktar girin.");
                        return;
                    }
                    if (EconomyAPI.getMoney(player.getName().toLowerCase()) < amount) {
                        player.sendMessage("§c» Yeterli paranız yok!");
                        return;
                    }
                    if (!DatabaseManager.existsKey(targetName.toLowerCase())) {
                        player.sendMessage("§c» Bu oyuncu bulunamadı veya hesabı yok.");
                        return;
                    }
                    EconomyAPI.takeMoney(player.getName().toLowerCase(), amount);
                    EconomyAPI.addMoney(targetName.toLowerCase(), amount);
                    player.sendMessage(String.format("§2» §aBaşarıyla §2%s §aadlı oyuncuya §2%d §a gönderdiniz.", targetName, amount));
                    Player targetPlayer = player.getServer().getPlayer(targetName);
                    if (targetPlayer != null && targetPlayer.isOnline()) {
                        targetPlayer.sendMessage(String.format("§2» %s §aadlı oyuncudan size §2%d §a gönderildi.", player.getName(), amount));
                    }
                }
            }
        }
        if (id == Manager.RICHES_FORM_ID) {
            if (event.getWindow() instanceof FormWindowSimple) {
                if (event.getResponse() != null) {
                    if (event.getResponse() instanceof FormResponseSimple response) {
                        int buttonId = response.getClickedButtonId();
                        if (buttonId == 0) MoneyForm.openForm(player);
                    }
                }
            }
        }
    }
}