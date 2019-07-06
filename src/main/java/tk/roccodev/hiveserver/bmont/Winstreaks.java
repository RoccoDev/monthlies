package tk.roccodev.hiveserver.bmont;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tk.roccodev.nightfallbot.Test;
import tk.roccodev.overnight.fetcher.apiwrapper.APIUtils;

public class Winstreaks {
	
	private static String uri = "https://api.hivemc.com/v1/game/BED/leaderboard/0/200";
	private static String uri2 = "https://api.hivemc.com/v1/game/BED/leaderboard/200/400";
	private static String uri3 = "https://api.hivemc.com/v1/game/BED/leaderboard/400/600";
	private static String uri4 = "https://api.hivemc.com/v1/game/BED/leaderboard/600/800";
	private static String uri5 = "https://api.hivemc.com/v1/game/BED/leaderboard/800/1000";

	
	
	private static void runForMode(String mode) throws Exception {
		
		String url = mode.equalsIgnoreCase("BED") ? uri + "/win_streak" : uri + "/" + mode.toUpperCase() + "/win_streak";
		String url2 = mode.equalsIgnoreCase("BED") ? uri2 + "/win_streak" : uri2 + "/" + mode.toUpperCase() + "/win_streak";
		String url3 = mode.equalsIgnoreCase("BED") ? uri3 + "/win_streak" : uri3 + "/" + mode.toUpperCase() + "/win_streak";
		String url4 = mode.equalsIgnoreCase("BED") ? uri4 + "/win_streak" : uri4 + "/" + mode.toUpperCase() + "/win_streak";
		String url5 = mode.equalsIgnoreCase("BED") ? uri5 + "/win_streak" : uri5 + "/" + mode.toUpperCase() + "/win_streak";
		
		//JSONObject hist = APIUtils.getObject(APIUtils.readURL(new URL("https://bedwarstoolkit-streaks-beds.firebaseio.com/" + mode + ".json")));
		
		final FirebaseDatabase database = FirebaseDatabase.getInstance(Test.streakApp);
		DatabaseReference newOne = database.getReference(mode);
		//DatabaseReference newOneHist = FirebaseDatabase.getInstance(Test.histoStreakApp).getReference(mode);
		//HashMap<SavedWinstreakEarly, Long> histBefore = new HashMap<>();
		//LinkedHashMap<String, WinstreakStructure> histAfter = new LinkedHashMap<>();
		
		JSONObject o = APIUtils.getObject(APIUtils.readURL(new URL(url)));
		JSONArray a = (JSONArray) o.get("leaderboard");
		
		JSONObject o2 = APIUtils.getObject(APIUtils.readURL(new URL(url2)));
		JSONArray a2 = (JSONArray) o2.get("leaderboard");
		
		JSONObject o3 = APIUtils.getObject(APIUtils.readURL(new URL(url3)));
		JSONArray a3 = (JSONArray) o3.get("leaderboard");
		
		JSONObject o4 = APIUtils.getObject(APIUtils.readURL(new URL(url4)));
		JSONArray a4 = (JSONArray) o4.get("leaderboard");
		
		JSONObject o5 = APIUtils.getObject(APIUtils.readURL(new URL(url5)));
		JSONArray a5 = (JSONArray) o5.get("leaderboard");
		
		a.addAll(a2);
		a.addAll(a3);
		a.addAll(a4);
		a.addAll(a5);
		
		LinkedHashMap<String, WinstreakStructure> toUpdate = new LinkedHashMap<>();
		
		for(Object o1 : a) {
			JSONObject j = (JSONObject) o1;
			String uuid = (String) j.get("UUID");
			String name = (String) j.get("username");
			long place = (long) j.get("humanIndex");
			long streak = (long) j.get("win_streak");
			
			toUpdate.put(uuid, new WinstreakStructure(name, streak, place));
		}
	
		newOne.setValueAsync(toUpdate);
	}
	
	
	
	public static void run() throws Exception {
		
		/*
		URL url = new URL(uri);
		
		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		
		DatabaseReference newOne = database.getReference("winstreaks");
		DatabaseReference newOneHist = database.getReference("winstreaks-historical");
		
		JSONObject hist = APIUtils.getObject(APIUtils.readURL(new URL("https://roccodev-misc.firebaseio.com/winstreaks-historical.json")));
		HashMap<SavedWinstreakEarly, Long> histBefore = new HashMap<>();
		LinkedHashMap<String, WinstreakStructure> histAfter = new LinkedHashMap<>();
		
		for(Object e : hist.entrySet()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>)e;
			JSONObject v = (JSONObject) entry.getValue();
			histBefore.put(new SavedWinstreakEarly((String)v.get("name"), entry.getKey(), (long)v.get("winstreak")), (long)v.get("winstreak")); 
			
		}
		
		JSONObject o = APIUtils.getObject(APIUtils.readURL(url));
		JSONArray a = (JSONArray) o.get("leaderboard");
		
		JSONObject o2 = APIUtils.getObject(APIUtils.readURL(new URL(uri2)));
		JSONArray a2 = (JSONArray) o2.get("leaderboard");
		
		JSONObject o3 = APIUtils.getObject(APIUtils.readURL(new URL(uri3)));
		JSONArray a3 = (JSONArray) o3.get("leaderboard");
		
		JSONObject o4 = APIUtils.getObject(APIUtils.readURL(new URL(uri4)));
		JSONArray a4 = (JSONArray) o4.get("leaderboard");
		
		JSONObject o5 = APIUtils.getObject(APIUtils.readURL(new URL(uri5)));
		JSONArray a5 = (JSONArray) o5.get("leaderboard");
		
		
		LinkedHashMap<String, WinstreakStructure> toUpdate = new LinkedHashMap<>();

		a.addAll(a2);
		a.addAll(a3);
		a.addAll(a4);
		a.addAll(a5);
		
		for(Object o1 : a) {
			JSONObject j = (JSONObject) o1;
			String uuid = (String) j.get("UUID");
			String name = (String) j.get("username");
			long place = (long) j.get("humanIndex");
			long streak = (long) j.get("win_streak");
			
			if(!hist.containsKey(uuid)) {
				histBefore.put(new SavedWinstreakEarly(name, uuid, streak), streak);
			}
			else {
				JSONObject j2 = (JSONObject) hist.get(uuid);
				if((long)j2.get("winstreak") < streak) {
					histBefore.put(new SavedWinstreakEarly(name, uuid, streak), streak);
				}
				
			}
			
			
			toUpdate.put(uuid, new WinstreakStructure(name, streak, place));
			
		}
		
		Map<SavedWinstreakEarly, Long> sorted = LBs.sortByValue(histBefore);
		int i = sorted.size();
		for(Map.Entry<SavedWinstreakEarly, Long> e : sorted.entrySet()) {
			histAfter.put(e.getKey().uuid, new WinstreakStructure(e.getKey().name, e.getKey().streak, i--));
		}
		
		newOne.setValueAsync(toUpdate);
		newOneHist.setValueAsync(histAfter);
		*/
		
		runForMode("BED");
		runForMode("BEDS");
		runForMode("BEDD");
		runForMode("BEDT");
		runForMode("BEDX");
		
	}
	
}
