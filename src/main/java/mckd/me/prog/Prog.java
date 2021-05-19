package mckd.me.prog;

import mckd.me.prog.Worlds.*;
import mckd.me.prog.Worlds.Build.BuildBattle;
import mckd.me.prog.Worlds.OneNightJinro.GameStatus;
import mckd.me.prog.Worlds.OneNightJinro.JinroConfig;
import mckd.me.prog.Worlds.OneNightJinro.command.JinroAdminCommand;
import mckd.me.prog.Worlds.OneNightJinro.command.JinroCommand;
import mckd.me.prog.Worlds.OneNightJinro.command.JinroToJobALLChatCommand;
import mckd.me.prog.Worlds.OneNightJinro.event.Event;
import mckd.me.prog.Worlds.OneNightJinro.player.JinroPlayers;
import mckd.me.prog.Worlds.OneNightJinro.scoreboard.JinroScoreboard;
import mckd.me.prog.Worlds.OneNightJinro.task.BaseTask;
import mckd.me.prog.Worlds.Raid.Raid;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public final class Prog extends JavaPlugin {

    private static Prog main;
    private static BaseTask timerTask = null;
    private static JinroScoreboard js;
    private HashMap<String, TabExecutor> commands;
    private static boolean isDEBUG = false;

    private static World world;

    public static String getPrefix() {
        return ChatColor.GREEN + "[" + ChatColor.AQUA + "OneNightJinro" + ChatColor.GREEN + "] " + ChatColor.WHITE;
    }

    public static void sendGameMaster(String s, boolean isLogging){
        for( Player p : Bukkit.getOnlinePlayers() ){
            if( JinroPlayers.isGameMaster(p) ){
                p.sendMessage( s );
            }
        }
        if( isLogging ){
            sendConsole(s);
        }
    }

    public static void sendConsole(String s){
        getMain().getServer().getConsoleSender().sendMessage(s);
    }

    public static void sendGameMaster(String s){
        sendGameMaster(s, true);
    }

    public static void setTask(BaseTask task) { timerTask = task;
    }

    public static BaseTask getTask() {
        return timerTask;
    }

    public static JinroScoreboard getScoreboard() {
        return js;
    }

    public static void setScoreboard(JinroScoreboard js) {
        Prog.js = js;
    }



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
        new BuildBattle(this);
        main = this;

        // ワールド読み込み
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    world = Bukkit.getWorld(getConfig().getString("spawnpoint.world"));
                    if (world == null) {
                        world = Bukkit.getWorlds().get(0);
                    }
                } catch (IllegalArgumentException e) {
                    world = Bukkit.getWorlds().get(0);
                }

                if(getConfig().get("spawnpoint.x") == null ||
                        getConfig().get("spawnpoint.y") == null ||
                        getConfig().get("spawnpoint.z") == null) {
                    setRespawnLoc( world.getSpawnLocation() );
                    getConfig().set("spawnpoint.world", world.getName() );
                    getConfig().set("spawnpoint.x", world.getSpawnLocation().getX() );
                    getConfig().set("spawnpoint.y", world.getSpawnLocation().getY() );
                    getConfig().set("spawnpoint.z", world.getSpawnLocation().getZ() );
                    saveConfig();
                } else if( getConfig().get("spawnpoint.yaw") == null ||
                        getConfig().get("spawnpoint.pitch") == null ) {
                    World w;
                    try {
                        w = Bukkit.getWorld(getConfig().getString("spawnpoint.world"));
                        if (w == null) {
                            w = Bukkit.getWorlds().get(0);
                        }
                    } catch (IllegalArgumentException e) {
                        w = Bukkit.getWorlds().get(0);
                    }
                    setRespawnLoc(new Location(w, getConfig().getDouble("spawnpoint.x"),
                            getConfig().getDouble("spawnpoint.y"),
                            getConfig().getDouble("spawnpoint.z")
                    ));
                } else {
                    World w;
                    try {
                        w = Bukkit.getWorld(getConfig().getString("spawnpoint.world"));
                        if (w == null) {
                            w = Bukkit.getWorlds().get(0);
                        }
                    } catch (IllegalArgumentException e) {
                        w = Bukkit.getWorlds().get(0);
                    }
                    setRespawnLoc(new Location(w, getConfig().getDouble("spawnpoint.x"),
                            getConfig().getDouble("spawnpoint.y"),
                            getConfig().getDouble("spawnpoint.z"),
                            Float.parseFloat(getConfig().getString("spawnpoint.yaw")),
                            Float.parseFloat(getConfig().getString("spawnpoint.pitch"))
                    ));
                }
                init();
                JinroPlayers.init();
            }
        }.runTaskAsynchronously(this);

        commands = new HashMap<String, TabExecutor>();
        commands.put("jinro", new JinroCommand(this));
        commands.put("jinro_ad", new JinroAdminCommand(this));
        commands.put("c", new JinroToJobALLChatCommand(this));

        saveDefaultConfig();
        reloadConfig();

        isDEBUG = getConfig().getBoolean("Debug");

        getServer().getPluginManager().registerEvents(new Event(), this);
    }

    public static Prog getMain() {
        return main;
    }

    public static boolean isDEBUG() {
        return isDEBUG;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commands.get(command.getName()).onCommand(sender,command,label,args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return commands.get(command.getName()).onTabComplete(sender, command, alias, args);
    }

    public static void init(){
        Prog.getMain().reloadConfig();
        Scoreboard s = JinroScoreboard.getScoreboard();
        Objective obj = s.getObjective( Prog.getMain().getConfig().getString(JinroConfig.InfoObjectiveName.getPath()) );
        if( obj != null ){
            obj.unregister();
        }
        obj = s.registerNewObjective(Prog.getMain().getConfig().getString(JinroConfig.InfoObjectiveName.getPath()),"dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("情報");
        GameStatus.setStatus(GameStatus.Ready);
        GameStatus.Cycle.setCycle(GameStatus.Cycle.Ready);
        Prog.getRespawnLoc().getWorld().setTime(0);
        JinroPlayers.init();
        for( Player p : Bukkit.getOnlinePlayers() ){
            p.setPlayerListName(p.getName() + " ");
            if( p.hasPermission("Jinro.GameMaster") ){
                p.setPlayerListName(ChatColor.YELLOW + "[GM] " + p.getName() + " ");
            }
            if (p.getGameMode().equals(GameMode.SPECTATOR)) {
                p.setGameMode(GameMode.ADVENTURE);
                TeleportToRespawn(p);
            }
        }
        if( Prog.getTask() != null ) {
            Prog.getTask().stop();
            Prog.setTask(null);
        }
    }

    private static Location ReikaiLoc = null;
    private static Location RespawnLoc = null;

    public static void TeleportToReikai(Player p){
        if( getReikaiLoc() == null ){
            p.teleport(p.getWorld().getSpawnLocation());
        }
        p.teleport(getReikaiLoc());
    }

    public static void TeleportToRespawn(Player p){
        if( getRespawnLoc() == null ){
            p.teleport(p.getWorld().getSpawnLocation());
        }
        p.teleport(getRespawnLoc());
    }

    public static void setReikaiLoc(Location loc){
        Prog.getMain().getConfig().set("reikai.world", loc.getWorld().getName());
        Prog.getMain().getConfig().set("reikai.x", loc.getX());
        Prog.getMain().getConfig().set("reikai.y", loc.getY());
        Prog.getMain().getConfig().set("reikai.z", loc.getZ());
        Prog.getMain().getConfig().set("reikai.yaw", loc.getYaw());
        Prog.getMain().getConfig().set("reikai.pitch", loc.getPitch());
        try {
            Prog.getMain().getConfig().save( Prog.getMain().getDataFolder() + File.separator + "config.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReikaiLoc = loc;
    }

    public static void setRespawnLoc(Location loc) {
        world = loc.getWorld();
        Prog.getMain().getConfig().set("spawnpoint.world", loc.getWorld().getName());
        Prog.getMain().getConfig().set("spawnpoint.x", loc.getX());
        Prog.getMain().getConfig().set("spawnpoint.y", loc.getY());
        Prog.getMain().getConfig().set("spawnpoint.z", loc.getZ());
        Prog.getMain().getConfig().set("spawnpoint.yaw", loc.getYaw());
        Prog.getMain().getConfig().set("spawnpoint.pitch", loc.getPitch());
        try {
            Prog.getMain().getConfig().save(Prog.getMain().getDataFolder() + File.separator + "config.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        RespawnLoc = loc;
    }

    public static Location getReikaiLoc(){
        return ReikaiLoc;
    }

    public static Location getRespawnLoc(){
        return RespawnLoc;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
