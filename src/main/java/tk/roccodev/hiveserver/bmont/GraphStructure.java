package tk.roccodev.hiveserver.bmont;

public class GraphStructure {
	
	public long k, d, v, p, s, b, t;
	
	public Object r, z;
	
	public GraphStructure(long pts, long kills, long deaths, long vics, long played, long beds, long teams) {
		s = pts;
		p = played;
		v = vics;
		k = kills;
		d = deaths;
		t = teams;
		b = beds;
		
		r = k / (double) deaths;
		z = vics / ((double) played - (double) vics);
		
		if((double)r == Double.POSITIVE_INFINITY) r = "Infinity";
		else if(Double.isNaN((double)r)) r = "NaN";
		
		if((double)z == Double.POSITIVE_INFINITY) z = "Infinity";
		else if(Double.isNaN((double)z)) z = "NaN";
		
	}

}
