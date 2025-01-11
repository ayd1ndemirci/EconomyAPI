package ayd1ndemirci.EconomyAPI.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import ayd1ndemirci.EconomyAPI.api.EconomyAPI;
import ayd1ndemirci.EconomyAPI.manager.Manager;

public class SeeMoneyCommand extends Command {

    public SeeMoneyCommand() {
        super("seemoney", "Oyuncunun parasına bakarsınız", "/seemoney <playerName>");
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("player", true, CommandParamType.TARGET)
        });
        setPermission(Manager.SEE_MONEY);
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!testPermission(sender)) return false;
        if (!sender.getServer().isOp(sender.getName())) return false;
        if (args.length < 1) {
            sender.sendMessage(getUsage());
            return false;
        }
        String playerName = String.join(" ", args);
        if (sender.getServer().getPlayer(playerName) != null) playerName = sender.getServer().getPlayer(playerName).getName();
        sender.sendMessage(String.format("§2» %s §aisimli oyuncunun parası: §2%s", playerName, EconomyAPI.formatMoney(EconomyAPI.getMoney(playerName))));
        return true;
    }
}