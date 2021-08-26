package mckd.me.prog.Worlds;

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
                p.sendTitle(args[0],"",0,40,0);
            }
            return true;
        }
        return true;
    }
}
