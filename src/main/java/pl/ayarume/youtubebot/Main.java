package pl.ayarume.youtubebot;

import pl.ayarume.youtubebot.commands.YTCmd;
import pl.ayarume.youtubebot.commands.YouTubeCmd;
import pl.ayarume.youtubebot.config.ConfigManager;
import pl.ayarume.youtubebot.mysql.MySQLManager;
import pl.ayarume.youtubebot.tasks.CheckTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
   private static Main inst;

   public void onEnable() {
      inst = this;
      this.saveDefaultConfig();
      ConfigManager.load();
      MySQLManager.register();
      MySQLManager.getMySQL().createVideosFromDatabase();
      Bukkit.getScheduler().runTaskTimerAsynchronously(this, new CheckTask(), (long)(ConfigManager.getCheck_delay() * 20L), (long)(ConfigManager.getCheck_delay() * 20L));
      this.getCommand("ytber").setExecutor(new YouTubeCmd());
      this.getCommand("yt").setExecutor(new YTCmd());

   }

   public static Main getInst() {
      return inst;
   }
}
