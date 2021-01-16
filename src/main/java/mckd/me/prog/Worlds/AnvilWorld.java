package mckd.me.prog.Worlds;

import com.sun.tools.javac.jvm.Items;
import mckd.me.prog.Prog;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AnvilWorld implements Listener {
    private Prog plugin;
    public String worldName = "anvil";
    public Location StartPlace;
    public Location centerPlace;
    public boolean isPlaying = false;

    public AnvilWorld(Prog plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.centerPlace = new Location(Bukkit.getWorld(this.worldName), -520, 50, 1292);
        this.StartPlace = new Location(Bukkit.getWorld(this.worldName), -473, 53, -1289);
    }


    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            player.teleport(this.StartPlace);
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.setPlayerWeather(WeatherType.CLEAR);
            World world = player.getWorld();
            world.setTime(30000);
            ItemStack itemStack = new ItemStack(Material.ANVIL);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("ゲームスタート");
            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);

        }
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            Block block = e.getBlock();
            if (block.getType() == Material.STONE) {
                player.sendMessage("anvil1");
                player.setGameMode(GameMode.CREATIVE);
            }
        }
    }


    @EventHandler
    public void interactBlock(PlayerInteractEvent e) {
        if (e.getPlayer().getWorld().getName().equals(this.worldName)) {
            Player player = e.getPlayer();
            ItemStack item = e.getItem();
            if (item.getType() == Material.ANVIL) {
                int NowPlayerCount = player.getWorld().getPlayers().size();
                if (isPlaying == true) {
                    player.sendMessage("ゲームがおわるまでまってね");
                } else {
                    if (NowPlayerCount >= 1) {
                        isPlaying = true;
                        this.startGame();
                        player.sendTitle("Gamestart", "ゲームスタート", 20, 20, 20);
                        player.sendMessage("移動するよ");
                    } else {
                        isPlaying = false;
                        player.sendMessage("2人まで待ってね");
                    }
                }
            }

        }
    }

    public void startGame() {
        World world = Bukkit.getWorld("Anvil");
        List<Player> players = world.getPlayers();
        for (Player player : players) {
            player.teleport(new Location(Bukkit.getWorld("Anvil"), -520, 5, -1292));


        }


    }
}


