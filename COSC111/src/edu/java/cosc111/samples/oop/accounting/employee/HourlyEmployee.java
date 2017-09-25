package edu.java.cosc111.samples.oop.accounting.employee;

import java.math.BigDecimal;
import static java.math.BigDecimal.*;


/**
 *
 * @author russel
 */
public class HourlyEmployee extends Employee {

    private BigDecimal nRate;
    private BigDecimal taxRate;
    private int nHours;

    public HourlyEmployee() {
        super();
        nRate = ZERO;
    }

    public HourlyEmployee(String sFName, String sMName, String sLName,
            String sID) {
        this(sFName, sMName, sLName, sID, ZERO, 0);
    }

    public HourlyEmployee(String sFName, String sMName, String sLName, String sID, BigDecimal nRate, int nHours) {
        super(sFName, sMName, sLName, sID);
        this.nRate = nRate;
        this.nHours = nHours;
    }

    public BigDecimal getHourlyRate() {
        return nRate;
    }

    public void setHourlyRate(BigDecimal nRate) {
        this.nRate = nRate;
    }

    public int getHours() {
        return nHours;
    }

    public void setHours(int nHours) {
        this.nHours = nHours;
    }

    @Override
    public void setTaxPercent(BigDecimal tax) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.taxRate = tax;
    }

    @Override
    public BigDecimal getTaxPercent() {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return this.taxRate;
    }
    
    

    @Override
    public BigDecimal getTax() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return getGrossPay().multiply(taxRate);
    }

     
    
    @Override
    public BigDecimal getGrossPay() {
        return nRate.multiply(new BigDecimal(nHours));
    }

    @Override
    public String toString() {
        return String.format("%s\nHourly Rate:%s\nNumber of Hours: %d\nPay: %s", 
                super.toString(),nRate.toString(),nHours,getNetPay().toString());
    }
}
