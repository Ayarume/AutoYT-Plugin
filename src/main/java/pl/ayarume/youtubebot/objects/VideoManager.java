package pl.ayarume.youtubebot.objects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import pl.ayarume.youtubebot.mysql.MySQLManager;

public class VideoManager {
   private static Map<UUID, Video> videos = new HashMap();

   public static Video createVideo(UUID uuid, String id) {
      Video v = new Video(uuid, id);
      videos.put(uuid, v);
      return v;
   }

   public static boolean isRegistered(String id) {
      Iterator var2 = videos.values().iterator();

      while(var2.hasNext()) {
         Video v = (Video)var2.next();
         if (v.getId().equalsIgnoreCase(id)) {
            return true;
         }
      }

      return false;
   }

   public static Video getVideo(UUID uuid) {
      return (Video)videos.get(uuid);
   }

   public static boolean hasRegistered(UUID uuid) {
      return videos.get(uuid) != null;
   }

   public static Map<UUID, Video> getVideos() {
      return videos;
   }

   public static void removeVideo(Video v) {
      MySQLManager.getMySQL().remove(v);
      videos.remove(v.getUuid());
   }
}
