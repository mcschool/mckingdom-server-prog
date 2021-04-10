package mckd.me.prog;

import mckd.me.prog.Worlds.*;
import mckd.me.prog.Worlds.OneNightJinro.ActionBar;
import mckd.me.prog.Worlds.OneNightJinro.MConJinro;
import mckd.me.prog.Worlds.OneNightJinro.event.Event;
import mckd.me.prog.Worlds.Raid.Raid;
import org.bukkit.plugin.java.JavaPlugin;

public final class Prog extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("hello");
        new MainWorld(this);
        new TestWorld(this);
        new TntWorld(this);
        new SnowWorld(this);
        new AnvilWorld(this);
        new FallColorWorld(this);
        new Raid(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
