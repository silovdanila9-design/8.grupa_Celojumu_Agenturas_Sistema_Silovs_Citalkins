package model;

public class Lietotajs {

    private int id;

    private String vards;

    private String uzvards;

    private String epasts;

    private String talrunis;

    private String lietotajvards;

    private String parolesHash;

    private String loma;

    public Lietotajs() {
        this.loma = "KLIENTS";
    }

    public Lietotajs(int id, String vards, String uzvards, String epasts,
                     String talrunis, String lietotajvards,
                     String parolesHash, String loma) {
        this.id = id;
        this.vards = vards;
        this.uzvards = uzvards;
        this.epasts = epasts;
        this.talrunis = talrunis;
        this.lietotajvards = lietotajvards;
        this.parolesHash = parolesHash;
        this.loma = loma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVards() {
        return vards;
    }

    public void setVards(String vards) {
        this.vards = vards;
    }

    public String getUzvards() {
        return uzvards;
    }

    public void setUzvards(String uzvards) {
        this.uzvards = uzvards;
    }

    public String getEpasts() {
        return epasts;
    }

    public void setEpasts(String epasts) {
        this.epasts = epasts;
    }

    public String getTalrunis() {
        return talrunis;
    }

    public void setTalrunis(String talrunis) {
        this.talrunis = talrunis;
    }

    public String getLietotajvards() {
        return lietotajvards;
    }

    public void setLietotajvards(String lietotajvards) {
        this.lietotajvards = lietotajvards;
    }

    public String getParolesHash() {
        return parolesHash;
    }

    public void setParolesHash(String parolesHash) {
        this.parolesHash = parolesHash;
    }

    public String getLoma() {
        return loma;
    }

    public void setLoma(String loma) {
        this.loma = loma;
    }

    @Override
    public String toString() {
        return vards + " " + uzvards;
    }
}
