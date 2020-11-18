package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TntWorld implements Listener {
    private Prog plugin;
    public String worldName = "tnt";
    public Location startPlace;

    public TntWorld(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.startPlace = new Location(Bukkit.getWorld(this.worldName), -263, 51, 1088);
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
    public void breakBlock(BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            Block block = e.getBlock();
            if (block.getType() == Material.GRASS) {
                player.sendTitle("GameStart", "ゲームスタート", 20, 20, 20);
                player.sendMessage("移動するよ");
                this.startGame();
            } else {
                player.sendMessage("はずれ");
            }
        }
    }

    public void startGame() {
        World world = Bukkit.getWorld("tnt");
        List<Player> players = world.getPlayers();

        for (Player player : players) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(new Location(Bukkit.getWorld("tnt"), -265, 40, 1047));
                }
            }.runTaskLater(this.plugin, 20);
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        if (e.getBlock().getWorld().getName().equals("tnt")) {
            e.setCancelled(true);
        }
    }
}