package pw.roccodev.hive.monthlies;

import pw.roccodev.hive.monthlies.modes.Mode;
import pw.roccodev.hive.monthlies.modes.data.*;

public enum SupportedGame {

    // BED has its own worker
    TIMV(TimvData.class),
    DR(DrData.class),
    GNT(GntData.class),
    GNTM(GntData.class),
    SKY(SkyData.class),
    HIDE(HideData.class),
    CAI(CaiData.class),
    BP(BpData.class);

    private Class<? extends Mode> dataClass;

    SupportedGame(Class<? extends Mode> dataClass) {
        this.dataClass = dataClass;
    }

    public Class<? extends Mode> getDataClass() {
        return dataClass;
    }
}
