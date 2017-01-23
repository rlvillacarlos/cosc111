package edu.java.cosc111.samples.timecard;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author RLVillacarlos
 */
public class TimecardDump {
    private final Map<Integer,TimecardEntry> dump;//List of timecard entry
    private final Date dumpDate;//Date of the timecard

    public TimecardDump(Map<Integer,TimecardEntry> dump_, Date dumpDate_) {
        this.dump = dump_;
        this.dumpDate = dumpDate_;
    }

    public Map<Integer,TimecardEntry> getDump() {
        return Collections.unmodifiableMap(dump);
    }

    public Date getDumpDate() {
        return (Date)dumpDate.clone();
    }
    
    
}
