package pw.roccodev.hive.monthlies.utils;

import pw.roccodev.hive.monthlies.modes.Mode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Sort {

    public static <V extends Comparable<? super V>> LinkedHashMap<String, Mode> sortByValueAndApplyPlace(Map<Mode, V> map) {
        List<Map.Entry<Mode, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        LinkedHashMap<String, Mode> result = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<Mode, V> entry : list) {
            Mode k = entry.getKey();
            k.place = ++count;
            result.put(k.UUID, k);
        }

        return result;
    }

}
