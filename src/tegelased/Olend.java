package tegelased;

abstract public class Olend {
    private double elud;
    private double mana;
    private double kiirus;
    private double joud;
    private double tapsus;
    private boolean elus = true;

    public Olend(double elud, double mana, double kiirus, double joud, double tapsus) {
        this.elud = elud;
        this.mana = mana;
        this.kiirus = kiirus;
        this.joud = joud;
        this.tapsus = tapsus;
    }
    public Olend(double elud, double mana, double kiirus, double joud, double tapsus, boolean elus) {
        this.elud = elud;
        this.mana = mana;
        this.kiirus = kiirus;
        this.joud = joud;
        this.tapsus = tapsus;
        this.elus = elus;
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
    public void setMana(double mana) {
        this.elud = mana;
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

    public boolean isElus() {
        return elus;
    }

    public abstract double runnak(double vastaseElud);
}
