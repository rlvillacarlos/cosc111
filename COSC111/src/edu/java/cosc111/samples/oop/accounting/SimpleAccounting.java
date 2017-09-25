/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.java.cosc111.samples.oop.accounting;

import edu.java.cosc111.samples.oop.accounting.employee.Employee;
import edu.java.cosc111.samples.oop.accounting.employee.Employee;
import edu.java.cosc111.samples.oop.accounting.employee.HourlyEmployee;
import edu.java.cosc111.samples.oop.accounting.employee.SalariedEmployee;
import java.math.BigDecimal;

/**
 *
 * @author russel
 */
public class SimpleAccounting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HourlyEmployee emp1 = new HourlyEmployee();
        SalariedEmployee emp3 = new SalariedEmployee();
        Employee emp2 = emp1;
        
        
        emp1.setFirstName("Russel");
        emp1.setMiddleName("Lazaro");
        emp1.setLastName("Villacarlos");
        emp1.setHourlyRate(null);
        emp1.setHours(10);
        emp1.setTaxPercent(new BigDecimal("0.1"));
        emp3.setSalary(new BigDecimal("20000"));
//        emp1 = new HourlyEmployee();
        
//        emp2.setID("2017-1220");
        System.out.println(emp2.getGrossPay());
        emp2 = emp3;
        System.out.println(emp2.getGrossPay());
//        System.out.println(emp2);
    }
    
}
