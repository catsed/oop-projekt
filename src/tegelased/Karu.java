package tegelased;

public class Karu extends Olend {
    public Karu() {
        super(25, 0, 55, 80, 0);
    }

    @Override
    public double runnak(double vastaseElud) {
        System.out.println("(" + super.getElud() + "H) Karu ründab.");

        double elusidMaha = super.getJoud() / 10.0;

        if (vastaseElud - elusidMaha > 0) {
            return vastaseElud - elusidMaha;
        } else {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        return "Karu" +
                "\n\tElud: " + super.getElud() +
                "\n\tJõud: " + super.getJoud() +
                "\n\tKiirus: " + super.getKiirus();
    }
}
