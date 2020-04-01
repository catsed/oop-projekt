package rassid;

import java.util.List;

public class RassInimene extends Rass {
    private List<Double> voimed;
    private double joud = 0.6;
    private double kiirus = 0.9;
    private double tapsus = 0.8;

    public RassInimene(String rass) {
        super(rass);
    }

    public List<Double> inimeseVoimed() {
        this.voimed = super.getVoimed(joud, kiirus, tapsus);
        return voimed;
    }

    @Override
    public String toString() {
        inimeseVoimed();
        return super.toString() + "Jõud on " + voimed.get(0) + ". Kiirus on " + voimed.get(1) + ". Täpsus on " + voimed.get(2);
    }
}