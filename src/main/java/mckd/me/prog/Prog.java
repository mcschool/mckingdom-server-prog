package mckd.me.prog;

import mckd.me.prog.Worlds.*;
import mckd.me.prog.Worlds.Build.BuildBattle;

import mckd.me.prog.Worlds.Build.BuildCommand;
import mckd.me.prog.Worlds.Build.BuildPlayingCommand;
import mckd.me.prog.Worlds.Raid.Raid;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public final class Prog extends JavaPlugin {

    private static Prog main;
    private BuildBattle buildBattle;

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
        buildBattle =  new BuildBattle(this);
        new TPvp(this);


    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("count")) {
            BuildCommand.command(sender, command, label, args);
        }
        if (command.getName().equalsIgnoreCase("playingBuild")){
            BuildPlayingCommand.command(sender,command,label,args,buildBattle);
        }
        return true;
    }




    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}

