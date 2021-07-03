package mckd.me.prog.Worlds.Build;

import mckd.me.prog.Prog;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BuildScheduler extends JavaPlugin {
    public boolean timerFlag;//個人的に変数をpublicにするの好きじゃないけどわかりやすくするためこのようにしてる。
    @Override
    public void onEnable() {
        super.onEnable();
        getCommand("timer").setExecutor(new TimerCommand(this));//timerコマンドを受け付けたらTimerCommandのonCommandが実行されるように

        new MoveListener(this);//足元のブロックを消すためのリスナのインスタンスを作成
        //リスナの登録はコンストラクタ内で行っているため、ここでは不要
    }
    @Override
    public void onDisable() {
        super.onDisable();
    }

}

class TimerCommand implements CommandExecutor{
    BukkitTask task;
    JavaPlugin plugin;
    final int INIT_SEC = 5;//とりあえず初期値

    public TimerCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
                             String[] arg3) {
        /*
         * コマンドは./timer <秒数>とする
         * もし秒数が指定されていない場合はデフォルトの秒数でタイマーをスタートする
         */
        Timer timer;//何度もコマンドを実行するとメモリにゴミが残ってちょっと不安だけど簡単だからこの方法を採用
        /*
         * 本当はもう少しスッキリとかけるがわかりにくくなるかもしれないのでこのまま
         */
        if(arg3.length == 0){
            //timerのみ
            timer = new Timer(plugin, INIT_SEC);
            task = plugin.getServer().getScheduler().runTaskTimer(plugin, timer, 0L, 20L);

            timer.setTask(task);//あとで自分自身を止めさせるため参照を渡しておく

            return true;//コマンドの実行が成功したことを表す
        }else if(arg3.length == 1){
            //timer <何か>
            //足元のブロックを消さないようにするコマンド->timer reset
            int i;//とりあえず秒数を入れておく一時的な変数(初期化しないと起こられるのでとりあえず0を入れる)

            if(arg3[0].equalsIgnoreCase("reset")){
                //まずサブコマンドがresetであるかどうか判定
                //これは秒数指定の処理の前に持ってくること
                ((BuildScheduler)plugin).timerFlag = false;//falseを指定するとリスナ側でブロックを消す処理を実行しないようになる。

                return true;//コマンドが正常に終了したことをBukkitに伝えるためのtrue
            }

            try {
                i = Integer.parseInt(arg3[0]);
            } catch (NumberFormatException e) {
                //arg3[0]が整数として解析不可能だった場合Integer.parseIntはNumberFormatExceptionを投げる
                //のでその時デフォルト値を入れてあげる。
                i = INIT_SEC;
            }
            timer = new Timer(plugin, i);
            task = plugin.getServer().getScheduler().runTaskTimer(plugin, timer, 0L, 20L);

            timer.setTask(task);//あとで自分自身を止めさせるため参照を渡しておく

            return true;//コマンドの実行が成功したことを表す
        }

        return false;
    }

}

//都合によりちょっと移動
class Timer extends BukkitRunnable{
    int time;//秒数
    JavaPlugin plugin;//BukkitのAPIにアクセスするためのJavaPlugin
    BukkitTask task;//自分自身を止めるためのもの
    public Timer(JavaPlugin plugin ,int i) {
        this.time = i;
        this.plugin = plugin;
    }
    @Override
    public void run() {
        if(time <= 0){
            //タイムアップなら
            plugin.getServer().broadcastMessage("Start!");//Start!と全員に表示
            plugin.getServer().getScheduler().cancelTask(task.getTaskId());//自分自身を止める

            //ここからタイマーが終了した際に行う処理
            ((BuildScheduler)plugin).timerFlag = true;//JavaPluginだとMainクラスの独自の変数、メソッドにアクセス出来ないためキャスト

            //ここまで
        }else{
            plugin.getServer().broadcastMessage("" + time);//残り秒数を全員に表示
        }
        time--;//1秒減算
    }
    //もっときれいなやり方ありそうだけどすぐに思いついたのがこれ
    public void setTask(BukkitTask task) {
        this.task = task;
    }
}
class PlayerDeathListener implements Listener{
    JavaPlugin plugin;
    public PlayerDeathListener(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        EntityDamageEvent.DamageCause dc = p.getLastDamageCause().getCause();//最後に受けたダメージの種類の取得
        if(dc == EntityDamageEvent.DamageCause.VOID){//確か奈落に落ちた時のダメージはvoidだった気がする
            //奈落に落ちたのであれば
            p.setMetadata("ignore", new FixedMetadataValue(plugin, true));//ignoreタグにtrueを立たせる
        }
    }
}
class MoveListener implements Listener {
    JavaPlugin plugin;

    public MoveListener(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(((BuildScheduler)plugin).timerFlag){


            Player p = event.getPlayer();

            if(p.hasMetadata("ignore") && p.getMetadata("ignore").get(0).asBoolean())return;//ignoreフラグが立っているならこれ以降を実行しない
            //もしかしたらp.getMetadata("ignore").get(0).asBoolean()の部分でぬるぽで落ちるかもしれないので調整するといいかも

            //タイマーが終了したフラグが立っているなら
            //プレイヤーが動く際に
            Location blockLoc = p.getLocation().add(0, -1, 0);//足元のブロックを指定
            /*
             * note:この方法だと向きを変えたり、少しでも動くとすぐに足元のブロックが消えてしまうため、
             * 		スケジューラを使用して若干ブロックを消すのを遅らせると実用的になると思います。
             * 		具体的には↓のような感じ
             */
            plugin.getServer().getScheduler().runTaskLater(plugin, new DeleteBlock(blockLoc), 20L);//1秒後にDeleeBlockのrunメソッドが動作するようにする
        }
    }
}
class DeleteBlock extends BukkitRunnable {
    Location loc;//消すブロックのロケーション

    public DeleteBlock(Location loc) {
        this.loc = loc;
    }

    @Override
    public void run() {
        loc.getBlock().setType(Material.AIR);
    }
}