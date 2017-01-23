package edu.java.cosc111.samples.timecard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author RLVillacarlos
 */
public class EmployeeShiftScheduleGenerator {
    private static final Random rnd = new Random(System.currentTimeMillis());
    
    public static Map<Integer,Integer> generate(Set<Integer> ids){
        if(ids.size()>0){
            Map<Integer,Integer> shifts = new TreeMap<>();
            
            for(Integer id:ids){
                shifts.put(id, getShift());
            }
            
            return shifts;
        }
        return new HashMap<>();
    }
    
    private static int getShift(){
        double toss = rnd.nextDouble();
            return (toss < 0.33)? 1 : ((toss < 0.67)? 2 : 3);
    }
    
}
