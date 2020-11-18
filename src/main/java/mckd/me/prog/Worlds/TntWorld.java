package mckd.me.prog.Worlds;

import mckd.me.prog.Prog;
import org.bukkit.event.Listener;

public class TntWorld implements Listener {
    public TntWorld(Prog plugin){ plugin.getServer().getPluginManager().registerEvents(this,plugin);

    }
}
