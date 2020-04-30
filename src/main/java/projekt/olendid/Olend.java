package projekt.olendid;

abstract public class Olend {
    private double elud;
    private double mana;
    private double kiirus;
    private double joud;
    private double tapsus;
    private boolean elus = true;
    private String nimi;

    public Olend(double elud, double mana, double kiirus, double joud, double tapsus, String nimi) {
        this.elud = elud;
        this.mana = mana;
        this.kiirus = kiirus;
        this.joud = joud;
        this.tapsus = tapsus;
        this.nimi = nimi;
    }
    public Olend(double elud, double mana, double kiirus, double joud, double tapsus, String nimi, boolean elus) {
        this.elud = elud;
        this.mana = mana;
        this.kiirus = kiirus;
        this.joud = joud;
        this.tapsus = tapsus;
        this.elus = elus;
        this.nimi = nimi;
    }

    public double getElud() {
        return elud;
    }
    public void setElud(double elud) {
        this.elud = elud;
    }

    public double getMana() {
        return mana;
    }

    public double getKiirus() {
        return kiirus;
    }

    public double getJoud() {
        return joud;
    }

    public double getTapsus() {
        return tapsus;
    }

    public String getNimi() {
        return nimi;
    }

    public abstract double runnak(double vastaseElud);
}
