package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class MainWorld implements Listener {

    public MainWorld(Prog plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player player=e.getPlayer();
        player.sendMessage("ブロック置いたね");
    }
}
