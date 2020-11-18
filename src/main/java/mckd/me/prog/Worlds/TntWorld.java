package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class TntWorld implements Listener {
    public String worldName = "tnt";
    public Location startPlace ;
    public TntWorld(Prog plugin){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.startPlace = new Location(Bukkit.getWorld(this.worldName),-263,51,1088);
    }
    //待合所にテレポート
    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            player.teleport(this.startPlace);
        }
    }
    @EventHandler
    public void breakBlock(BlockBreakEvent e){
        if (e.getPlayer().getWorld().getName().equals(this.worldName)){
            Player player = e.getPlayer();
            Block block = e.getBlock();
            if (block.getType() == Material.GLASS){
                player.sendMessage("ブロックを壊したよ");
            }else{
                player.sendMessage("はずれ");
            }
        }
    }
}
