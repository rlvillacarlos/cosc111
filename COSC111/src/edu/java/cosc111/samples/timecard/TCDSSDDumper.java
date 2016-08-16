package edu.java.cosc111.samples.timecard;

import edu.java.cosc111.samples.RandomIntegerIDGenerator;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 *
 * @author RLVillacarlos
 */
public class TCDSSDDumper{
   
    private static final int ID_WIDTH = 6;
    
    public static void main(String[] args) throws IOException {        
        //Create calendar using the default (system dependent) timezone
        Calendar now = Calendar.getInstance();
        //Set time of calendar to current time
        now.setTimeInMillis(System.currentTimeMillis());
        
        DateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmmss");
        String nowString = dateFormat.format(now.getTime());
        
        
        now.add(Calendar.DAY_OF_MONTH , -1);//Subtract 1 day from now (yesterday)
        //Set the time to 6:00:00 am
        now.set(Calendar.HOUR_OF_DAY,6);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        
        //Generate random employee numbers
        List<Integer> empIds = RandomIntegerIDGenerator.generate(ID_WIDTH, 100, 1000);
        
        //Assign random shift schedule to the generated employee to generate work shift schedule
        Map<Integer,Integer> shifts = EmployeeShiftScheduleGenerator.generate(empIds);
        
        //Assign random time-in and time-out based on generated shift schedule to generate timecard dump
        //The assignment will randomly exclude an employee, which in effect makes the employee absent for the day
        TimecardDump timecardDump = TimecardDumpGenerator.generate(shifts,now.getTime());
        
        //Save the generated shift schedule in the dumps folder of the project folder
        saveShiftSchedule(Paths.get("dumps","dump_shift_schedule_" + nowString + ".ssd"), shifts);
        
        //Save the generated timecard dump in the dumps folder of the project folder
        saveTimecardDump(Paths.get("dumps","dump_timecard_" + nowString + ".tcd"), timecardDump);
//        System.out.println(timecardDump.getDump());
    }
    
    private static void saveShiftSchedule(Path p, Map<Integer,Integer> sched) throws IOException{
        
        /*
            Create all the necessary directories in the path if they do not exists.
            We use getParent() since the last element of the path is considered
            as the filename.
        */
        Files.createDirectories(p.getParent());
        
        try(PrintWriter out = new PrintWriter(Files.newBufferedWriter(
                                    p, StandardCharsets.UTF_8,
                                        StandardOpenOption.CREATE,
                                        StandardOpenOption.TRUNCATE_EXISTING))){
            
            /*
              0 padded integer format. Each integer has width(number of digits) 
              equal to the number of digits provided earlier
            */
            
            String format = "%1$0"+ID_WIDTH+"d\t%2$d%n";
            
            for(Integer id:sched.keySet()){                
                out.printf(format, id,sched.get(id));
            }
        }
        System.out.println("Done saving shift schedule.");
    }
    
    private static void saveTimecardDump(Path p, TimecardDump dump) throws IOException{
        
        /*
            Create all the necessary directories in the path if they do not exists.
            We use getParent() since the last element of the path is considered
            as the filename.
        */
        Files.createDirectories(p.getParent());
        
        try(DataOutputStream out = new DataOutputStream(
                                    new BufferedOutputStream(
                                        Files.newOutputStream(p,
                                            StandardOpenOption.CREATE,
                                            StandardOpenOption.TRUNCATE_EXISTING)))){
            
            /*
                Convert the timecard date to a long, representing the number of 
                seconds since January 01, 1970-01-01 00:00:00 UTC
            */
            out.writeLong(dump.getDumpDate().toInstant().getEpochSecond());
            
            //Write each timecard entry to the file
            for(TimecardEntry e:dump.getDump()){
                //Write the employee id as an integer in the file
                out.writeInt(e.getEmployeeId());
                /*
                    Convert the time-in and time-out to a long value, representing 
                    the number of seconds since January 01, 1970-01-01 00:00:00 UTC
                    then write the corresponding long value to the file
                */    
                out.writeLong(e.getTimeIn().toInstant().getEpochSecond());
                out.writeLong(e.getTimeOut().toInstant().getEpochSecond());
            }
        }
        System.out.println("Done saving timecard dump.");
    }
}
