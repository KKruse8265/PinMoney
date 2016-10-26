package de.cokuss.chhe.pinmoney;

import java.util.Date;

public class Payments {
    private Cycle cycle;
    private float amount;
    private Date date;

    public Payments(Date date, Cycle cycle, float amount) {
        this.date = date;
        this.cycle = cycle;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    Cycle getCycle() {
        return cycle;
    }

    String getTurnusStr () {
        return cycle.getBezeichner();
    }

    public String getTurnusStrShort() {
        return cycle.getBezeichnerLetter();
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

}
