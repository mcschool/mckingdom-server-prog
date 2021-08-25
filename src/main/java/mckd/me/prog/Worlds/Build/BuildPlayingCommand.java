package mckd.me.prog.Worlds.Build;

import mckd.me.prog.Prog;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildPlayingCommand {


    public static boolean command(CommandSender sender, Command command, String label, String args[]) {

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("true")) {
                Player player = (Player) sender;
                BuildBattle buildBattle = new BuildBattle(Prog.getPlugin());
                buildBattle.changetrue(player);
                return true;
            } else if (args[0].equalsIgnoreCase("false")) {
                Player player = (Player) sender;
                BuildBattle buildBattle = new BuildBattle(Prog.getPlugin());
                buildBattle.changefalse(player);
                return true;
            } else if (args[0].equalsIgnoreCase("check")) {
                Player player = (Player) sender;
                BuildBattle buildBattle = new BuildBattle(Prog.getPlugin());
                buildBattle.checkPlaying(player);
                return true;
            } else {
                sender.sendMessage("§ctrueもしくはfalseを入れてください");
                return true;
            }
        }
        sender.sendMessage("§c使い方: /playingBuild <true もしくは false>");
        return true;
    }

}
