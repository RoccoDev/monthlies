package tk.roccodev.hiveserver.bmont;

public class BedStructureEarly {

	public String name;
	public String uuid;
	public long points;
	public long victories, played, kills, deaths, beds, teams;
	public BedStructureEarly(String name, long points, long victories, long played, long kills,
			long deaths, String uuid, long beds, long teams) {
		super();
		this.name = name;
		this.points = points;
		this.victories = victories;
		this.played = played;
		this.kills = kills;
		this.deaths = deaths;
		this.uuid = uuid;
		this.beds = beds;
		this.teams = teams;
	}
	
	
	
	
}
