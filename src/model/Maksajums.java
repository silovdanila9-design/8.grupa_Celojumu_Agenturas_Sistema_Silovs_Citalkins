package model;

import java.time.LocalDateTime;

public class Maksajums {

    private int id;

    private int rezervacijaId;

    private double summa;

    private LocalDateTime datums;

    private String veids;

    public Maksajums(int id, int rezervacijaId, double summa,
                     LocalDateTime datums, String veids) {
        this.id = id;
        this.rezervacijaId = rezervacijaId;
        this.summa = summa;
        this.datums = datums;
        this.veids = veids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRezervacijaId() {
        return rezervacijaId;
    }

    public void setRezervacijaId(int rezervacijaId) {
        this.rezervacijaId = rezervacijaId;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    public LocalDateTime getDatums() {
        return datums;
    }

    public void setDatums(LocalDateTime datums) {
        this.datums = datums;
    }

    public String getVeids() {
        return veids;
    }

    public void setVeids(String veids) {
        this.veids = veids;
    }
}
