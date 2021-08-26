package mckd.me.prog.Worlds.Commands;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GamemodeCommand {
    public static boolean command(CommandSender sender, Command command, String label, String args[]) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("0")) {
                World world = ((Player) sender).getWorld();
                List<Player> players = world.getPlayers();
                for(Player p: players){
                    p.setGameMode(GameMode.SURVIVAL);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("1")) {
                World world = ((Player) sender).getWorld();
                List<Player> players = world.getPlayers();
                for(Player p: players){
                    p.setGameMode(GameMode.CREATIVE);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("2")) {
                World world = ((Player) sender).getWorld();
                List<Player> players = world.getPlayers();
                for(Player p: players){
                    p.setGameMode(GameMode.ADVENTURE);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("3")) {
                World world = ((Player) sender).getWorld();
                List<Player> players = world.getPlayers();
                for(Player p: players){
                    p.setGameMode(GameMode.SPECTATOR);
                }
                return true;
            }
        }
        sender.sendMessage("§c使い方: 0がサバイバル、1がクリエイティブ、2がアドベンチャー、3がスペクテイター");
        sender.sendMessage("§c使い方: /allGamemode <0,1,2,3>");
        return true;
    }
}
