package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

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
}
