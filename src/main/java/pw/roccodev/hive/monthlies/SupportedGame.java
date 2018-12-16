package pw.roccodev.hive.monthlies;

import pw.roccodev.hive.monthlies.modes.Mode;
import pw.roccodev.hive.monthlies.modes.data.DrData;
import pw.roccodev.hive.monthlies.modes.data.TimvData;

public enum SupportedGame {

    // BED has its own worker
    TIMV(TimvData.class),
    DR(DrData.class);
    /*
    SKY,
    HIDE,
    GNT,
    GNTM,
    BP,
    CAI*/

    private Class<? extends Mode> dataClass;

    SupportedGame(Class<? extends Mode> dataClass) {
        this.dataClass = dataClass;
    }

    public Class<? extends Mode> getDataClass() {
        return dataClass;
    }
}
