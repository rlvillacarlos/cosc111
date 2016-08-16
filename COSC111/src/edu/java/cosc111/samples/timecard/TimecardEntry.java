package edu.java.cosc111.samples.timecard;

import java.util.Date;

/**
 *
 * @author RLVillacarlos
 */
public class TimecardEntry {
    private final int empId; //Employee id
    private final Date in; //Time-in
    private final Date out;//Time-out
    
    public TimecardEntry(int empId_, Date in_, Date out_){
        empId = empId_;
        in = in_;
        out = out_;
    }

    public int getEmployeeId() {
        return empId;
    }

    public Date getTimeIn() {
        return (Date)in.clone();
    }

    public Date getTimeOut() {
        return (Date) out.clone();
    }

    @Override
    public String toString() {
        return "[id: "+ empId +", in: " + in.toString() + ","  + "out: " + out.toString() + "]";
    }
    
    
    
}
