package klassid;

import java.util.List;

abstract public class Klass {
    private String klass;

    public Klass(String klass) {
        this.klass = klass;
    }

    public String valiRelv(List<String> relvad){
        int pikkus = relvad.size();
        int valiRelv = (int)(Math.random()*pikkus);
        return relvad.get(valiRelv);
    }

    @Override
    public String toString() {
        return "Tegelase klass on " + klass + ".";
    }
}
