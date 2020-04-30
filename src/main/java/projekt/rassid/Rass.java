package projekt.rassid;

public class Rass {
    private String rass;
    private double elud = 100.0;
    private double mana = 100.0;
    private double joud = 100.0;
    private double kiirus = 100.0;
    private double tapsus = 100.0;

    public Rass(String rass) {
        this.rass = rass;
    }

    public void setVoimed(double eludKordaja, double manaKordaja, double jouKordaja, double kiiruseKordaja, double tapsuseKordaja) {
        elud *= eludKordaja; // muudab elud vastavaks rassile
        mana *= manaKordaja; // muudab mana vastavaks rassile
        joud *= jouKordaja; // muudab jõu vastavaks rassile
        kiirus *= kiiruseKordaja; // muudab kiiruse vastavaks rassile
        tapsus *= tapsuseKordaja; // muudab tapsuse vastavaks rassile
    }

    public double getElud() {
        return elud;
    }
    public double getMana() {
        return mana;
    }
    public double getJoud() {
        return joud;
    }
    public double getKiirus() {
        return kiirus;
    }
    public double getTapsus() {
        return tapsus;
    }

    @Override
    public String toString() {
        return rass;
    }
}
