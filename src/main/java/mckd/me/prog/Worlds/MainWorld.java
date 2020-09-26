package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.event.Listener;

public class MainWorld implements Listener {

    public MainWorld(Prog plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

}
