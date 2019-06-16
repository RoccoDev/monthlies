package tk.roccodev.hiveserver.bmont;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.*;
import com.google.firebase.database.Transaction.Result;
import com.mrpowergamerbr.temmiewebhook.DiscordEmbed;
import com.mrpowergamerbr.temmiewebhook.DiscordMessage;
import com.mrpowergamerbr.temmiewebhook.TemmieWebhook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tk.roccodev.nightfallbot.Test;
import tk.roccodev.overnight.fetcher.apiwrapper.APIUtils;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

public class LBs {
	
	private static String uri = "http://api.hivemc.com/v1/game/BED/leaderboard/0/200";
	private static String uri2 = "http://api.hivemc.com/v1/game/BED/leaderboard/200/400";
	private static String uri3 = "http://api.hivemc.com/v1/game/BED/leaderboard/400/500";
	private static String uriUpdate = "http://api.hivemc.com/v1/game/BED/leaderboard/400/600";

	public static void updateTheTing() throws IOException {
		URL url = new URL(uri);
		
		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		final FirebaseDatabase gDb = FirebaseDatabase.getInstance(Test.miscApp);
		
		final FirebaseDatabase kfDb = FirebaseDatabase.getInstance(Test.kfApp);
		
		DatabaseReference newOne = database.getReference();
		JSONObject data = APIUtils.getObject(APIUtils.readURL(new URL("https://bedwarstoolkit-season-data.firebaseio.com/.json")));
		JSONObject lastData = APIUtils.getObject(APIUtils.readURL(new URL("https://monthlies-bed-kf.firebaseio.com/l.json")));
		DatabaseReference lastDataRef = kfDb.getReference("l");
	
		// JSONObject disqualified = APIUtils.getObject(APIUtils.readURL(new URL("https://roccodev-misc.firebaseio.com/disqualified.json")));
		// JSONArray arr = (JSONArray) disqualified.get("array")
		HashMap<String, GraphStructure> graphsToAdd = new HashMap<>();
		System.out.println(data.size());
		LinkedHashMap<BedStructureEarly, Long> toUpdate = new LinkedHashMap<>();
		
		List<String> farmers = new ArrayList<String>();
		HashMap<String, KillFarmingStructure> farmStr = new HashMap<>();
		HashMap<String, Long> fKills = new HashMap<>();
		HashMap<String, String> fNames = new HashMap<>();
		
		
		
		JSONObject o = APIUtils.getObject(APIUtils.readURL(url));
		JSONArray a = (JSONArray) o.get("leaderboard");
		
		JSONObject o2 = APIUtils.getObject(APIUtils.readURL(new URL(uri2)));
		JSONArray a2 = (JSONArray) o2.get("leaderboard");
		
		JSONObject o3 = APIUtils.getObject(APIUtils.readURL(new URL(uriUpdate)));
		JSONArray a3 = (JSONArray) o3.get("leaderboard");
		
		a.addAll(a2);
		a.addAll(a3);
		System.out.println(o.get("start") + " / " + a.size());
		for(Object o1 : a) {
			JSONObject j = (JSONObject) o1;
			System.out.println(j.get("username"));
			if(!data.containsKey((String)j.get("UUID"))) continue;
			// if(arr.contains((String)j.get("UUID"))) continue;
			JSONObject child = (JSONObject) data.get((String)j.get("UUID"));
			long startPts = (long) child.get("startPoints");
			long now = (long)j.get("total_points");
			
			long pDiff = now - startPts;
			
			
			long startWins = (long) child.get("startVic");
			long nowW = (long)j.get("victories");
			long vDiff = nowW - startWins;
			
			long startPlayed = (long) child.get("startPl");
			long nowP = (long)j.get("games_played");
			long gDiff = nowP - startPlayed;
			
			long startKills = (long) child.get("startKil");
			long nowK = (long)j.get("kills");
			long kDiff = nowK - startKills;
			
			long startDeaths = (long) child.get("startDea");
			long nowD = (long)j.get("deaths");
			long dDiff = nowD - startDeaths;
			
			long startBeds = (long) child.get("startBed");
			long nowB = (long)j.get("beds_destroyed");
			long bDiff = nowB - startBeds;
			
			long startTeams = (long) child.get("startTem");
			long nowT = (long)j.get("teams_eliminated");
			long tDiff = nowT - startTeams;
			
			if(lastData.containsKey((String)j.get("UUID"))) {
				JSONObject lastDataUser = (JSONObject) lastData.get((String)j.get("UUID"));
				long kills = (long)lastDataUser.get("k");
				long games = (long)lastDataUser.get("g");
				long pts = lastDataUser.containsKey("p") ? (long)lastDataUser.get("p") : -1;
				long vics = lastDataUser.containsKey("v") ? (long)lastDataUser.get("v") : -1;
				long deaths = lastDataUser.containsKey("d") ? (long)lastDataUser.get("d") : -1;
				
				
				long triggerDiff = nowK - kills;
				long gamesDiff = nowP - games;
				long ptsDiff = now - pts;
				long vicDiff = nowW - vics;
				long deathsDiff = nowD - deaths;
				double ppgDiff = (double) ((double)ptsDiff / (double)gamesDiff);
				double kpgDiff = (double) ((double)triggerDiff / (double)gamesDiff);
				
				double thing = kpgDiff / ppgDiff * 1000D;
				
				if(kpgDiff >= 15 && thing >= 50) {
					farmers.add("**" + j.get("username") + "** is suspected of killfarming.\n\n"
							+ "**KPG:** " + kpgDiff + "\n**PPG:** " + ppgDiff + "\n\n"
							+ "**Kills:** " + triggerDiff + "\n**Deaths:** " + deathsDiff + "\n**Games:** " + gamesDiff
							+ "\n**Points:** " + ptsDiff + "\n**Victories**: " + vicDiff + "\n**Kills Per Point:** " + new DecimalFormat("00.00").format(thing) + "‰");
					
					fKills.put(j.get("UUID").toString(), (long)kpgDiff);
					fNames.put(j.get("UUID").toString(), j.get("username").toString());
				}
						
			}
			
			farmStr.put((String)j.get("UUID"), new KillFarmingStructure(nowP, nowK, now, nowW, nowD));
			
			System.out.println(j.get("username") + " / "  + now + " / " + startPts + " / " + pDiff);
			toUpdate.put(new BedStructureEarly((String)j.get("username"), pDiff, vDiff, gDiff, kDiff, dDiff, (String)j.get("UUID"), bDiff, tDiff), pDiff);
			graphsToAdd.put((String)j.get("UUID"), new GraphStructure(pDiff, kDiff, dDiff, vDiff, gDiff, bDiff, tDiff));
			
		}
		
		HashMap<String, BedStructure> toUpdateNow = new HashMap<>();
		
		int i = toUpdate.size();
		for(Entry<BedStructureEarly, Long> e : sortByValue(toUpdate).entrySet()) {
			toUpdateNow.put(e.getKey().uuid, new BedStructure(e.getKey().name, e.getKey().points, e.getKey().victories, e.getKey().played, e.getKey().kills, e.getKey().deaths, i--, e.getKey().beds, e.getKey().teams));
		
		}
		
		newOne.setValueAsync(toUpdateNow);
		newOne.orderByChild("points");
		lastDataRef.setValueAsync(farmStr);
		
		System.out.println("Done updating " + a.size() + " profiles.");
		
		TemmieWebhook hook = new TemmieWebhook("https://canary.discordapp.com/api/webhooks/541378665016197139/V57qLEPDO0cL9sZvFnc2bvnf_MYZI_4I_vC11-EopyJPpVA0bsH7Y781B2ijTigJ_KWX");
		
		if(farmers.size() > 0) {
			System.out.println("Found killfarmers!");
			for(String s : farmers) {
				DiscordEmbed emb = DiscordEmbed.builder().description(s).color(0x42f445).title("Possible killfarmer found").build();
				DiscordMessage msg = DiscordMessage.builder().embed(emb).build();
				hook.sendMessage(msg);
			}
		}
		
		for(Entry<String, Long> kE : fKills.entrySet()) {
			DatabaseReference newDb = kfDb.getReference("b/" + kE.getKey());
			newDb.runTransaction(new Transaction.Handler() {

				@Override
				public Result doTransaction(MutableData arg0) {
					if(arg0.getValue() == null) {
						arg0.setValue(new KfSavedStructure(kE.getValue(), fNames.get(kE.getKey())));
					}
					else {
						MutableData vl = arg0.child("v");
						MutableData best = arg0.child("k");
						MutableData name = arg0.child("n");
						
						name.setValue(fNames.get(kE.getKey()));
						
						vl.setValue(vl.getValue(Long.class) + 1);
						
						if(kE.getValue() > best.getValue(Long.class)) best.setValue(kE.getValue());
					}
					return Transaction.success(arg0);
				}

				@Override
				public void onComplete(DatabaseError arg0, boolean arg1, DataSnapshot arg2) {
					if(arg0 != null) System.out.println("Error: " + arg0.getMessage());
				}
				
			});
		}
		
		System.out.println("Updating graphs");
		Calendar cal = Calendar.getInstance();
		String dateStr = cal.get(Calendar.YEAR) + "S" +  (cal.get(Calendar.MONTH) + 1) + "S" + cal.get(Calendar.DAY_OF_MONTH);
		for(Entry<String, GraphStructure> e : graphsToAdd.entrySet()) {
			DatabaseReference newDb = gDb.getReference("g/" + e.getKey() + "/" + dateStr);
			newDb.runTransaction(new Transaction.Handler() {
				
				@Override
				public void onComplete(DatabaseError arg0, boolean arg1, DataSnapshot arg2) {
					if(arg0 != null) System.out.println("Error: " + arg0.getMessage());
				}
				
				@Override
				public Result doTransaction(MutableData arg0) {
					if(arg0.getValue() != null) {
						return Transaction.abort();
					}
					arg0.setValue(e.getValue());
					return Transaction.success(arg0);
				}
				
				
				
			});
		}
	
		System.out.println("Done");
	
		
	}
	
