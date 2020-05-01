package projekt;

/*
* Peaklass
 */


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import projekt.olendid.*;
import projekt.klassid.*;
import projekt.rassid.*;

import java.util.Random;
import java.util.Scanner;


public class Programm extends Application {

    /*static Tegelane suvalineTegelane() { // meetod, mis genereerib suvalise tegelase
        Random random = new Random();
        String[] nimed = new String[]{"Icathmus", "Tuline", "Yuvia", "Trikos", "Umbra", "Iunas", "Jag", "Marik", "Tunis", "Pokin", "Galli"};
        String[] sood = new String[]{"Mees", "Naine"};
        Rass[] rassid = new Rass[]{new RassInimene(), new RassHaldjas(), new RassOrk(), new RassPakapikk()}; //Kõik rassid
        Klass[] klassid = new Klass[]{new KlassSodalane(), new KlassVibukutt(), new KlassMaag(), new KlassLinnaelanik()}; //Kõik klassid

        return new Tegelane(klassid[random.nextInt(klassid.length)],
                rassid[random.nextInt(rassid.length)],
                random.nextInt(85)+15,
                sood[random.nextInt(sood.length)],
                nimed[random.nextInt(nimed.length)]);
    }

    static boolean eluKontroll(double mangija, double vastane) {
        System.out.println();

        if (mangija == 0) {
            System.out.println("Sa surid!");
            return true;
        }
        if (vastane == 0) {
            System.out.println("Sa võitsid!");
            return true;
        }

        return false;
    }

     */

    public void start(Stage primaryStage) {
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            double korgus = primaryStage.getHeight();
        });
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            double laius = primaryStage.getWidth();
        });

        Group juur = new Group();
        Text text, text1, text2;
        text = new Text("Tere tulemast! See on Marki ja Saskia projekt.");
        text1 = new Text("See on väike rollimäng, kus saad luua oma tegelase ning proovida ründamist." + "\n-----------------------------------------------\n");
        text2 = new Text("Alustuseks, mis on sinu tegelase nimi?" );
        VBox vBox = new VBox();
        vBox.getChildren().addAll(text, text1, text2);


        TextField tekst = new TextField();
        vBox.getChildren().add(tekst);


        tekst.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                String nimi = tekst.getText();
                Text text3 = new Text("Tere, " + nimi + "!\n");
                vBox.getChildren().add(text3);
                vBox.getChildren().add(new Text("Mis on sinu tegelase sugu?"));
                RadioButton radioButton, radioButton1;
                radioButton = new RadioButton("Mees");
                radioButton1 = new RadioButton("Naine");
                ToggleGroup toggleGroup = new ToggleGroup();
                radioButton.setToggleGroup(toggleGroup);
                radioButton1.setToggleGroup(toggleGroup);
                vBox.getChildren().addAll(radioButton,radioButton1);
                radioButton.setOnAction(event1 -> {
                    String sugu = "Mees";
                    //System.out.println(sugu);
                });
                radioButton1.setOnAction(event12 -> {
                    String sugu = "Naine";
                    //System.out.println(sugu);
                });

            }

        });

        juur.getChildren().addAll(vBox);
        Scene stseen = new Scene(juur, 535,535);
        primaryStage.setScene(stseen);
        primaryStage.setTitle("Projekt");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


    /*public static void main(String[] args) throws InterruptedException {
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

        // MÄNGIJA TEGELASE INFO //

        System.out.println("Sinu tegelase info:");
        Tegelane mangija = new Tegelane(klass, rass, vanus, (sugu.equals("M") ? "Mees" : "Naine"), nimi);
        System.out.println(mangija.toString());
        System.out.println("\nKui oled rahul oma tegelasega, kirjuta 'jätka', et jätkata.");
        while (!sc.hasNext("(?i)jätka"))
            sc.next();
        String s = sc.next(); // püüab kinni sisendi, tegelikult seda kasutada pole vaja, aga muidu segab hilisemaid sisendeid

        System.out.println("\n-----------------------------------------------\n");

        // TEGELASTE GENEREERIMINE JA VÄLJASTAMINE //

        Tegelane suvaline1 = suvalineTegelane();
        Tegelane suvaline2 = suvalineTegelane();
        Karu karu = new Karu();

        Olend[] olendid = new Olend[]{suvaline1, suvaline2, karu};

        StringBuilder kontrollMuster = new StringBuilder(); // see tuleb hiljem kasuks scanner sisendi kontrollimiseks
        System.out.println("Asud nüüd linnas. Linnas on veel järgmised olendid:");
        for (Olend olend : olendid) {
            System.out.println("\n" + olend.toString());
            kontrollMuster.append(olend.getNimi() + "|");
        }

        System.out.println("\n-----------------------------------------------\n");

        // RÜNDAMISE OSA //

        System.out.println("Keda sooviksid rünnata?");
        while (!sc.hasNext("("+kontrollMuster.toString()+")")) {
            System.out.println("Proovi uuesti.");
            sc.next();
        }
        String runnatavStr = sc.next();

        Olend runnatav = null;  //vaadatakse keda rünnatakse
        for (Olend olend : olendid) {
            if (runnatavStr.equals(olend.getNimi())) {
                runnatav = olend;
            }
        }

        System.out.println("Rünnak algab.\n");
        while (true) { //rünnatakse kordamööda
            runnatav.setElud(mangija.runnak(runnatav.getElud()));
            Thread.sleep(1000 - (long)runnatav.getKiirus() * 2);

            if (eluKontroll(mangija.getElud(), runnatav.getElud()))
                break;

            mangija.setElud(runnatav.runnak(mangija.getElud()));
            Thread.sleep(1000 - (long)mangija.getKiirus() * 2);

            if (eluKontroll(mangija.getElud(), runnatav.getElud()))
                break;
        }

    }

     */
}
