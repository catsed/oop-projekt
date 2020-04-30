package projekt.rassid;

public class RassInimene extends Rass {
    private double eludKordaja = 1.0;
    private double manaKordaja = 0.8;
    private double joudKordaja = 0.8;
    private double kiirusKordaja = 0.9;
    private double tapsusKordaja = 0.7;

    public RassInimene() {
        super("Inimene");
        super.setVoimed(eludKordaja, manaKordaja, joudKordaja, kiirusKordaja, tapsusKordaja);
    }
}
