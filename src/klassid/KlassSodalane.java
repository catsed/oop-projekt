package klassid;

import java.util.List;

public class KlassSodalane extends Klass {
    private List<String> relvad;

    public KlassSodalane(String klass) {
        super(klass);
    }

    public String valiRelv(){
        relvad.add("Mõõk");
        relvad.add("Kahekäemõõk");
        relvad.add("Kirves");
        relvad.add("Pistoda");
        return super.valiRelv(relvad);
    }
    @Override
    public String toString() {
        valiRelv();
        return super.toString() + "Relvaks on " + valiRelv() + ". ";
    }
}
