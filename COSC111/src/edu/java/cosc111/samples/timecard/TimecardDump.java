package edu.java.cosc111.samples.timecard;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author RLVillacarlos
 */
public class TimecardDump {
    private final List<TimecardEntry> dump;//List of timecard entry
    private final Date dumpDate;//Date of the timecard

    public TimecardDump(List<TimecardEntry> dump_, Date dumpDate_) {
        this.dump = dump_;
        this.dumpDate = dumpDate_;
    }

    public List<TimecardEntry> getDump() {
        return Collections.unmodifiableList(dump);
    }

    public Date getDumpDate() {
        return (Date)dumpDate.clone();
    }
    
    
}
