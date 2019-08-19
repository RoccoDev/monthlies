package tk.roccodev.hiveserver.bmont;

import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Disqualified {
    private static List<String> players = new ArrayList<>();

    public static void download() {
        try {
            URL url = new URL("https://rocco.dev/archive/bedwars/disqualified");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("User-Agent", "MonthliesBot/1.0");

            if(conn.getResponseCode() == 200) {
                players.addAll(IOUtils.readLines(conn.getInputStream(), "UTF-8"));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isDisqualified(String uuid) {
        return players.contains(uuid);
    }
}
