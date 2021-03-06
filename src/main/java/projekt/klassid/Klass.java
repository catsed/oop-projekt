package projekt.klassid;

import java.util.Random;

public class Klass {
    public static final String[] MELEE_RELVAD = new String[]{"Rusikad", "Mõõk", "Kahekäemõõk", "Pistoda", "Kirves"};
    public static final String[] LASKE_RELVAD = new String[]{"Vibu", "Ammu", "Viskenuga", "Oda"};
    public static final String[] MAAGIA_RELVAD = new String[]{"Sau", "Võlukepp"};

    protected String relv = "Rusikad";
    private String klass;

    public Klass(String klass) {
        this.klass = klass;
    }

    public String setRelv(String[] relvad) {
        Random relvaValik = new Random();
        return relvad[relvaValik.nextInt(relvad.length)]; //valib suvalise relva
    }
    public String getRelv() {
        return relv;
    }

    @Override
    public String toString() {
        return klass;
    }
}
