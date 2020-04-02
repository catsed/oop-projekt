/*
* Peaklass
 */

import rassid.*;
import klassid.*;
import tegelased.*;

import java.util.Random;
import java.util.Scanner;

public class Programm {

    static Tegelane suvalineTegelane() {
        Random random = new Random();
        String[] nimed = new String[]{"Icathmus", "Tuline", "Yuvia", "Trikos", "Umbra", "Iunas", "Jag", "Marik", "Tunis", "Pokin", "Galli"};
        String[] sood = new String[]{"Mees", "Naine"};
        Rass[] rassid = new Rass[]{new RassInimene(), new RassHaldjas(), new RassOrk(), new RassPakapikk()}; //Kõik rassid
        Klass[] klassid = new Klass[]{new KlassSodalane(), new KlassVibukutt(), new KlassMaag(), new KlassLinnaelanik()}; //Kõik klassid

        return new Tegelane(klassid[random.nextInt(klassid.length)], //tegelased linna
                rassid[random.nextInt(rassid.length)],
                random.nextInt(85)+15,
                sood[random.nextInt(sood.length)],
                nimed[random.nextInt(nimed.length)]);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Tere tulemast! See on Marki ja Saskia projekt.");
        System.out.println("See on väike rollimäng, kus saad luua oma tegelase ning proovida ründamist.");
        System.out.println("-----------------------------------------------\n");

        Scanner sc = new Scanner(System.in);

        System.out.println("Alustuseks, mis on sinu tegelase nimi?");
        String nimi = sc.next();
        System.out.println("Tere, " + nimi + "!\n");

        System.out.println("Mis on sinu tegelase sugu? [M/N]");
        while (!sc.hasNext("(?i)[mn]")) {
            System.out.println("Proovi uuesti.");
            sc.next();
        }
        String sugu = sc.next();

        int vanus;
        System.out.println("\nKui vana sinu tegelane on?");
        do {
            while (!sc.hasNextInt()) {
                System.out.println("Proovi uuesti.");
                sc.next();
            }
            vanus = sc.nextInt();
            if (vanus <= 0) {
                System.out.println("Vanus peab olema positiivne arv.");
            }
        } while (vanus <= 0);

        System.out.println("\nHästi! Nüüd valime sinu rassi.");
        System.out.println("\tInimene - kõige tavalisem rass, võimete poolest kõiges keskmine, kuid mitte milleski parim.");
        System.out.println("\tHaldjas - maagiline rass, võimete poolest parim maagias, jõu poolest nõrk.");
        System.out.println("\tOrk - 'barbarite' rass, väga tugev, aga kõiges muus mitte eriti osav.");
        System.out.println("\tPäkapikk - terava mõistusega rass, imeline täpsus, kõiges muus mitte nii osav.");
        System.out.println("\nMilline saab olema sinu tegelase rass?");
        while (!sc.hasNext("(?i)(i(nimene)?|h(aldjas)?|o(rk)?|p(äkapikk)?)")) { //ainult esitäht on ka lubatud kirjutada
            System.out.println("Proovi uuesti.");
            sc.next();
        }
        String rassStr = sc.next();

        System.out.println("\nHästi! Nüüd valime sinu klassi. Hoia meeles, milles sinu rass parim on!");
        System.out.println("\tSõdalane - saab teha meleerünnakuid.");
        System.out.println("\tVibukütt - saab teha laskerünnakuid.");
        System.out.println("\tMaag - saab teha maagiarünnakuid.");
        System.out.println("\nMilline saab olema sinu tegelase klass?");
        while (!sc.hasNext("(?i)(s(õdalane)?|v(ibukütt)?|m(aag)?)")) {
            System.out.println("Proovi uuesti.");
            sc.next();
        }
        String klassStr = sc.next();


        System.out.println("\nHästi! Sinu tegelane on loodud!");

        Rass rass;
        switch(rassStr.toLowerCase()) { //rassi määramine
            case "i":
            case "inimene":
                rass = new RassInimene();
                break;
            case "h":
            case "haldjas":
                rass = new RassHaldjas();
                break;
            case "o":
            case "ork":
                rass = new RassOrk();
                break;
            case "p":
            case "päkapikk":
                rass = new RassPakapikk();
                break;
            default:
                rass = new Rass("midagi on valesti");
                break;
        }

        Klass klass;
        switch(klassStr.toLowerCase()) { //klassi määramine
            case "s":
            case "sõdalane":
                klass = new KlassSodalane();
                break;
            case "v":
            case "vibukütt":
                klass = new KlassVibukutt();
                break;
            case "m":
            case "maag":
                klass = new KlassMaag();
                break;
            default:
                klass = new Klass("midagi on valesti");
                break;
        }

        System.out.println("Sinu tegelase info:");
        Tegelane mangija = new Tegelane(klass, rass, vanus, (sugu.equals("M") ? "Mees" : "Naine"), nimi);
        System.out.println(mangija.toString());
        System.out.println("\nKui oled rahul oma tegelasega, kirjuta 'jätka', et jätkata.");
        while (!sc.hasNext("(?i)jätka")) {
            sc.next();
        }
        String s = sc.next();

        System.out.println("\n-----------------------------------------------\n");

        Tegelane suvaline1 = suvalineTegelane();
        Tegelane suvaline2 = suvalineTegelane();
        Karu karu = new Karu();

        Olend[] olendid = new Olend[]{suvaline1, suvaline2, karu};

        System.out.println("Asud nüüd linnas. Linnas on veel järgmised olendid:");
        for (Olend olend : olendid) {
            System.out.println("\n" + olend.toString());
        }
        System.out.println("\n-----------------------------------------------\n");
        System.out.println("Keda sooviksid rünnata?");
        while (!sc.hasNext("(?i)("+suvaline1.getNimi()+"|"+suvaline2.getNimi()+"|karu)")) {
            System.out.println("Proovi uuesti.");
            sc.next();
        }
        String runnatavStr = sc.next().toLowerCase();

        Olend runnatav = null;  //vaadatakse keda rünnatakse
        if (runnatavStr.equals(suvaline1.getNimi().toLowerCase())) {
            runnatav = suvaline1;
        } else if (runnatavStr.equals(suvaline2.getNimi().toLowerCase())) {
            runnatav = suvaline2;
        } else if (runnatavStr.equals("karu")) {
            runnatav = karu;
        }

        System.out.println("Rünnak algab.\n");
        while (true) {
            if (mangija.getElud() == 0) {  //rünnak kestab kuni kumbki sureb
                System.out.println("Sa surid!");
                break;
            }
            if (runnatav.getElud() == 0) {
                System.out.println("Sa võitsid!");
                break;
            }
            runnatav.setElud(mangija.runnak(runnatav.getElud())); //rünnatakse kordamööda
            Thread.sleep(1000);
            mangija.setElud(runnatav.runnak(mangija.getElud()));
            Thread.sleep(1000);
        }

    }
}
