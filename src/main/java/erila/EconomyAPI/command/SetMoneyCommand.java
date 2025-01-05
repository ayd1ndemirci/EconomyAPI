package erila.EconomyAPI.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import erila.EconomyAPI.api.EconomyAPI;
import erila.EconomyAPI.manager.LanguageManager;
import erila.EconomyAPI.manager.Manager;

public class SetMoneyCommand extends Command {

    public SetMoneyCommand() {
        super(
                "setmoney",
                LanguageManager.translate("command.setmoney.description"),
                LanguageManager.translate("command.setmoney.usage")
        );
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[] {
                CommandParameter.newType("player", true, CommandParamType.TARGET),
                CommandParameter.newType("money", true, CommandParamType.INT)
        });
        setPermission(Manager.SET_MONEY);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!testPermission(sender)) return false;

        if (args.length < 2) {
            sender.sendMessage(LanguageManager.translate("command.setmoney.usage"));
            return false;
        }

        String playerName = args[0];
        int amount;

        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(LanguageManager.translate("command.setmoney.invalid_amount"));
            return false;
        }

        if (amount < 0) {
            sender.sendMessage(LanguageManager.translate("command.setmoney.negative_amount"));
            return false;
        }

        if (sender.getServer().getPlayer(playerName) != null) {
            playerName = sender.getServer().getPlayer(playerName).getName();
        }

        EconomyAPI.setMoney(playerName.toLowerCase(), amount);
        sender.sendMessage(LanguageManager.translate(
                "command.setmoney.success",
                "player", playerName,
                "money", EconomyAPI.formatMoney(amount)
        ));

        return true;
    }
}
