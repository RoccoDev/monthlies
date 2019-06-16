package tk.roccodev.hiveserver.bmont;

public class SavedWinstreakEarly {
	
	public String name, uuid;
	public long streak;
	public SavedWinstreakEarly(String name, String uuid, long streak) {
		super();
		this.name = name;
		this.uuid = uuid;
		this.streak = streak;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SavedWinstreakEarly)) return false;
		SavedWinstreakEarly s = (SavedWinstreakEarly) obj;
		return this.uuid.equals(s.uuid);
	}
	
	

}
