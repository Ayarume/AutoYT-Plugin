package pl.ayarume.youtubebot.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import pl.ayarume.youtubebot.Main;
import pl.ayarume.youtubebot.objects.Video;
import pl.ayarume.youtubebot.objects.VideoManager;
import org.bukkit.Bukkit;

public class MySQL {
   private String host;
   private int port;
   private String user;
   private String pass;
   private String database;
   private String url;
   private Connection conn;

   public MySQL(String host, int port, String user, String pass, String database) {
      this.host = host;
      this.port = port;
      this.user = user;
      this.pass = pass;
      this.database = database;
      this.buildURL();
      this.connect();
   }

   public void buildURL() {
      this.url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
   }

   public boolean isConnected() {
      return this.conn != null;
   }

   public boolean connect() {
      try {
         System.out.println("Using url to connect: " + this.url);
         this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
         System.out.println("Polaczono z baza danych MySQL!");
         return true;
      } catch (SQLException var2) {
         System.out.println(var2.getMessage());
         return false;
      }
   }

   public boolean disconnect() {
      if (this.isConnected()) {
         try {
            this.conn.close();
            return true;
         } catch (SQLException var2) {
            System.out.println(var2.getMessage());
         }
      }

      return false;
   }

   public void send(String sql) {
      try {
         if (this.conn.isClosed()) {
            this.conn = null;
            this.connect();
         }
      } catch (SQLException var3) {
         var3.printStackTrace();
      }

      Bukkit.getScheduler().runTaskAsynchronously(Main.getInst(), () -> {
         try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.execute();
         } catch (SQLException var3) {
            System.out.println(var3.getMessage());
            var3.printStackTrace();
         }

      });
   }

   public ResultSet query(String sql) {
      try {
         if (this.conn.isClosed()) {
            this.conn = null;
            this.connect();
         }
      } catch (SQLException var4) {
         var4.printStackTrace();
      }

      try {
         return this.conn.createStatement().executeQuery(sql);
      } catch (SQLException var3) {
         System.out.println(var3.getMessage());
         return null;
      }
   }

   public void createVideosFromDatabase() {
      try {
         int i = 0;

         for(ResultSet rs = this.query("SELECT * FROM `videos`"); rs.next(); ++i) {
            UUID uuid = UUID.fromString(rs.getString("uuid"));
            String id = rs.getString("id");
            VideoManager.createVideo(uuid, id);
         }

         System.out.println("Zaladowano " + i + " filmow z bazy danych!");
      } catch (SQLException var5) {
         System.out.println(var5.getMessage());
      }

   }

   public Object get(String tableName, String columnName, UUID uuid) {
      try {
         PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM `" + tableName + "` WHERE uuid = ?");
         ps.setString(1, uuid.toString());
         ResultSet rs = ps.executeQuery();
         rs.last();
         if (rs.getRow() != 0) {
            rs.first();
            return rs.getObject(columnName);
         }
      } catch (SQLException var6) {
         System.out.println(var6.getMessage());
      }

      return null;
   }

   public void set(String tableName, String columnName, Object newValue, UUID uuid) {
      Bukkit.getScheduler().runTaskAsynchronously(Main.getInst(), () -> {
         try {
            PreparedStatement ps = this.conn.prepareStatement("UPDATE `" + tableName + "` SET `" + columnName + "`='" + newValue + "' WHERE uuid = ?");
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
            ps.close();
         } catch (SQLException var6) {
            var6.printStackTrace();
         }

      });
   }

   public void saveDefaultData(UUID u, Video v) {
      this.send("INSERT INTO `videos` (`uuid`, `id`) VALUES ('" + u + "', '" + v.getId() + "')");
   }

   public void remove(Video v) {
      this.send("DELETE FROM `videos` WHERE `id` = '" + v.getId() + "'");
   }
}
