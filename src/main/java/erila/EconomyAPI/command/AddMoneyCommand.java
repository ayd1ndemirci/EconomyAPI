package erila.EconomyAPI.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import erila.EconomyAPI.api.EconomyAPI;
import erila.EconomyAPI.manager.LanguageManager;
import erila.EconomyAPI.manager.Manager;

public class AddMoneyCommand extends Command {

    public AddMoneyCommand() {
        super(
                "addmoney",
                LanguageManager.translate("command.addmoney.description"),
                LanguageManager.translate("command.addmoney.usage")
        );
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[] {
                CommandParameter.newType("player", true, CommandParamType.TARGET),
                CommandParameter.newType("money", true, CommandParamType.INT)
        });
        setPermission(Manager.ADD_MONEY);
    }


    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!testPermission(sender)) return false;
        if (!sender.getServer().isOp(sender.getName())) return false;
        if (args.length < 2) {
            sender.sendMessage(getUsage());
            return false;
        }
        String playerName = args[0];
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(LanguageManager.translate("command.addmoney.invalid_amount"));
            return false;
        }

        if (amount <= 0) {
            sender.sendMessage(LanguageManager.translate("command.addmoney.positive_amount"));

            return false;
        }

        if (sender.getServer().getPlayer(playerName) != null) {
            playerName = sender.getServer().getPlayer(playerName).getName();
        }

        EconomyAPI.addMoney(playerName.toLowerCase(), amount);
        sender.sendMessage(String.format(LanguageManager.translate("command.addmoney.success", "player", playerName, "money", EconomyAPI.formatMoney(amount))));
        return true;
    }
}
