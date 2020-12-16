package mckd.me.prog.Worlds;

import jdk.javadoc.internal.doclets.formats.html.SearchIndexItems;
import mckd.me.prog.Prog;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import sun.jvm.hotspot.ui.ObjectHistogramPanel;

import java.util.List;

public class FallColorWorld implements Listener {
    private Prog plugin;
    public  String worldName = "fallColor";
    public Location startPlace;

    public  FallColorWorld(Prog plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.startPlace = new Location(Bukkit.getWorld(this.worldName), 87,70,111);
    }
    //待合所にテレポート
    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            player.sendTitle("FallColor","",20,20,20);
            player.sendMessage("ここから落ちたら地面に色がつくよ！");
            player.sendMessage("次落ちるときは色が付いたところに着地しないでね！");
            player.teleport(this.startPlace);
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.setFlying(false);
            player.setPlayerWeather(WeatherType.CLEAR);
            player.getWorld().setPVP(false);
            player.setGameMode(GameMode.SURVIVAL);
            player.setPlayerWeather(WeatherType.CLEAR);
            }
    }
    //着地したとき
    @EventHandler
    public  void FallPlayer(PlayerMoveEvent e){
          Player player = e.getPlayer();
          World world = player.getWorld();
          if (e.getPlayer().getWorld().getName().equals("fallColor")){
              Location location = e.getPlayer().getLocation().clone().subtract(0,0,0);
            if (location.getY()<=4){
                player.teleport(this.startPlace);
                ItemStack RedWool = new ItemStack(Material.WOOL,1,(byte)4);
                world.getBlockAt(location).setType(RedWool.getType());


            }
        }
    }
    //ダメージ無効化
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if (e.getEntity().getWorld().getName().equals(this.worldName)){
            if (!(e.getEntity() instanceof Player)){
                return;
            }
            if (e.getCause() != null && e.getCause() == EntityDamageEvent.DamageCause.FALL){
                e.setCancelled(true);
            }
        }
    }
    //色を踏んだ
    @EventHandler
    public  void onStepColor(PlayerMoveEvent e){
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (e.getPlayer().getWorld().getName().equals("fallColor")){
            Location location = e.getPlayer().getLocation().clone().subtract(0,0,0);
            Block block = location.getBlock();
            if (block.getType() == Material.WOOL){
                player.setGameMode(GameMode.SPECTATOR);
            }
        }
    }
}
