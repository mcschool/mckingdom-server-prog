package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class TestWorld implements Listener {
    public TestWorld(Prog plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equals("test")) {
            Player player = e.getPlayer();
            int count = this.getPlayer(player);
            player.sendMessage("Hello" + String.valueOf(count));
        }
    }

    @EventHandler
    public void sendMessage(BlockBreakEvent e) {
        if (e.getPlayer().getWorld().getName().equals("test")) {
            Player player = e.getPlayer();
            World world = e.getPlayer().getWorld();
            Location loc1 = player.getLocation();
            Location loc2 = new Location(world, loc1.getX(), loc1.getY() + 10, loc1.getZ());
            player.teleport(loc2);
        }
    }

        private int getPlayer (Player player){
            int playerCount = player.getWorld().getPlayers().size();
            return playerCount;
        }
    }