	public static void doTheTing() throws IOException {
		URL url = new URL(uri);
		
		updateTheTing();
		
		//Test.RoccoDev.openPrivateChannel().complete().sendFile(new URL("https://bedwarstoolkit-monthlies-bed.firebaseio.com/.json").openStream(), "bedmonthly.json", new MessageBuilder().append("aa").build()).queue();

		URL old = new URL("https://bedwarstoolkit-monthlies-bed.firebaseio.com/.json");

		Bucket dataContainer = StorageClient.getInstance(Test.mainApp).bucket();
		long latest = 0;
		Page<Blob> otherData = dataContainer.list();
		for(Blob ignored : otherData.iterateAll()) {
			latest++;
		}
		dataContainer.create(++latest + "", old.openStream(), "application/json");

		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		
		DatabaseReference newOne = database.getReference();
		
		DatabaseReference ref = FirebaseDatabase.getInstance(Test.seasonDataApp).getReference();
		
		HashMap<String, Object> toUpdate = new HashMap<String, Object>();
		HashMap<String, BedStructure> toUpdate1 = new HashMap<String, BedStructure>();
		
		JSONObject o = APIUtils.getObject(APIUtils.readURL(url));
		JSONArray a = (JSONArray) o.get("leaderboard");
		
		JSONObject o2 = APIUtils.getObject(APIUtils.readURL(new URL(uri2)));
		JSONArray a2 = (JSONArray) o2.get("leaderboard");
		
		JSONObject o3 = APIUtils.getObject(APIUtils.readURL(new URL(uri3)));
		JSONArray a3 = (JSONArray) o3.get("leaderboard");
		
		a.addAll(a2);
		a.addAll(a3);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		toUpdate.put("info", new InfoStructure(new Date().toGMTString(),cal.getTime().toGMTString()));
		for(Object o1 : a) {
			JSONObject j = (JSONObject) o1;
			
			
			toUpdate.put((String)j.get("UUID"), new SavedStructure((String)j.get("UUID"), (long)j.get("total_points"), (long)j.get("victories"), (long)j.get("games_played"), (long)j.get("kills"), (long)j.get("deaths"), (long)j.get("beds_destroyed"), (long)j.get("teams_eliminated")));
			toUpdate1.put((String)j.get("UUID"), new BedStructure((String)j.get("username"), 0L, 0L, 0L, 0L, 0L, 1, 0L, 0L));
			
		}
		ref.setValueAsync(toUpdate);
		newOne.setValueAsync(toUpdate1);
		
		final FirebaseDatabase gDb = FirebaseDatabase.getInstance(Test.miscApp);
		gDb.getReference("g").removeValueAsync();
		
	}
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
	
}
