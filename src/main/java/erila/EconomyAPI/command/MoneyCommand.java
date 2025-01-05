package erila.EconomyAPI.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import erila.EconomyAPI.forms.MoneyForm;
import erila.EconomyAPI.manager.LanguageManager;

public class MoneyCommand extends Command {

    public MoneyCommand() {
        super(
                "money",
                LanguageManager.translate("command.money.description")
        );
        this.setAliases(new String[]{"para"});
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        MoneyForm.openForm(player);
        return true;
    }
}