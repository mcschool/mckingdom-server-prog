package mckd.me.prog.Worlds.Build;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BuildPlayingCommand {


    public Boolean isPlaying = false;


    public static boolean command(CommandSender sender, Command command, String label, String args[]) {

        if(args.equals("true")){
            sender.sendMessage("aa");
        } else{
            sender.sendMessage("trueもしくはfalseに変更してください");
            return true;
        }
        return true;
    }
}
