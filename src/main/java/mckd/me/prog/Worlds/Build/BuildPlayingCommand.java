package mckd.me.prog.Worlds.Build;

import mckd.me.prog.Prog;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildPlayingCommand {


    public static boolean command(CommandSender sender, Command command, String label, String args[]) {

        if (args.length == 1) {
            sender.sendMessage("1");
            if (args[0].equalsIgnoreCase("true")) {
                sender.sendMessage("true1");
                Player player = (Player)sender;
                BuildBattle buildBattle = new BuildBattle(Prog.getPlugin());
                buildBattle.isPlayingtrue(player);
            }
            if (args[0].equalsIgnoreCase("false")){
                sender.sendMessage("false1");
            }
            else {
                sender.sendMessage("§ctrueもしくはfalseを入れてください");
                return true;
            }
        } else {
            sender.sendMessage("§c使い方: /playingBuild <true もしくは false>");
            return true;
        }
        return true;
    }
}
