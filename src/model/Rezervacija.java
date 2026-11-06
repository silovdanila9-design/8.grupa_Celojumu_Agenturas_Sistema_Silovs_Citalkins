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
public class Rezervacija {
    private int id;
    private int lietotajsId;
    private int celojumsId;
    private Date rezervacijasDatums;
    private int personuSkaits;
    private String statuss;

    public Rezervacija(int id, int lietotajsId, int celojumsId, Date rezervacijasDatums, int personuSkaits, String statuss) {
        this.id = id;
        this.lietotajsId = lietotajsId;
        this.celojumsId = celojumsId;
        this.rezervacijasDatums = rezervacijasDatums;
        this.personuSkaits = personuSkaits;
        this.statuss = statuss;
    }

    public int getId() { return id; }
    public int getLietotajsId() { return lietotajsId; }
    public int getCelojumsId() { return celojumsId; }
    public Date getRezervacijasDatums() { return rezervacijasDatums; }
    public int getPersonuSkaits() { return personuSkaits; }
    public String getStatuss() { return statuss; }
}
