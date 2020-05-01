package projekt;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projekt.olendid.*;
import projekt.rassid.*;
import projekt.klassid.*;

import java.io.*;
import java.util.*;

public class Programm extends Application {

    // https://stackoverflow.com/questions/17047000/javafx-closing-a-tab-in-tabpane-dynamically
    private static void closeTab(Tab tab) {
        EventHandler<Event> handler = tab.getOnClosed();
        if (null != handler) {
            handler.handle(null);
        } else {
            tab.getTabPane().getTabs().remove(tab);
        }
    }

    private static Tegelane looMangija(Map<String, String> tegelaseInfo) {
        Klass klass;
        switch(tegelaseInfo.get("Klass").toLowerCase()) { // klassi määramine
            case "sõdalane":
                klass = new KlassSodalane();
                break;
            case "maag":
                klass = new KlassMaag();
                break;
            case "vibukütt":
                klass = new KlassVibukutt();
                break;
            default:
                klass = new Klass("midagi on valesti");
                break;
        }

        Rass rass;
        switch(tegelaseInfo.get("Rass").toLowerCase()) { // rassi määramine
            case "inimene":
                rass = new RassInimene();
                break;
            case "haldjas":
                rass = new RassHaldjas();
                break;
            case "ork":
                rass = new RassOrk();
                break;
            case "päkapikk":
                rass = new RassPakapikk();
                break;
            default:
                rass = new Rass("midagi on valesti");
                break;
        }

        return new Tegelane(klass, rass, Integer.parseInt(tegelaseInfo.get("Vanus")), tegelaseInfo.get("Sugu"), tegelaseInfo.get("Nimi"));
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
        sonumLava.setTitle("Teade");

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
                        sonumLava.setOnCloseRequest(e -> Platform.exit());
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
        Tegelane[] mangija = { suvalineTegelane() }; // kuna lambda funktsioonides on muutujate sättimine raskendatud,
                                                     // on massiiv abiks wrapperina

        /////////////////////// LAVA ERALDI SÕNUMI EDASTAMISEKS
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
        sonumLava.setTitle("Teade");

        /////////////////////// TEGELASE LOOMINE
        Map<String, String> tegelaseInfo = new HashMap<>(); // siia salvestatakse loomise käigus tegelase info

        Stage loomineLava = new Stage();
        VBox loomine = new VBox(10);
        loomine.setMinWidth(650);
        loomine.setMinHeight(200);
        loomine.setPadding(new Insets(10, 10, 10, 10));

        Button nuppUus = new Button("Loon uue tegelase");
        Button nuppVana = new Button("Soovin kasutada juba varem loodud tegelast");
        HBox nupud = new HBox(5);
        nupud.getChildren().addAll(nuppUus, nuppVana);
        nupud.setLayoutY(80);

        Text tekst1 = new Text(0, 30, "Tere tulemast! See on Marki ja Saskia projekt.");
        tekst1.setFont(Font.font("system", FontWeight.BOLD, 20));
        tekst1.setTextAlignment(TextAlignment.LEFT);
        Text tekst2 = new Text(0, 50, "See on väike rollimäng, kus saad luua oma tegelase ning proovida ründamist.\n");
        tekst2.setFont(Font.font("system", 13));
        tekst2.setTextAlignment(TextAlignment.LEFT);
        loomine.getChildren().addAll(tekst1, tekst2, nupud);

        Button nuppEdasi = new Button("Edasi");

        nuppUus.setOnMousePressed(event -> {
            nuppUus.setDisable(true);
            nuppVana.setDisable(true);

            // NIMI
            Text nimiKus = new Text("\nAlustuseks, mis on sinu tegelase nimi?");
            nimiKus.setFont(Font.font("system", FontWeight.BOLD, 13));
            TextField nimiVas = new TextField();
            nimiVas.setMaxWidth(650);
            loomine.getChildren().addAll(nimiKus, nimiVas);

            nimiVas.setOnKeyPressed(event1 -> {
                if (event1.getCode() == KeyCode.ENTER) {
                    nimiVas.setDisable(true);
                    Text nimiTekst = new Text("Tere, " + nimiVas.getText() + "!\n");
                    loomine.getChildren().add(nimiTekst);

                    // SUGU
                    Text suguKus = new Text("Mis on sinu tegelase sugu?");
                    suguKus.setFont(Font.font("system", FontWeight.BOLD, 13));
                    RadioButton suguMees, suguNaine;
                    suguMees = new RadioButton("Mees");
                    suguNaine = new RadioButton("Naine");
                    ToggleGroup suguValik = new ToggleGroup();
                    suguMees.setToggleGroup(suguValik);
                    suguNaine.setToggleGroup(suguValik);
                    loomine.getChildren().addAll(suguKus, suguMees, suguNaine);

                    suguValik.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
                        suguValik.getToggles().forEach(toggle -> {
                            Node node = (Node) toggle;
                            node.setDisable(true);
                        });

                        // VANUS
                        Text vanusKus = new Text("\nKui vana sinu tegelane on?");
                        vanusKus.setFont(Font.font("system", FontWeight.BOLD, 13));
                        Slider vanusSlider = new Slider(1, 100, 1);
                        vanusSlider.setShowTickMarks(true);
                        vanusSlider.setShowTickLabels(true);
                        vanusSlider.setBlockIncrement(10);
                        Label vanus = new Label("Vanus: 0");
                        vanusSlider.valueProperty().addListener(
                                (observable, oldValue, newValue) -> vanus.setText("Vanus: " + Math.round((Double) newValue)));
                        vanusSlider.setMaxWidth(615);
                        loomine.getChildren().addAll(vanusKus, vanusSlider, vanus);

                        vanusSlider.setOnMouseReleased(event2 -> {
                            vanusSlider.setDisable(true);

                            // RASS
                            Text rassKus = new Text("\nHästi! Nüüd valime sinu rassi.");
                            rassKus.setFont(Font.font("system", FontWeight.BOLD, 13));
                            RadioButton valikInimene, valikHaldjas, valikOrk, valikPakapikk;
                            valikInimene = new RadioButton("Inimene - kõige tavalisem rass, võimete poolest kõiges keskmine, kuid mitte milleski parim.");
                            valikHaldjas = new RadioButton("Haldjas - maagiline rass, võimete poolest parim maagias, jõu poolest nõrk.");
                            valikOrk = new RadioButton("Ork - 'barbarite' rass, väga tugev, aga kõiges muus mitte eriti osav.");
                            valikPakapikk = new RadioButton("Päkapikk - terava mõistusega rass, imeline täpsus, kõiges muus mitte nii osav.\n");
                            ToggleGroup rassValik = new ToggleGroup();
                            valikInimene.setToggleGroup(rassValik);
                            valikHaldjas.setToggleGroup(rassValik);
                            valikOrk.setToggleGroup(rassValik);
                            valikPakapikk.setToggleGroup(rassValik);
                            loomine.getChildren().addAll(rassKus, valikInimene, valikHaldjas, valikOrk, valikPakapikk);

                            rassValik.selectedToggleProperty().addListener((ov1, old_toggle1, new_toggle1) -> {
                                rassValik.getToggles().forEach(toggle -> {
                                    Node node = (Node) toggle;
                                    node.setDisable(true);
                                });

                                // KLASS
                                Text klassKus = new Text("\nHästi! Nüüd valime sinu klassi. Hoia meeles, milles sinu rass parim on!");
                                klassKus.setFont(Font.font("system", FontWeight.BOLD, 13));
                                RadioButton valikSodalane, valikVibukutt, valikMaag;
                                valikSodalane = new RadioButton("Sõdalane - saab teha meleerünnakuid.");
                                valikVibukutt = new RadioButton("Vibukütt - saab teha laskerünnakuid.");
                                valikMaag = new RadioButton("Maag - saab teha maagiarünnakuid.");
                                ToggleGroup klassValik = new ToggleGroup();
                                valikSodalane.setToggleGroup(klassValik);
                                valikVibukutt.setToggleGroup(klassValik);
                                valikMaag.setToggleGroup(klassValik);
                                loomine.getChildren().addAll(klassKus, valikSodalane, valikVibukutt, valikMaag);

                                klassValik.selectedToggleProperty().addListener((ov11, old_toggle11, new_toggle11) -> {
                                    klassValik.getToggles().forEach(toggle -> {
                                        Node node = (Node) toggle;
                                        node.setDisable(true);
                                    });

                                    // LOODUD
                                    Text loodudTekst = new Text("\nHästi! Sinu tegelane on loodud!\n");
                                    loodudTekst.setFont(Font.font("system", FontWeight.BOLD, 13));
                                    loomine.getChildren().add(loodudTekst);

                                    HBox loodudNupud = new HBox(10);
                                    loodudNupud.getChildren().addAll(nuppEdasi);
                                    loomine.getChildren().add(loodudNupud);

                                    tegelaseInfo.put("Nimi", nimiVas.getText());

                                    if (String.valueOf(suguValik.getSelectedToggle()).contains("Naine")) {
                                        tegelaseInfo.put("Sugu", "Naine");
                                    } else {
                                        tegelaseInfo.put("Sugu", "Mees");
                                    }

                                    tegelaseInfo.put("Vanus", String.valueOf(vanus.getText()).replace("Vanus: ", ""));

                                    RadioButton rass = (RadioButton)rassValik.getSelectedToggle();
                                    tegelaseInfo.put("Rass", (rass.getText().split(" "))[0]);

                                    RadioButton klass = (RadioButton)klassValik.getSelectedToggle();
                                    tegelaseInfo.put("Klass", (klass.getText().split(" "))[0]);

                                    try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tegelaseInfo.get("Nimi")+".txt"), "UTF-8"))) {
                                        for (Map.Entry<String, String> info : tegelaseInfo.entrySet()) {
                                            bw.write(info.getKey() + ": " + info.getValue() + "\n");
                                        }
                                    } catch (IOException e) {
                                        loomine.getChildren().clear();
                                        tegelaseInfo.clear();
                                        sonumKirjeldus.setText("Midagi läks salvestamisel valesti.");
                                        sonumLava.show();
                                        sonumNupp.setOnAction(event4 -> sonumLava.hide());

                                        nuppUus.setDisable(false);
                                        nuppVana.setDisable(false);
                                        loomine.getChildren().addAll(tekst1, tekst2, nupud);
                                    }
                                });
                            });
                        });
                    });
                }
            });
        });

        nuppVana.setOnMousePressed(event -> {
            nuppUus.setDisable(true);
            nuppVana.setDisable(true);

            Text nimiKus = new Text("Sisesta oma tegelase nimi");
            nimiKus.setFont(Font.font("system", FontWeight.BOLD, 13));

            loomine.getChildren().add(nimiKus);
            TextField tekstiAla = new TextField();
            tekstiAla.setMaxWidth(650);
            loomine.getChildren().add(tekstiAla);

            tekstiAla.setOnKeyPressed(event1 -> {
                if(event1.getCode() == KeyCode.ENTER){
                    tekstiAla.setDisable(true);
                    String nimi = tekstiAla.getText();
                    tegelaseInfo.put("Nimi", nimi);

                    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nimi+".txt"), "UTF-8"))) {
                        String rida = br.readLine();

                        for (int i = 0; i < 4; i++) {
                            rida = br.readLine();
                            String[] osad = rida.split(": ");
                            tegelaseInfo.put(osad[0], osad[1]);
                        }

                        loomine.getChildren().add(new Text("Sinu tegelase info: "));
                        loomine.getChildren().add(new Text("Nimi: " + tegelaseInfo.get("Nimi") + "\nSugu: " + tegelaseInfo.get("Sugu") +
                                    "\nVanus: " + tegelaseInfo.get("Vanus") + "\nRass: " + tegelaseInfo.get("Rass") + "\nKlass: " + tegelaseInfo.get("Klass")));

                        loomine.getChildren().add(nuppEdasi);
                    } catch (IOException e) {
                        tegelaseInfo.clear();
                        loomine.getChildren().clear();
                        sonumKirjeldus.setText("Tundub, et sellist tegelast pole olemas.");
                        sonumLava.show();
                        sonumNupp.setOnAction(event2 -> sonumLava.hide());

                        nuppUus.setDisable(false);
                        nuppVana.setDisable(false);
                        loomine.getChildren().addAll(tekst1, tekst2, nupud);
                    }
                }
            });
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(loomine);
        scrollPane.setMaxWidth(400);
        Scene loomineStseen = new Scene(scrollPane);
        loomineLava.setScene(loomineStseen);
        loomineLava.setResizable(false);
        loomineLava.setWidth(700);
        loomineLava.setHeight(700);
        loomineLava.setTitle("Rollimäng");
        loomineLava.show();

        /////////////////////// MÄNG ALGAB SIIT

        nuppEdasi.setOnMousePressed(event3 ->  {
            mangija[0] = looMangija(tegelaseInfo);
            loomineLava.hide();

            List<Olend> valitud = new ArrayList<>();

            TabPane menuu = new TabPane();
            menuu.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

            Tab profiil = new Tab("Profiil");
            Tab linn = new Tab("Linn");
            Tab runnak = new Tab("Rünnak");

            // PROFIIL

            Text profiilNimi = new Text(15, 40, mangija[0].getNimi());
            profiilNimi.setFont(Font.font("system", FontWeight.BOLD, 30));

            Text profiilInfo = new Text(25, 65, mangija[0].getProfiil());
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
                looRunnak(mangija[0], valitud.get(0), runnak);
                menuu.getSelectionModel().select(runnak);
                profiilNimi.setText(mangija[0].getNimi());
                profiilInfo.setText(mangija[0].getProfiil());
                profiilPane.getChildren().remove(profiilRunda);
            });

            // LINN

            int elanikke = 5; // seda arvu muutes saab muuta linnaelanike arvu
            ObservableList<Olend> elanikud = FXCollections.observableArrayList();
            elanikud.add(mangija[0]);
            for (int i = 0; i < elanikke; i++)
                elanikud.add(suvalineTegelane());
            elanikud.add(new Karu());

            ListView<Olend> elanikeList = new ListView(elanikud);
            elanikeList.setOrientation(Orientation.VERTICAL);
            elanikeList.setPrefSize(635, 310);
            elanikeList.setLayoutY(20);

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

                    if (!elanikeList.getSelectionModel().getSelectedItem().equals(mangija[0]) && !profiilPane.getChildren().contains(profiilRunda)) {
                        profiilPane.getChildren().add(profiilRunda);
                    } else if (elanikeList.getSelectionModel().getSelectedItem().equals(mangija[0]) && profiilPane.getChildren().contains(profiilRunda)) {
                        profiilPane.getChildren().remove(profiilRunda);
                    }

                    valitud.clear();
                    valitud.add(elanikeList.getSelectionModel().getSelectedItem());

                    menuu.getSelectionModel().select(profiil);
                }
            });

            Text abiLinn = new Text(5, 15, "Et näha rohkem infot ja rünnata, kliki tegelase peale kaks korda!");

            AnchorPane linnPane = new AnchorPane();
            linnPane.setMinWidth(650);
            linnPane.setMinHeight(400);
            linnPane.getChildren().addAll(abiLinn, elanikeList);
            linn.setContent(linnPane);

            // RÜNNAK

            menuu.getTabs().add(profiil);
            menuu.getTabs().add(linn);

            VBox peaVBox = new VBox(menuu);
            Scene stseen = new Scene(peaVBox);

            peaLava.setScene(stseen);
            peaLava.setResizable(false);
            peaLava.setWidth(650);
            peaLava.setHeight(400);
            peaLava.setTitle("Rollimäng");
            peaLava.show();
        });
    }
}
