package projekt.olendid;

import javafx.scene.control.TextArea;
import projekt.klassid.Klass;
import projekt.rassid.Rass;

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

    public double runnak(double vastaseElud, TextArea valjund) {
        double elusidMaha = 0.0;
        String relv = klass.getRelv();

        List<String> meleeRelvad = Arrays.asList(Klass.MELEE_RELVAD);
        List<String> laskeRelvad = Arrays.asList(Klass.LASKE_RELVAD);
        List<String> maagiaRelvad = Arrays.asList(Klass.MAAGIA_RELVAD);

        StringBuilder valjundStr = new StringBuilder();
        valjundStr.append(String.format("%s ründab, kasutades relva %s.\n", super.getNimi(), klass.getRelv()));

        if (meleeRelvad.contains(relv)) { // rünnaku tugevus sõltub tegelase jõust või manast
            elusidMaha = rass.getJoud() / 2.0 * Math.random();
        } else if (laskeRelvad.contains(relv)) {
            if (Math.random() > rass.getTapsus() / 100.0) {
                valjundStr.append(String.format("%s lasi mööda.\n", super.getNimi()));
            } else {
                elusidMaha = rass.getJoud() / 2.0 * rass.getTapsus() / 100.0;
            }
        } else if (maagiaRelvad.contains(relv)) {
            elusidMaha = rass.getMana() / 2.0 * Math.random();
        }

        if (elusidMaha > vastaseElud)
            elusidMaha = vastaseElud;

        valjundStr.append(String.format("%s rünnak vähendab vastase elusid %.1f HP võrra.\n", super.getNimi(), elusidMaha));

        valjund.setText(valjundStr + "\n" + valjund.getText());
        return vastaseElud - elusidMaha;
    }

    public String getProfiil() {
        return "Sugu: " + sugu +
                "\nVanus: " + vanus +
                "\nRass: " + rass.toString() +
                "\nKlass: " + klass.toString() +
                "\nRelv: " + klass.getRelv() +
                "\nElud: " + String.format("%.1f", super.getElud()) +
                "\nMana: " + super.getMana() +
                "\nJõud: " + super.getJoud() +
                "\nKiirus: " + super.getKiirus() +
                "\nTäpsus: " + super.getTapsus();
    }

    @Override
    public String toString() {
        return "Nimi: " + super.getNimi() + "\n" +
                "Sugu: " + sugu + "\n" +
                "Vanus: "+ vanus + " aastane " + "\n" +
                "Rass: " + rass.toString() + "\n" +
                "Klass: "+ klass.toString();
    }
}