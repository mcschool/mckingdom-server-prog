package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class TntWorld implements Listener {
    public String worldName = "Tnt";
    public Location startPlace ;
    public TntWorld(Prog plugin){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.startPlace = new Location(Bukkit.getWorld(this.worldName),-263,51,1088);
    }
    //待合所にテレポート
    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent e){
        if(e.getPlayer().getWorld().getName().equals(this.worldName)){
            Player player = e.getPlayer();
            player.teleport(this.startPlace);
        }
    }
}
