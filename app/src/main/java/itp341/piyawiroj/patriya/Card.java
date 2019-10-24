package itp341.piyawiroj.patriya;

import java.io.Serializable;

public class Card implements Serializable {

    private long number;
    private String name;
    private int securityCode;

    public Card(long number, String name, int securityCode) {
        this.number = number;
        this.name = name;
        this.securityCode = securityCode;
    }

    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                ", name=" + name +
                ", securityCode=" + securityCode +
                '}';
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }
}
