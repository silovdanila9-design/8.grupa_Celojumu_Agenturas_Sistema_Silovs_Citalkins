/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
/**
 *
 * @author Danils.Silovs
 */
public class Maksajums {
    private int id;
    private int rezervacijaId;
    private double summa;
    private Date maksajumaDatums;
    private String maksajumaVeids;

    public Maksajums(int id, int rezervacijaId, double summa, Date maksajumaDatums, String maksajumaVeids) {
        this.id = id;
        this.rezervacijaId = rezervacijaId;
        this.summa = summa;
        this.maksajumaDatums = maksajumaDatums;
        this.maksajumaVeids = maksajumaVeids;
    }

    public int getId() { return id; }
    public int getRezervacijaId() { return rezervacijaId; }
    public double getSumma() { return summa; }
    public Date getMaksajumaDatums() { return maksajumaDatums; }
    public String getMaksajumaVeids() { return maksajumaVeids; }
}
