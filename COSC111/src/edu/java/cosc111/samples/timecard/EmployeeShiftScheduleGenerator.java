package edu.java.cosc111.samples.timecard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author RLVillacarlos
 */
public class EmployeeShiftScheduleGenerator {
    private static final Random rnd = new Random(System.currentTimeMillis());

    public static Map<Integer,Integer> generate(List<Integer> ids){
        if(ids.size()>0){
            Map<Integer,Integer> shifts = new HashMap<>(ids.size());
            
            for(Integer id:ids){
                shifts.put(id, getShift());
            }
            
            return shifts;
        }
        return new HashMap<>();
    }
    
    private static int getShift(){
        double toss = rnd.nextDouble();
            return (toss < 1/3.0)? 1 : ((toss < 2/3.0)? 2 : 3);
    }
    
}
