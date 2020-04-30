package projekt.olendid;

public class Karu extends Olend {
    public Karu() {
        super(25, 0, 55, 80, 0, "Karu");
    }

    @Override
    public double runnak(double vastaseElud) { //Meetod karu rünnaku jaoks
        System.out.printf("(%.1fHP) Karu ründab.\n", super.getElud());

        double elusidMaha = super.getJoud() / 2.0 * Math.random();

        if (elusidMaha > vastaseElud)
            elusidMaha = vastaseElud;

        System.out.printf("(%.1fHP) Karu rünnak vähendab vastase elusid %.1f HP võrra.\n", super.getElud(), elusidMaha);

        return vastaseElud - elusidMaha;
    }

    @Override
    public String toString() {
        return "Karu" +
                "\n\tElud: " + super.getElud() +
                "\n\tJõud: " + super.getJoud() +
                "\n\tKiirus: " + super.getKiirus();
    }
}
