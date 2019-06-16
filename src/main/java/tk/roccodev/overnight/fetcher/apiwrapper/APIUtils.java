package tk.roccodev.overnight.fetcher.apiwrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class APIUtils
{
  public APIUtils() {}
  
  public static String getUUID(String ign)
  {
    if (ign.length() == 32) { return ign;
    }
    JSONParser parser = new JSONParser();
    JSONObject o = null;
    try {
      o = (JSONObject)parser.parse(Parser.read(Parser.mojang(ign)));
    }
    catch (Exception e) {
      e.printStackTrace();
      return "invaliduuid/";
    }
    return (String)o.get("id");
  }
  
  public static JSONObject getObject(String toParse) {
    JSONParser parser = new JSONParser();
    JSONObject o = null;
    try {
      o = (JSONObject)parser.parse(toParse);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return o;
  }
  
  public static JSONObject getSpeedrunObject(String toParse, int mode) {
    return getObject(Parser.read(Parser.speedrun(toParse, mode)));
  }
  
  public static String readURL(URL url) {
    return Parser.read(url);
  }
  
  public static URL speedrunPublic(String id, int mode) {
    return Parser.speedrun(id, mode);
  }
  
  static class Parser
  {
    Parser() {}
    
    public static URL mojang(String ign)
    {
      String url = "https://api.mojang.com/users/profiles/minecraft/@ign@";
      try {
        return new URL(url.replaceAll("@ign@", ign));
      }
      catch (MalformedURLException e) {
        e.printStackTrace(); }
      return null;
    }
    

    public static URL game(String uuid, String game)
    {
      String url = "http://api.hivemc.com/v1/player/@player@/" + game;
      if (game.isEmpty()) url = "http://api.hivemc.com/v1/player/@player@";
      try {
        return new URL(url.replaceAll("@player@", uuid));
      }
      catch (MalformedURLException e) {
        e.printStackTrace(); }
      return null;
    }
    








    public static URL speedrun(String param, int mode)
    {
      String url = "";
      switch (mode) {
      case 0: 
        url = "http://www.speedrun.com/api/v1/leaderboards/369ep8dl/level/@id@/824xzvmd?top=1";
        break;
      case 1: 
        url = "http://www.speedrun.com/api/v1/users/@id@";
      }
      try {
        return new URL(url.replaceAll("@id@", param));
      }
      catch (MalformedURLException e) {
        e.printStackTrace(); }
      return null;
    }
    
    public static URL hiveLB(int index, String game)
    {
      String url = "http://api.hivemc.com/v1/game/@game@/leaderboard/" + index + "/" + (index + 1);
      try {
        return new URL(url.replaceAll("@game@", game));
      }
      catch (MalformedURLException e) {
        e.printStackTrace(); }
      return null;
    }
    
    public static URL monthlyLB(String game)
    {
      String url = "https://thtmx.rocks/@game@/api/ighGH789fdf5kfHUo";
      try {
        return new URL(url.replaceAll("@game@", game));
      }
      catch (MalformedURLException e) {
        e.printStackTrace(); }
      return null;
    }
    
    public static String read(final URL url) {
        BufferedReader reader = null;
        try {
            final URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36(KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36");
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer buffer = new StringBuffer();
            final char[] chars = new char[1024];
            int read;
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            return buffer.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
}
