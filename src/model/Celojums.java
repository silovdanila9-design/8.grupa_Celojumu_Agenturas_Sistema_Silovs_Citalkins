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
public class Celojums {
    private int id;
    private String nosaukums;
    private String apraksts;
    private double cena;

    public Celojums(int id, String nosaukums, String apraksts, double cena) {
        this.id = id;
        this.nosaukums = nosaukums;
        this.apraksts = apraksts;
        this.cena = cena;
    }

    public int getId() { return id; }
    public String getNosaukums() { return nosaukums; }
    public String getApraksts() { return apraksts; }
    public double getCena() { return cena; }
}
