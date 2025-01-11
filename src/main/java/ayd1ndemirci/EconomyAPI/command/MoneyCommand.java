package ayd1ndemirci.EconomyAPI.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import ayd1ndemirci.EconomyAPI.forms.MoneyForm;

public class MoneyCommand extends Command {

    public MoneyCommand() {
        super("para", "Ekonomi ekranı");
        this.setAliases(new String[]{"money"});
    }

    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        MoneyForm.openForm(player);
        return true;
    }
}
