package klassid;

import java.util.List;

public class KlassMaag extends Klass {
    private List<String> relvad;

    public KlassMaag(String klass) {
        super(klass);
    }

    public String valiRelv() {
        relvad.add("Sau");
        relvad.add("Mõõk");
        relvad.add("Võlukepp");
        relvad.add("Pistoda");
        return super.valiRelv(relvad);
    }

    @Override
    public String toString() {
        valiRelv();
        return super.toString() + "Relvaks on " + valiRelv() + ". ";
    }
}
