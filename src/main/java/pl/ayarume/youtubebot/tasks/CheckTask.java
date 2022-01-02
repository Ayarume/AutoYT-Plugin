package pl.ayarume.youtubebot.tasks;

import java.io.IOException;
import java.util.Iterator;
import pl.ayarume.youtubebot.Main;
import pl.ayarume.youtubebot.config.ConfigManager;
import pl.ayarume.youtubebot.objects.Video;
import pl.ayarume.youtubebot.objects.VideoManager;
import pl.ayarume.youtubebot.utils.StatusEnum;
import pl.ayarume.youtubebot.utils.YouTubeUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONException;

public class CheckTask extends BukkitRunnable {
   public void run() {
      Iterator var2 = VideoManager.getVideos().values().iterator();

      while(var2.hasNext()) {
         Video v = (Video)var2.next();
         this.main(v);
      }

   }

   private void main(Video v) {
      String title;
      StatusEnum status;
      int views;
      String desc;
      try {
         Object[] o = YouTubeUtils.getInfo(v.getId());
         title = (String)o[0];
         desc = (String)o[1];
         views = (Integer)o[2];
         status = (StatusEnum)o[3];
      } catch (JSONException | IOException var7) {
         VideoManager.removeVideo(v);
         this.ban(v);
         return;
      }

      if (!title.toLowerCase().contains(ConfigManager.getNeeded_title().toLowerCase())) {
         this.ban(v);
         VideoManager.removeVideo(v);
      } else if (!desc.toLowerCase().contains(ConfigManager.getNeeded_desc().toLowerCase())) {
         this.ban(v);
         VideoManager.removeVideo(v);
      } else if (views < ConfigManager.getNeeded_views()) {
         this.ban(v);
         VideoManager.removeVideo(v);
      } else {
         if (status != StatusEnum.PUBLIC) {
            VideoManager.removeVideo(v);
            this.ban(v);
         }

      }
   }

   private void ban(Video v) {
      Bukkit.getScheduler().runTask(Main.getInst(), () -> {
         Iterator var2 = ConfigManager.getErr_cmds().iterator();

         while(var2.hasNext()) {
            String s = (String)var2.next();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("{nick}", Bukkit.getOfflinePlayer(v.getUuid()).getName()));
         }

      });
   }
}
