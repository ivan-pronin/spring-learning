package com.epam.springadvanced.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="seat")
public class Seat {
    @XmlElement
    private int number;
    @XmlElement
    private boolean vip;

    public Seat(int number, boolean vip) {
        this.number = number;
        this.vip = vip;
    }

    public Seat(int number) {
        this.number = number;
        this.vip = false;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
}
