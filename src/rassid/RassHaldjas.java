package rassid;

import java.util.List;

public class RassHaldjas extends Rass {
    private List<Double> voimed;
    private double joud = 0.7;
    private double kiirus = 0.8;
    private double tapsus = 0.9;

    public RassHaldjas(String rass) {
        super(rass);
    }

    public List<Double> haljdaVoimed() {
        this.voimed = super.getVoimed(joud, kiirus, tapsus);
        return voimed;
    }

    @Override
    public String toString() {
        haljdaVoimed();
        return super.toString() + "Jõud on " + voimed.get(0) + ". Kiirus on " + voimed.get(1) + ". Täpsus on " + voimed.get(2);
    }

}
