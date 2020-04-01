package rassid;

import java.util.ArrayList;
import java.util.List;

abstract public class Rass {
    private String rass;
    List<Double> voimed = new ArrayList<>();

    public Rass(String rass) {
        this.rass = rass;
    }

    public List<Double> getVoimed(double jouKordaja, double kiiruseKordaja, double tapsuseKordaja) {
        for(int i=0; i<3; i++){
            voimed.add(100.0);
        }
        voimed.set(0,voimed.get(0)*jouKordaja); //muudab jõu vastavaks rassile
        voimed.set(1, voimed.get(1)*kiiruseKordaja); //muudab kiiruse vastavaks rassile
        voimed.set(2, voimed.get(2)*tapsuseKordaja); //muudab tapsuse vastavaks rassile
        return voimed; // list(jõud, kiirus, täpsus)
    }

    @Override
    public String toString() {
        return "Tegelase rass on " + rass + ". ";
    }
}
