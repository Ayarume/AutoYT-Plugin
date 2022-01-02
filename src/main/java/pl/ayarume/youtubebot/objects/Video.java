package pl.ayarume.youtubebot.objects;

import java.util.UUID;

public class Video {
   private final UUID uuid;
   private final String id;

   public Video(UUID uuid, String id) {
      this.uuid = uuid;
      this.id = id;
   }

   public UUID getUuid() {
      return this.uuid;
   }

   public String getId() {
      return this.id;
   }
}
