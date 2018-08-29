package tk.roccodev.bots.trackerbot.oop;

import java.util.HashMap;

public class Structure {

    private String tracked;
    private HashMap<Long, Long> ids;
    private boolean wasOnline;
    private long createdAt;

    public Structure(String tracked, HashMap<Long, Long> ids, boolean wasOnline, long createdAt) {
        this.tracked = tracked;
        this.ids = ids;
        this.wasOnline = wasOnline;
        this.createdAt = createdAt;
    }

    public String getTracked() {
        return tracked;
    }

    public HashMap<Long, Long> getIds() {
        return ids;
    }

    public boolean wasOnline() {
        return wasOnline;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
