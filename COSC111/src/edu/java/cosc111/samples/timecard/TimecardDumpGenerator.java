package edu.java.cosc111.samples.timecard;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author RLVillacarlos
 */
public class TimecardDumpGenerator {
    private static final Random RND1 = new Random(System.currentTimeMillis());
    private static final Random RND2 = new Random(System.currentTimeMillis());
    private static final int[] SHIFT_START = {6,14,22}; //6am, 2pm, 10pm
    private static final int SHIFT_SPAN_IN_HOURS = 8;
    
    public static TimecardDump generate(Map<Integer,Integer> employeeShifts,Date timecardDate){
        List<TimecardEntry> dump = new ArrayList<>();
        
        for(Integer id:employeeShifts.keySet()){
            if(isPresent()){
                dump.add(new TimecardEntry(id, 
                        createTimeIn(timecardDate, employeeShifts.get(id)), 
                        createTimeOut(timecardDate, employeeShifts.get(id))));                
            }
        }
        
        return new TimecardDump(dump, timecardDate);
    }
    
    private static Date createTimeIn(Date d,int shift){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        
        //There are only 3 shifts
        if(shift<1 || shift >3){
            throw new IllegalArgumentException("Invalid shift value.");
        }
        //Set the time-in based on the shift
        c.set(Calendar.HOUR_OF_DAY,SHIFT_START[shift-1]);        
        c.set(Calendar.MINUTE,0);        
        c.set(Calendar.SECOND,0); 
        
        //Add factors affecting time-in
        addTimeInFactor(c);
        
        return c.getTime();
    }
    
    private static Date createTimeOut(Date d,int shift){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        
        //There are only 3 shifts
        if(shift<1 || shift >3){
            throw new IllegalArgumentException("Invalid shift value.");
        }
        
        //Set the time-out based on the shift
        c.set(Calendar.HOUR_OF_DAY,SHIFT_START[shift-1]);    
        c.set(Calendar.MINUTE,0);        
        c.set(Calendar.SECOND,0); 
        //Time-in + number of work hours 
        c.add(Calendar.HOUR_OF_DAY, SHIFT_SPAN_IN_HOURS );

        //Add factors affecting time-out
        addTimeOutFactors(c);
        
        return c.getTime();
    }
    
   
   
    private static void addTimeInFactor(Calendar c){
        double toss = RND2.nextDouble();
        if(toss> 0.95){
            //lateness hour, min, sec
            int[] l = {RND2.nextInt(SHIFT_SPAN_IN_HOURS),RND2.nextInt(60),RND2.nextInt(60)}; 
            c.add(Calendar.HOUR_OF_DAY, l[0]);
            c.add(Calendar.MINUTE, l[1]);
            c.add(Calendar.SECOND, l[2]);
        }else if(toss> 0.8){
            //lateness min, sec
            int[] l = {RND2.nextInt(60),RND2.nextInt(60)}; 
            c.add(Calendar.MINUTE, l[0]);
            c.add(Calendar.SECOND, l[1]);
        }else if(toss> 0.6){
            //lateness sec
            int l = RND2.nextInt(60); 
            c.add(Calendar.SECOND, l);
        }
        
    }
    
    private static void addTimeOutFactors(Calendar c){
        double toss = RND2.nextDouble();
        
        if(toss> 0.99){
            //early out hour, min, sec
            int[] eo = {RND2.nextInt(SHIFT_SPAN_IN_HOURS),RND2.nextInt(60),RND2.nextInt(60)}; 
            c.add(Calendar.HOUR_OF_DAY, -eo[0]);
            c.add(Calendar.MINUTE, -eo[1]);
            c.add(Calendar.SECOND, -eo[2]);
        }else if(toss> 0.95){
            //early out min, sec
            int[] eo = {RND2.nextInt(60),RND2.nextInt(60)}; 
            c.add(Calendar.MINUTE, -eo[0]);
            c.add(Calendar.SECOND, -eo[1]);
        }else if(toss> 0.90){
            //early out sec
            int eo = RND2.nextInt(60); 
            c.add(Calendar.SECOND, -eo);
        }else if(toss>0.7){
            //overtime hours, minute, sec
            int[] ot = {RND2.nextInt(3),RND2.nextInt(60),RND2.nextInt(60)};
            c.add(Calendar.HOUR_OF_DAY, ot[0]);
            c.add(Calendar.MINUTE, ot[1]);
            c.add(Calendar.SECOND, ot[2]);
        }        
    }
    
    private static boolean isPresent(){
        return RND1.nextDouble() > 0.3;
    }    
    
}
