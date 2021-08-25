package mckd.me.prog.Worlds.Build;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BuildPlayingCommand {


    public Boolean isPlaying = false;


    public static boolean command(CommandSender sender, Command command, String label, String args[]) {

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("false")) {
                sender.sendMessage("aa");
            } else {
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
