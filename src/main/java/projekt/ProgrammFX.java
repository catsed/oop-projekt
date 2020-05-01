package projekt;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projekt.olendid.*;
import projekt.rassid.*;
import projekt.klassid.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;


public class ProgrammFX extends Application {

    // https://stackoverflow.com/questions/17047000/javafx-closing-a-tab-in-tabpane-dynamically
    private static void closeTab(Tab tab) {
        EventHandler<Event> handler = tab.getOnClosed();
        if (null != handler) {
            handler.handle(null);
        } else {
            tab.getTabPane().getTabs().remove(tab);
        }
    }

    private static Tegelane suvalineTegelane() { // meetod, mis genereerib suvalise tegelase
        Random random = new Random();
        String[] nimed = new String[]{"Icathmus", "Tuline", "Yuvia", "Trikos", "Umbra", "Iunas", "Jag", "Marik", "Tunis", "Pokin", "Galli", "Achaea", "Agrippa", "Amor", "Aquila", "Denarius", "Glaucia", "Iovita", "Solidus", "Annalis", "Barba", "Bestia", "Cinna", "Costa", "Curio", "Dives", "Galeo", "Merula", "Natta", "Nero", "Nerva", "Ralla", "Stolo", "Trio", "Ceres", "Minerva", "Pax", "Pomona", "Rohan"};
        String[] sood = new String[]{"Mees", "Naine"};
        Rass[] rassid = new Rass[]{new RassInimene(), new RassHaldjas(), new RassOrk(), new RassPakapikk()}; //Kõik rassid
        Klass[] klassid = new Klass[]{new KlassSodalane(), new KlassVibukutt(), new KlassMaag(), new KlassLinnaelanik()}; //Kõik klassid

        return new Tegelane(klassid[random.nextInt(klassid.length)],
                rassid[random.nextInt(rassid.length)],
                random.nextInt(85)+15,
                sood[random.nextInt(sood.length)],
                nimed[random.nextInt(nimed.length)]);
    }

    private static boolean eluKontroll(Olend olend) {
        return olend.getElud() == 0;
    }

    private static void looRunnak(Tegelane mangija, Olend vastane, Tab runnak) {
        // LAVA ERALDI SÕNUMI EDASTAMISEKS RÜNNAKU AJAL
        Stage sonumLava = new Stage();
        Label sonumKirjeldus = new Label("");
        Button sonumNupp = new Button("OK");

        VBox sonumKast = new VBox(10);
        sonumKast.setAlignment(Pos.CENTER);
        sonumKast.getChildren().addAll(sonumKirjeldus, sonumNupp);

        Scene sonumStseen = new Scene(sonumKast);
        sonumLava.setScene(sonumStseen);
        sonumLava.setHeight(120);
        sonumLava.setWidth(400);

        // RÜNNAKU TABI LOOMINE (eraldasin meetodisse, kuna sündmuste käsitlemine on niisama raskendatud)
        Text mangijaNimi = new Text(15, 40, mangija.getNimi());
        mangijaNimi.setFont(Font.font("system", FontWeight.BOLD, 20));
        Text mangijaHP = new Text(15, 60, "HP: " + String.format("%.1f", mangija.getElud()));
        mangijaHP.setFont(Font.font("system", 14));

        Text vastaneNimi = new Text(15, 280, vastane.getNimi());
        vastaneNimi.setFont(Font.font("system", FontWeight.BOLD, 20));
        Text vastaneHP = new Text(15, 300, "HP: " + String.format("%.1f", vastane.getElud()));
        vastaneHP.setFont(Font.font("system", 14));

        TextArea runnakInfo = new TextArea();
        runnakInfo.setMinWidth(200);
        runnakInfo.setMinHeight(75);
        runnakInfo.setLayoutX(15);
        runnakInfo.setLayoutY(70);
        runnakInfo.setEditable(false);

        ButtonBar mangijaNupud = new ButtonBar();
        Button rundaNupp = new Button("Ründa");
        Button kaitseNupp = new Button("Kaitse");
        Button pogeneNupp = new Button("Põgene");
        mangijaNupud.getButtons().addAll(rundaNupp, kaitseNupp, pogeneNupp);
        mangijaNupud.setLayoutX(350);
        mangijaNupud.setLayoutY(30);

        rundaNupp.setOnAction(event -> {
            if (Math.random() > 0.2) { // 80% tõenäosusega vastane ei kaitse ennast, 20% kordadest aga kaitseb
                vastane.setElud(mangija.runnak(vastane.getElud(), runnakInfo));
                vastaneHP.setText("HP: " + String.format("%.1f", vastane.getElud()));
                if (eluKontroll(vastane)) {
                    sonumKirjeldus.setText("Sa võitsid! Vastane on surnud.");
                    vastane.setElus(false);
                    closeTab(runnak);
                    sonumLava.initModality(Modality.APPLICATION_MODAL);
                    sonumLava.show();
                    sonumNupp.setOnAction(e -> sonumLava.hide());
                } else {
                    mangija.setElud(vastane.runnak(mangija.getElud(), runnakInfo));
                    mangijaHP.setText("HP: " + String.format("%.1f", mangija.getElud()));
                    if (eluKontroll(mangija)) {
                        sonumKirjeldus.setText("Sa surid! Pead mängu uuesti alustama.");
                        closeTab(runnak);
                        sonumLava.initModality(Modality.APPLICATION_MODAL);
                        sonumLava.show();
                        sonumNupp.setOnAction(e -> Platform.exit());
                    }
                }
            } else {
                runnakInfo.setText(vastane.getNimi() + " kaitses end, mängija ei saa seekord rünnata.\n\n" + runnakInfo.getText());
            }
        });

        kaitseNupp.setOnAction(event -> runnakInfo.setText(mangija.getNimi() + " kaitses end, vastane ei saa seekord rünnata.\n\n" + runnakInfo.getText()));

        pogeneNupp.setOnAction(event -> {
            sonumKirjeldus.setText("Sa põgenesid.");
            closeTab(runnak);
            sonumLava.initModality(Modality.APPLICATION_MODAL);
            sonumLava.show();
            sonumNupp.setOnAction(e -> sonumLava.hide());
        });

        AnchorPane runnakPane = new AnchorPane();
        runnakPane.setMinWidth(650);
        runnakPane.setMinHeight(400);
        runnakPane.getChildren().addAll(mangijaNimi, mangijaHP, mangijaNupud, runnakInfo, vastaneNimi, vastaneHP);
        runnak.setContent(runnakPane);
    }

