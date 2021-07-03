package mckd.me.prog.Worlds.Build;

import mckd.me.prog.Prog;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BuildScheduler extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        getCommand("timer").setExecutor(new TimerCommand(this));//timerコマンドを受け付けたらTimerCommandのonCommandが実行されるように

    }
    @Override
    public void onDisable() {
        super.onDisable();
    }

}

class TimerCommand implements CommandExecutor {
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
            int i;//とりあえず秒数を入れておく一時的な変数(初期化しないと起こられるのでとりあえず0を入れる)

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
