package erila.EconomyAPI.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import erila.EconomyAPI.api.EconomyAPI;
import erila.EconomyAPI.manager.LanguageManager;
import erila.EconomyAPI.manager.Manager;

public class TakeMoneyCommand extends Command {

    public TakeMoneyCommand() {
        super(
                "takemoney",
                LanguageManager.translate("command.takemoney.description"),
                "/takemoney <playerName> <amount>"
        );
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("player", true, CommandParamType.TARGET),
                CommandParameter.newType("money", true, CommandParamType.INT)
        });
        setPermission(Manager.TAKE_MONEY);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!testPermission(sender)) return false;

        if (args.length < 2) {
            sender.sendMessage(LanguageManager.translate("command.takemoney.usage"));
            return false;
        }

        String playerName = args[0];
        int amount;

        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(LanguageManager.translate("command.takemoney.invalid_amount"));
            return false;
        }

        if (amount <= 0) {
            sender.sendMessage(LanguageManager.translate("command.takemoney.negative_amount"));
            return false;
        }

        if (sender.getServer().getPlayer(playerName) != null) {
            playerName = sender.getServer().getPlayer(playerName).getName();
        }

        if (EconomyAPI.getMoney(playerName.toLowerCase()) < amount) {
            sender.sendMessage(LanguageManager.translate("command.takemoney.insufficient_funds"));
            return false;
        }

        EconomyAPI.takeMoney(playerName.toLowerCase(), amount);
        sender.sendMessage(LanguageManager.translate(
                "command.takemoney.success",
                "player", playerName,
                "money", EconomyAPI.formatMoney(amount)
        ));

        return true;
    }
}
