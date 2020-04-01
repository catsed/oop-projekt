package rassid;

import java.util.List;

public class RassPakapikk extends Rass {
    private List<Double> voimed;
    private double joud = 0.7;
    private double kiirus = 0.5;
    private double tapsus = 0.9;

    public RassPakapikk(String rass) {
        super(rass);
    }

    public List<Double> pakapikuVoimed() {
        this.voimed = super.getVoimed(joud, kiirus, tapsus);
        return voimed;
    }

    @Override
    public String toString() {
        pakapikuVoimed();
        return super.toString() + "Jõud on " + voimed.get(0) + ". Kiirus on " + voimed.get(1) + ". Täpsus on " + voimed.get(2);
    }
}
