package projekt.olendid;

import javafx.scene.control.TextArea;

public class Karu extends Olend {
    public Karu() {
        super(25, 0, 55, 80, 0, "Karu");
    }

    @Override
    public double runnak(double vastaseElud, TextArea valjund) { //Meetod karu rünnaku jaoks
        StringBuilder valjundStr = new StringBuilder();
        valjundStr.append("Karu ründab.\n");

        double elusidMaha = super.getJoud() / 2.0 * Math.random();

        if (elusidMaha > vastaseElud)
            elusidMaha = vastaseElud;

        valjundStr.append(String.format("Karu rünnak vähendab vastase elusid %.1f HP võrra.\n", elusidMaha));

        valjund.setText(valjundStr + valjund.getText());
        return vastaseElud - elusidMaha;
    }

    public String getProfiil() {
        return "Elud: " + String.format("%.1f", super.getElud()) +
                "\nJõud: " + super.getJoud() +
                "\nKiirus: " + super.getKiirus();
    }

    @Override
    public String toString() {
        return "Karu";
    }
}
