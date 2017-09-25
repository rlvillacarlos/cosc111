package edu.java.cosc111.samples.oop.accounting.employee;

import edu.java.cosc111.samples.oop.accounting.Taxable;
import java.math.BigDecimal;

public abstract class Employee implements Taxable{

    private String sFName;
    private String sLName;
    private String sMName;
    private String sID;
    private String rank;

    public Employee() {
//        sFName = "";
//        sMName = "";
//        sLName = "";
//        sID = "";
        this("", "", "", "");
    }

    public Employee(String sFName, String sMName, String sLastName, String sID) {
        this.sFName = sFName;
        this.sMName = sMName;
        this.sLName = sLastName;
        this.sID = sID;
    }

    public void setFirstName(String sFirstName) {
        this.sFName = sFirstName;
    }

    public void setLastName(String sLastName) {
        this.sLName = sLastName;
    }

    public void setMiddleName(String sMiddleName) {
        this.sMName = sMiddleName;
    }

    public void setID(String sID) {
        this.sID = sID;
    }

    public String getFirstName() {
        return sFName;
    }

    public String getLastName() {
        return sLName;
    }

    public String getMiddleName() {
        return sMName;
    }

    public String getID() {
        return sID;
    }

    @Override
    public String toString() {
        return String.format("ID: %s \nName: %s %s %s", sID , sLName , sFName , sMName);
    }
    
    
    public BigDecimal getNetPay(){
        return getGrossPay().subtract(getTax());
    }
    
    public abstract BigDecimal getGrossPay();
}
