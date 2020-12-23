package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
<<<<<<< HEAD
import org.bukkit.material.Wool;
=======
import org.bukkit.inventory.ItemStack;
>>>>>>> beb05de8c3112f5c98f19598671daff9f13bead7

import java.util.Random;

public class FallColorWorld implements Listener {
    private Prog plugin;
    public String worldName = "fallColor";
    public Location startPlace;

    public FallColorWorld(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.startPlace = new Location(Bukkit.getWorld(this.worldName), 87, 70, 111);
    }

    //待合所にテレポート
    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Random rand = new Random();
            int colors = rand.nextInt(3);
            String colorName;
            if (colors == 0) {
                colorName = "red";
            } else if (colors == 1) {
                colorName = "blue";
            } else {
                colorName = "green";
                Player player = e.getPlayer();
                player.sendTitle("FallColor", "", 20, 20, 20);
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
                FileConfiguration config = this.plugin.getConfig();
                config.set(player.getUniqueId() + "test", colors);
                player.sendMessage(player.getUniqueId().toString());
            }
        }
        //着地したとき
        public void FallPlayer(PlayerMoveEvent e){
            Player player = e.getPlayer();
            World world = player.getWorld();
            if (e.getPlayer().getWorld().getName().equals("fallColor")) {
                Location location = e.getPlayer().getLocation().clone().subtract(0, 0, 0);
                if (location.getY() <= 4) {
                    player.teleport(this.startPlace);
                    this.ChangeColor(player);
                    FileConfiguration config = this.plugin.getConfig();
                    String color = config.getString("test");
                    player.sendMessage(color);
                }
            }
        }
        //色分け
        public void ChangeColor (Player player){
            FileConfiguration config = this.plugin.getConfig();
            String color = config.getString(player.getUniqueId() + "test");
            player.sendMessage(color);
            World world = player.getWorld();
            Location location = player.getLocation().clone().subtract(0, 0, 0);
            Block block = world.getBlockAt(location);
            block.setType(Material.WOOL);
            BlockState blockState = block.getState();
            if (color.equals("red")) {
                blockState.setData(new Wool(DyeColor.RED));
            } else if (color.equals("blue")) {
                blockState.setData(new Wool(DyeColor.BLUE));
            } else {
                blockState.setData(new Wool(DyeColor.BLACK));
            }
            blockState.update();
        }

        //ダメージ無効化
        @EventHandler
        public void onEntityDamage (EntityDamageEvent e){
            if (e.getEntity().getWorld().getName().equals(this.worldName)) {
                if (!(e.getEntity() instanceof Player)) {
                    return;
                }
                if (e.getCause() != null && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    e.setCancelled(true);
                }
            }
        }
        //羊毛を踏んだ時
        @EventHandler
        public void onStepColor (PlayerMoveEvent e){
            Player player = e.getPlayer();
            World world = player.getWorld();
            if (e.getPlayer().getWorld().getName().equals("fallColor")) {
                Location location = e.getPlayer().getLocation().clone().subtract(0, +1, 0);
                Block block = location.getBlock();
                if (block.getType() == Material.WOOL) {
                    player.setGameMode(GameMode.SPECTATOR);
                }
            }
        }
    }
}


