package olendid;

import klassid.Klass;
import rassid.Rass;

import java.util.Arrays;
import java.util.List;

public class Tegelane extends Olend {
    private Klass klass;
    private Rass rass;
    private int vanus;
    private String sugu;

    public Tegelane(Klass klass, Rass rass, int vanus, String sugu, String nimi) {
        super(rass.getElud(), rass.getMana(), rass.getKiirus(), rass.getJoud(), rass.getTapsus(), nimi);
        this.klass = klass;
        this.rass = rass;
        this.vanus = vanus;
        this.sugu = sugu;
    }

    public double runnak(double vastaseElud) {
        double elusidMaha = 0.0;
        String relv = klass.getRelv();

        List<String> meleeRelvad = Arrays.asList(Klass.MELEE_RELVAD);
        List<String> laskeRelvad = Arrays.asList(Klass.LASKE_RELVAD);
        List<String> maagiaRelvad = Arrays.asList(Klass.MAAGIA_RELVAD);

        System.out.printf("(%.1fHP) %s ründab, kasutades relva %s.\n", super.getElud(), super.getNimi(), klass.getRelv());

        if (meleeRelvad.contains(relv)) { // rünnaku tugevus sõltub tegelase jõust või manast
            elusidMaha = rass.getJoud() / 2.0 * Math.random();
        } else if (laskeRelvad.contains(relv)) {
            if (Math.random() > rass.getTapsus() / 100.0) {
                System.out.printf("(%.1fHP) %s lasi mööda.\n", super.getElud(), super.getNimi());
            } else {
                elusidMaha = rass.getJoud() / 2.0 * rass.getTapsus() / 100.0;
            }
        } else if (maagiaRelvad.contains(relv)) {
            elusidMaha = rass.getMana() / 2.0 * Math.random();
        }

        if (elusidMaha > vastaseElud)
            elusidMaha = vastaseElud;

        System.out.printf("(%.1fHP) %s rünnak vähendab vastase elusid %.1f HP võrra.\n", super.getElud(), super.getNimi(), elusidMaha);

        return vastaseElud - elusidMaha;
    }

    @Override
    public String toString() {
        return super.getNimi() +
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