package tk.roccodev.hiveserver.bmont;

public class SavedStructure {

	public String uuid;
	public long startPoints, startVic, startPl, startKil, startDea, startBed, startTem;
	public SavedStructure(String uuid, long startPoints, long startVic, long startPl, long startKil, long startDea, long startBed, long startTem) {
		super();
		this.uuid = uuid;
		this.startPoints = startPoints;
		this.startVic = startVic;
		this.startPl = startPl;
		this.startKil = startKil;
		this.startDea = startDea;
		this.startBed = startBed;
		this.startTem = startTem;
	}
	
	
}
