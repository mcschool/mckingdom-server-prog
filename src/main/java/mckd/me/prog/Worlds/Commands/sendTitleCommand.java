package mckd.me.prog.Worlds.Commands;

import org.bukkit.Color;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class sendTitleCommand {
    public static boolean command(CommandSender sender, Command command, String label, String args[]) {
        if(args.length == 1){
            World world = ((Player) sender).getWorld();
            List<Player> players = world.getPlayers();
            for(Player p: players){
                p.sendTitle(Color.ORANGE + "" + args[0],"",0,40,0);
            }
            return true;
        }
        sender.sendMessage("§c使い方: /sendTitle <表示したい文字>");
        return true;
    }
}
