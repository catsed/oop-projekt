package klassid;

import java.util.List;

public class KlassVibukutt extends Klass {
    private List<String> relvad;

    public KlassVibukutt(String klass) {
        super(klass);
    }

    public String valiRelv(){
        relvad.add("Vibu");
        relvad.add("Ammu");
        relvad.add("Viskenuga");
        relvad.add("Oda");
        return super.valiRelv(relvad);
    }
    @Override
    public String toString() {
        valiRelv();
        return super.toString() + "Relvaks on " + valiRelv() + ". ";
    }
}
