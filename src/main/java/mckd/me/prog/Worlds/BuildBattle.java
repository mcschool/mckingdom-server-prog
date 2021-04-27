package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class BuildBattle implements Listener {
    private Prog plugin;
    public String worldName = "Build";
    public Location changePlace;

    public BuildBattle(Prog plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.changePlace = new Location(Bukkit.getWorld(this.worldName),-20,10,538);
    }

    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent e){
        if(e.getPlayer().getWorld().getName().equals(this.worldName)){
            Player player = e.getPlayer();
            player.teleport(this.changePlace);
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.setGameMode(GameMode.ADVENTURE);

        }

    }

    @EventHandler
    public void PlayerMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();
        if(e.getPlayer().getWorld().getName().equals(this.worldName)){
            if(location.getY() >70){
                double x = location.getX();
                double y = 5;
                double z = location.getZ();
                player.sendMessage(ChatColor.RED + "これより上は行けないよ！(パクリ対策)");
                Location loc = new Location(Bukkit.getWorld(this.worldName),x,y,z);
                player.teleport(loc);
            }
        }
    }
}
