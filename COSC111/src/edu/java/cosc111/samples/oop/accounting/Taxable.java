package edu.java.cosc111.samples.oop.accounting;

import java.math.BigDecimal;

/**
 *
 * @author russel
 */
public interface Taxable {
    public void setTaxPercent(BigDecimal tax);
    public BigDecimal getTaxPercent();
    public BigDecimal getTax();
}
