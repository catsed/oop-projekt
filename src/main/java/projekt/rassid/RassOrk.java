package projekt.rassid;

public class RassOrk extends Rass {
    private double eludKordaja = 0.9;
    private double manaKordaja = 0.2;
    private double joudKordaja = 1.4;
    private double kiirusKordaja = 0.6;
    private double tapsusKordaja = 0.4;

    public RassOrk() {
        super("Ork");
        super.setVoimed(eludKordaja, manaKordaja, joudKordaja, kiirusKordaja, tapsusKordaja);
    }
}
