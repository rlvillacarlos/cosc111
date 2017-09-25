package edu.java.cosc111.samples.oop.accounting.employee;

import java.math.BigDecimal;
import static java.math.BigDecimal.*;

/**
 *
 * @author russel
 */
public class SalariedEmployee extends Employee {

    private BigDecimal nSalary;

    public SalariedEmployee() {
        super();		
        nSalary = ZERO;
    }

    public SalariedEmployee(String sFName, String sMName, String sLName, String sID) {
        this(sFName, sMName, sLName, sID, ZERO);
    }

    public SalariedEmployee(String sFName, String sMName, String sLName, String sID, BigDecimal nSalary) {
        super(sFName, sMName, sLName, sID);
        this.nSalary = nSalary;
    }

    public void setSalary(BigDecimal nSalary) {
        this.nSalary = nSalary;
    }

    public BigDecimal getSalary() {
        return this.nSalary;
    }

    @Override
    public BigDecimal getGrossPay() {
        return nSalary;
    }

    @Override
    public String toString() {
        return String.format("%s\nSalary:%s\nPay: %s",
                super.toString(),nSalary.toString(),nSalary.toString());
    }

    @Override
    public void setTaxPercent(BigDecimal tax) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getTaxPercent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getTax() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
