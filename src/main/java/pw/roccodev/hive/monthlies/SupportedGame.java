package pw.roccodev.hive.monthlies;

import pw.roccodev.hive.monthlies.modes.Mode;
import pw.roccodev.hive.monthlies.modes.data.*;

public enum SupportedGame {

    // BED has its own worker
    TIMV(TimvData.class),
    DR(DrData.class),
    SKY(SkyData.class),
    HIDE(HideData.class),
    BP(BpData.class),
    GRAV(GravData.class),
    SP(SpData.class),
    DRAW(DrawData.class),
    BD(BdData.class),
    CR(CrData.class);

    private Class<? extends Mode> dataClass;

    SupportedGame(Class<? extends Mode> dataClass) {
        this.dataClass = dataClass;
    }

    public Class<? extends Mode> getDataClass() {
        return dataClass;
    }

    public String getDbName() {
        if(this == SupportedGame.GRAV) return "gnt";
        else if(this == SupportedGame.DRAW) return "gntm";
        else if(this == SupportedGame.SP) return "cai";
        else return name().toLowerCase();
    }
}
