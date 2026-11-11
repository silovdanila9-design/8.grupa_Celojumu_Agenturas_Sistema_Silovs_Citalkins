package model;

import java.time.LocalDateTime;

public class Rezervacija {

    public static final String STATUS_AKTIVA = "AKTIVA";

    public static final String STATUS_APMAKSATA = "APMAKSATA";

    public static final String STATUS_ATCELTA = "ATCELTA";

    private int id;

    private int lietotajaId;

    private int celojumaId;

    private int personuSkaits;

    private double kopsumma;

    private String statuss;

    private LocalDateTime izveidota;

    public Rezervacija(int id, int lietotajaId, int celojumaId,
                       int personuSkaits, double kopsumma, String statuss,
                       LocalDateTime izveidota) {
        this.id = id;
        this.lietotajaId = lietotajaId;
        this.celojumaId = celojumaId;
        this.personuSkaits = personuSkaits;
        this.kopsumma = kopsumma;
        this.statuss = statuss;
        this.izveidota = izveidota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLietotajaId() {
        return lietotajaId;
    }

    public void setLietotajaId(int lietotajaId) {
        this.lietotajaId = lietotajaId;
    }

    public int getCelojumaId() {
        return celojumaId;
    }

    public void setCelojumaId(int celojumaId) {
        this.celojumaId = celojumaId;
    }

    public int getPersonuSkaits() {
        return personuSkaits;
    }

    public void setPersonuSkaits(int personuSkaits) {
        this.personuSkaits = personuSkaits;
    }

    public double getKopsumma() {
        return kopsumma;
    }

    public void setKopsumma(double kopsumma) {
        this.kopsumma = kopsumma;
    }

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

    public LocalDateTime getIzveidota() {
        return izveidota;
    }

    public void setIzveidota(LocalDateTime izveidota) {
        this.izveidota = izveidota;
    }
}
