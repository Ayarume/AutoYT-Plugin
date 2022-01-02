package pl.ayarume.youtubebot.utils;

import org.json.JSONException;
import org.json.JSONObject; // te 2 beda potrzebne to po chuj tamto błędy wywala XD
import pl.ayarume.youtubebot.config.ConfigManager;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class YouTubeUtils {
    public static Object[] getInfo(String id) throws IOException, JSONException {
        URL url = new URL("https://www.googleapis.com/youtube/v3/videos?id=" + id + "&part=snippet,statistics,status,contentDetails&key=" + ConfigManager.getApi_key());
        String raw = "";

        for (Scanner scan = new Scanner(url.openStream()); scan.hasNextLine(); raw = raw + scan.nextLine()) {
        }

        JSONObject item = (new JSONObject(raw)).getJSONArray("items").getJSONObject(0);
        Object[] o = new Object[]{item.getJSONObject("snippet").getString("title"), item.getJSONObject("snippet").getString("description"), item.getJSONObject("statistics").getInt("viewCount"), item.getJSONObject("status").getString("privacyStatus").equalsIgnoreCase("public") ? StatusEnum.PUBLIC : StatusEnum.UNLISTED, parseTime(item.getJSONObject("contentDetails").getString("duration")), item.getJSONObject("snippet").getString("channelId")};
        return o;
    }

    public static Object[] getInfoSub(String id) throws IOException, JSONException {
        URL url = new URL("https://www.googleapis.com/youtube/v3/channels?part=statistics&id=" + id + "&key=" + ConfigManager.getApi_key());
        String raw = "";

        for (Scanner scan = new Scanner(url.openStream()); scan.hasNextLine(); raw = raw + scan.nextLine()) {
        }

        JSONObject item = (new JSONObject(raw)).getJSONArray("items").getJSONObject(0);
        Object[] o = new Object[]{item.getJSONObject("statistics").getInt("subscriberCount"), item.getJSONObject("statistics").getBoolean("hiddenSubscriberCount")};
        return o;
    }

    private static int parseTime(String s) {
        String time = s.replace("PT", "");
        int hours = 0;
        if (time.contains("H")) {
            hours = Integer.valueOf(time.split("H")[0]);
            time = time.split("H")[1];
        }

        int minutes = 0;
        if (time.contains("M")) {
            minutes = Integer.valueOf(time.split("M")[0]);
            time = time.split("M")[1];
        }

        int seconds = Integer.valueOf(time.replace("S", ""));
        return hours * 3600 + minutes * 60 + seconds;
    }
}
