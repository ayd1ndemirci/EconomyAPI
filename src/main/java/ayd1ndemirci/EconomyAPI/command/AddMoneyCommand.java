package ayd1ndemirci.EconomyAPI.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import ayd1ndemirci.EconomyAPI.api.EconomyAPI;
import ayd1ndemirci.EconomyAPI.manager.Manager;

public class AddMoneyCommand extends Command {

    public AddMoneyCommand() {
        super("addmoney", "Bir oyuncuya para eklersiniz", "/addmoney <playerName> <money>");
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
            sender.sendMessage("§c» Geçerli bir miktar girin.");
            return false;
        }

        if (amount <= 0) {
            sender.sendMessage("§c» Para miktarı pozitif olmalıdır.");
            return false;
        }

        if (sender.getServer().getPlayer(playerName) != null) {
            playerName = sender.getServer().getPlayer(playerName).getName();
        }

        EconomyAPI.addMoney(playerName.toLowerCase(), amount);
        String formattedAmount = EconomyAPI.formatMoney(amount);
        if (formattedAmount == null) {
            sender.sendMessage("§c» Para formatı hatalı.");
            return false;
        }
        sender.sendMessage(String.format("§2» %s §aadlı oyuncuya §2%s §aeklendi.", playerName, formattedAmount));

        return true;
    }
}
