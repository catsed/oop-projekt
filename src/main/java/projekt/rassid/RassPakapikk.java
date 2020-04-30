package projekt.rassid;

public class RassPakapikk extends Rass {
    private double eludKordaja = 1.0;
    private double manaKordaja = 0.5;
    private double joudKordaja = 0.7;
    private double kiirusKordaja = 0.5;
    private double tapsusKordaja = 0.95;

    public RassPakapikk() {
        super("Päkapikk");
        super.setVoimed(eludKordaja, manaKordaja, joudKordaja, kiirusKordaja, tapsusKordaja);
    }
}
