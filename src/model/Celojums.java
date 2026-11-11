package model;

import java.time.LocalDate;

public class Celojums {

    private int id;

    private String nosaukums;

    private String galamerkis;

    private String apraksts;

    private LocalDate sakumaDatums;

    private LocalDate beiguDatums;

    private double cena;

    private int vietuSkaits;

    public Celojums(int id, String nosaukums, String galamerkis, String apraksts,
                    LocalDate sakumaDatums, LocalDate beiguDatums,
                    double cena, int vietuSkaits) {
        this.id = id;
        this.nosaukums = nosaukums;
        this.galamerkis = galamerkis;
        this.apraksts = apraksts;
        this.sakumaDatums = sakumaDatums;
        this.beiguDatums = beiguDatums;
        this.cena = cena;
        this.vietuSkaits = vietuSkaits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNosaukums() {
        return nosaukums;
    }

    public void setNosaukums(String nosaukums) {
        this.nosaukums = nosaukums;
    }

    public String getGalamerkis() {
        return galamerkis;
    }

    public void setGalamerkis(String galamerkis) {
        this.galamerkis = galamerkis;
    }

    public String getApraksts() {
        return apraksts;
    }

    public void setApraksts(String apraksts) {
        this.apraksts = apraksts;
    }

    public LocalDate getSakumaDatums() {
        return sakumaDatums;
    }

    public void setSakumaDatums(LocalDate sakumaDatums) {
        this.sakumaDatums = sakumaDatums;
    }

    public LocalDate getBeiguDatums() {
        return beiguDatums;
    }

    public void setBeiguDatums(LocalDate beiguDatums) {
        this.beiguDatums = beiguDatums;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getVietuSkaits() {
        return vietuSkaits;
    }

    public void setVietuSkaits(int vietuSkaits) {
        this.vietuSkaits = vietuSkaits;
    }

    @Override
    public String toString() {
        return nosaukums + " (" + galamerkis + ") — "
                + String.format("%.2f", cena) + " EUR";
    }
}
