package edu.java.cosc111.samples.oop.accounting.products;

import edu.java.cosc111.samples.oop.accounting.Taxable;
import java.math.BigDecimal;

/**
 *
 * @author russel
 */
public class Product implements Taxable {

    private String sItemName;
    private int nQuantity;
    private BigDecimal nBasePrice;
    private BigDecimal nTaxPercent;

    public Product(String sName, BigDecimal nBasePrice, int nQuantity, BigDecimal nTaxPercent) {
        this.sItemName = sName;
        this.nBasePrice = nBasePrice;
        this.nQuantity = nQuantity;
        this.nTaxPercent = nTaxPercent;
    }

    public void setName(String sItemName) {
        this.sItemName = sItemName;
    }

    public void setBasePrice(BigDecimal nPrice) {
        this.nBasePrice = nPrice;
    }

    public void setQuantity(int nQuantity) {
        this.nQuantity = nQuantity;
    }

    public String getName() {
        return this.sItemName;
    }

    public BigDecimal getBasePrice() {
        return this.nBasePrice;
    }

    public int getQuantity() {
        return this.nQuantity;
    }

    public BigDecimal getComputedPrice() {
        return this.nBasePrice.add(getTax());
    }

    @Override
    public void setTaxPercent(BigDecimal nTaxPercent) {
        this.nTaxPercent = nTaxPercent;
    }

    @Override
    public BigDecimal getTaxPercent() {
        return this.nTaxPercent;
    }

    @Override
    public BigDecimal getTax() {
        return nBasePrice.multiply(new BigDecimal(nQuantity)).multiply(nTaxPercent);
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nBase Price: %s\nQuantity: %d\nTax Percent: %s\nComputed Price: %s",
                getName(),getBasePrice(),getQuantity(),getTaxPercent(),getComputedPrice());
    }

}
