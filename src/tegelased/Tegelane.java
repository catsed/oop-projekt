package tegelased;

import klassid.Klass;
import rassid.Rass;

import java.util.Arrays;
import java.util.List;

public class Tegelane extends Olend {
    private Klass klass;
    private Rass rass;
    private int vanus;
    private String sugu;
    private String nimi;

    public Tegelane(Klass klass, Rass rass, int vanus, String sugu, String nimi) {
        super(rass.getElud(), rass.getMana(), rass.getKiirus(), rass.getJoud(), rass.getTapsus());
        this.klass = klass;
        this.rass = rass;
        this.vanus = vanus;
        this.sugu = sugu;
        this.nimi = nimi;
    }

    public Klass getKlass() { //neid get meetodeid on vaja?
        return klass;
    }

    public Rass getRass() {
        return rass;
    }

    public int getVanus() {
        return vanus;
    }

    public String getSugu() {
        return sugu;
    }

    public String getNimi() {
        return nimi;
    }

    public double runnak(double vastaseElud) {
        double elusidMaha = 0.0;
        String relv = klass.getRelv();

        List<String> meleeRelvad = Arrays.asList(Klass.MELEE_RELVAD);
        List<String> laskeRelvad = Arrays.asList(Klass.LASKE_RELVAD);
        List<String> maagiaRelvad = Arrays.asList(Klass.MAAGIA_RELVAD);

        System.out.println("(" + super.getElud() + "H) " + nimi + " ründab, kasutades relva " + klass.getRelv() + ".");

        if (meleeRelvad.contains(relv)) { //rünnaku tugevus sõltub tegelase jõust või manast
            elusidMaha = rass.getJoud() / 10.0;
        } else if (laskeRelvad.contains(relv)) {
            if (Math.random() > rass.getTapsus() / 100.0) {
                System.out.println(nimi + " lasi mööda.");
            } else {
                elusidMaha = rass.getJoud() / 10.0;
            }
        } else if (maagiaRelvad.contains(relv)) {
            elusidMaha = rass.getMana() / 10.0;
        }

        if (vastaseElud - elusidMaha > 0) {
            return vastaseElud - elusidMaha;
        } else {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        return nimi +
                "\n\tSugu: " + sugu +
                "\n\tVanus: " + vanus +
                "\n\tRass: " + rass.toString() +
                "\n\tKlass: " + klass.toString() +
                "\n\tRelv: " + klass.getRelv() +
                "\n\tElud: " + super.getElud() +
                "\n\tMana: " + super.getMana() +
                "\n\tJõud: " + super.getJoud() +
                "\n\tKiirus: " + super.getKiirus() +
                "\n\tTäpsus: " + super.getTapsus();
    }
}