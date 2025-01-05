package erila.EconomyAPI.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import erila.EconomyAPI.api.EconomyAPI;
import erila.EconomyAPI.manager.LanguageManager;
import erila.EconomyAPI.manager.Manager;

public class SeeMoneyCommand extends Command {

    public SeeMoneyCommand() {
        super(
                "seemoney",
                LanguageManager.translate("command.seemoney.description"),
                LanguageManager.translate("command.seemoney.usage")
        );
        commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("player", true, CommandParamType.TARGET)
        });
        setPermission(Manager.SEE_MONEY);
    }


    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!testPermission(sender)) return false;
        if (!sender.getServer().isOp(sender.getName())) return false;
        if (args.length < 1) {
            sender.sendMessage(LanguageManager.translate("command.seemoney.usage"));
            return false;
        }

        String playerName = String.join(" ", args);
        if (sender.getServer().getPlayer(playerName) != null) {
            playerName = sender.getServer().getPlayer(playerName).getName();
        }
        sender.sendMessage(LanguageManager.translate(
                "command.seemoney.success",
                "player", playerName,
                "money", EconomyAPI.formatMoney(EconomyAPI.getMoney(playerName))
        ));
        return true;
    }
}