    public void start(Stage peaLava) {
        Tegelane mangija = suvalineTegelane();
        List<Olend> valitud = new ArrayList<>();

        TabPane menuu = new TabPane();
        menuu.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab profiil = new Tab("Profiil");
        Tab linn = new Tab("Linn");
        Tab runnak = new Tab("Rünnak");

        // PROFIIL

        Text profiilNimi = new Text(15, 40, mangija.getNimi());
        profiilNimi.setFont(Font.font("system", FontWeight.BOLD, 30));

        Text profiilInfo = new Text(25, 65, mangija.getProfiil());
        profiilInfo.setFont(Font.font("system",15));

        Button profiilRunda = new Button("Ründa!");
        profiilRunda.setLayoutX(15);
        profiilRunda.setLayoutY(280);

        AnchorPane profiilPane = new AnchorPane();
        profiilPane.setMinWidth(650);
        profiilPane.setMinHeight(400);
        profiilPane.getChildren().addAll(profiilNimi, profiilInfo);
        profiil.setContent(profiilPane);

        profiilRunda.setOnAction(event -> {
            menuu.getTabs().add(runnak);
            looRunnak(mangija, valitud.get(0), runnak);
            menuu.getSelectionModel().select(runnak);
            profiilNimi.setText(mangija.getNimi());
            profiilInfo.setText(mangija.getProfiil());
            profiilPane.getChildren().remove(profiilRunda);
        });

        // LINN

        int elanikke = 5; // seda arvu muutes saab muuta linnaelanike arvu
        ObservableList<Olend> elanikud = FXCollections.observableArrayList();
        elanikud.add(mangija);
        for (int i = 0; i < elanikke; i++)
            elanikud.add(suvalineTegelane());
        elanikud.add(new Karu());

        ListView<Olend> elanikeList = new ListView(elanikud);
        elanikeList.setOrientation(Orientation.VERTICAL);
        elanikeList.setPrefSize(635, 330);

        linn.setOnSelectionChanged(event -> {
            if (linn.isSelected()) {
                elanikud.removeIf(olend -> !olend.isElus());
                elanikeList.setItems(elanikud);
            }
        });

        elanikeList.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                profiilNimi.setText(elanikeList.getSelectionModel().getSelectedItem().getNimi());
                profiilInfo.setText(elanikeList.getSelectionModel().getSelectedItem().getProfiil());

                if (!elanikeList.getSelectionModel().getSelectedItem().equals(mangija) && !profiilPane.getChildren().contains(profiilRunda)) {
                    profiilPane.getChildren().add(profiilRunda);
                } else if (elanikeList.getSelectionModel().getSelectedItem().equals(mangija) && profiilPane.getChildren().contains(profiilRunda)) {
                    profiilPane.getChildren().remove(profiilRunda);
                }

                valitud.clear();
                valitud.add(elanikeList.getSelectionModel().getSelectedItem());

                menuu.getSelectionModel().select(profiil);
            }
        });

        AnchorPane linnPane = new AnchorPane();
        linnPane.setMinWidth(650);
        linnPane.setMinHeight(400);
        linnPane.getChildren().addAll(elanikeList);
        linn.setContent(linnPane);

        // RÜNNAK

        menuu.getTabs().add(profiil);
        menuu.getTabs().add(linn);

        VBox peaVBox = new VBox(menuu);

        //stseeni loomine ja naitamine
        Scene stseen = new Scene(peaVBox);

        peaLava.setScene(stseen);
        peaLava.setResizable(false);
        peaLava.setWidth(650);
        peaLava.setHeight(400);
        peaLava.setTitle("Rollimäng");

        peaLava.show();
    }
}
