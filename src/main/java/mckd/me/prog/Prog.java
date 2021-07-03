package mckd.me.prog;

import mckd.me.prog.Worlds.*;
import mckd.me.prog.Worlds.Build.BuildBattle;

import mckd.me.prog.Worlds.Build.BuildScheduler;
import mckd.me.prog.Worlds.Raid.Raid;

import org.bukkit.plugin.java.JavaPlugin;


public final class Prog extends JavaPlugin {

    private static Prog main;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("hello");
        new MainWorld(this);
        new TestWorld(this);
        new TntWorld(this);
        new SnowWorld(this);
        //new AnvilWorld(this);
        new FallColorWorld(this);
        new Raid(this);
        new BuildBattle(this);
        new TPvp(this);
        main = this;
        getCommand("count").setExecutor(new BuildScheduler());


    }




    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static Prog getPlugin() {
        return main;
    }

}

