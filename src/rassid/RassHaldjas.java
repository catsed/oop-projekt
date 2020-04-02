package rassid;

public class RassHaldjas extends Rass {
    private double eludKordaja = 1.2;
    private double manaKordaja = 1.5;
    private double joudKordaja = 0.3;
    private double kiirusKordaja = 0.8;
    private double tapsusKordaja = 0.9;

    public RassHaldjas() {
        super("Haldjas");
        super.setVoimed(eludKordaja, manaKordaja, joudKordaja, kiirusKordaja, tapsusKordaja);
    }
}
