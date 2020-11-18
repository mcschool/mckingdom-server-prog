package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class TntWorld implements Listener {
    public TntWorld(Prog plugin){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
    //待合所にテレポート
    public void changeWorld(PlayerChangedWorldEvent e){
        if(e.getPlayer().getWorld().getName().equals("Tnt")){
            Player player = e.getPlayer();
            Location location = new Location(Bukkit.getWorld("Tnt"),-263,51,1088);
            player.teleport(location);
        }
    }
}
