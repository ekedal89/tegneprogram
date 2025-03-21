package org.example.tegneprogram;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.LinkedList;

public class HelloApplication extends Application {

    ComboBox<String> figurMeny = new ComboBox<>();
    ComboBox<String> linjeFarge = new ComboBox<>();
    ComboBox<String> fyllFarge = new ComboBox<>();
    Button lagFigur;
    Button oppdaterFigur;
    Button fjernFigur;
    Button flyttTilForrest;
    Button flyttTilBakerst;
    Button flyttEttFrem;
    Button flyttEttTilbake;
    Button slettLinje;
    TextField tekstInnhold = new TextField();
    TextField tekstStørrelse = new TextField();
    Pane tegnePane = new Pane();
    LinkedList<Figur> figurer = new LinkedList<>();
    TextArea infoTextArea = new TextArea();
    Figur valgtFigur;
    Figur nyFigur;

    @Override
    public void start(Stage stage) {
        BorderPane hovedpane = new BorderPane();
        Scene scene = new Scene(hovedpane, 1200, 800);
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        figurMeny.getItems().addAll("Rektangel", "Sirkel", "Linje", "Tekst");
        figurMeny.setValue("Velg figur");

        linjeFarge.getItems().addAll("Rød", "Blå", "Grønn", "Svart", "Rosa", "Oransje");
        fyllFarge.getItems().addAll("Ingen fyllfarge", "Rød", "Blå", "Grønn", "Svart", "Rosa", "Oransje");


        linjeFarge.setPromptText("Velg linjefarge");
        fyllFarge.setPromptText("Velg fyllfarge");
        linjeFarge.setValue("Svart");

        tekstInnhold.setPromptText("Skriv tekst her");
        tekstStørrelse.setPromptText("Størrelse på tekst");

        lagFigur = new Button("Lag figur");
        lagFigur.setOnAction(e -> lagValgtFigur());

        oppdaterFigur = new Button("Oppdater figur");
        oppdaterFigur.setOnAction(e -> oppdaterValgtFigur());

        fjernFigur = new Button("Fjern figur");
        fjernFigur.setOnAction(e -> fjernValgtFigur());

        flyttTilForrest = new Button("Flytt til forrest");
        flyttTilForrest.setOnAction(e -> flyttForrest());

        flyttTilBakerst = new Button("Flytt til bakerst");
        flyttTilBakerst.setOnAction(e -> flyttBakerst());

        flyttEttFrem = new Button("Flytt ett trinn frem");
        flyttEttFrem.setOnAction(e -> flyttEttFrem());

        flyttEttTilbake = new Button("Flytt ett trinn tilbake");
        flyttEttTilbake.setOnAction(e -> flyttEttTilbake());

        slettLinje = new Button("Slett linje");
        slettLinje.setOnAction(e -> fjernSisteLinje());

        infoTextArea.setPrefWidth(250);
        infoTextArea.setEditable(false);

        infoTextArea.setText("For å endre størrelse på rektangel:"
                + "\nDra i nederste høyre hjørne\n\nFor å endre på sirkel:"
                + "\nDra i kanten på høyre side.\n");

        hovedpane.setCenter(tegnePane);
        hovedpane.setLeft(vbox);
        hovedpane.setRight(infoTextArea);

        vbox.getChildren().addAll(figurMeny,
                linjeFarge,
                fyllFarge,
                tekstInnhold,
                tekstStørrelse,
                lagFigur,
                oppdaterFigur,
                fjernFigur,
                flyttTilForrest,
                flyttTilBakerst,
                flyttEttFrem,
                flyttEttTilbake,
                slettLinje);

        stage.setTitle("Tegneprogram");
        stage.setScene(scene);
        stage.show();

        // Når programmet avsluttes skal det printes ut i terminalvinduet hvor mange objekter som ble opprettet og info om dem
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Totalt opprettede objekter:");
            for (Figur figur : figurer) {
                System.out.println("\n"+figur.getInfo());
            }
        }));

        // Dersom linje er valgt figur, blir disse metodene kalt på når musen presses ned, dras bortover og når musen slipper
        tegnePane.setOnMousePressed(this::startDrawLine);
        tegnePane.setOnMouseDragged(this::drawLine);
        tegnePane.setOnMouseReleased(this::endDrawLine);
    }

    /**
     * Metode for å starte linjen
     * @param event hva slags museevent som blir utført
     */
    private void startDrawLine(MouseEvent event) {
        if ("Linje".equals(figurMeny.getValue())) {
            String linjefarge = linjeFarge.getValue();
            Color strokeColor = fargeTilColor(linjefarge);

            nyFigur = new Linje(event.getX(), event.getY(), event.getX(), event.getY(), strokeColor);
            nyFigur.tegn(tegnePane);
            valgtFigur = nyFigur;
        }
    }

    /**
     * Metode for å tegne selve linjen
     * @param event hva slags museevent som blir utført
     */
    private void drawLine(MouseEvent event) {
        if ("Linje".equals(figurMeny.getValue()) && nyFigur instanceof Linje) {
            ((Linje) nyFigur).setEndPoint(event.getX(), event.getY());
        }
    }

    /**
     * Metode for å få sluttpunktet til linjen
     * @param event hva slags museevent som blir utført
     */
    private void endDrawLine(MouseEvent event) {
        if ("Linje".equals(figurMeny.getValue()) && nyFigur != null) {
            figurer.add(nyFigur);
            nyFigur = null;
        }
    }

    /**
     * Metode for å opprette valgt figur med ulike egenskaper som brukeren velger, og legger figur objektet inn i liste
     */
    private void lagValgtFigur() {
        String figurType = figurMeny.getValue();
        String linjefarge = linjeFarge.getValue();
        Color strokeColor = fargeTilColor(linjefarge);

        if ("Rektangel".equals(figurType)) {
            String fyllfarge = fyllFarge.getValue();
            Color fillColor = fargeTilColor(fyllfarge);

            nyFigur = new Rektangel(50, 50, 100, 75, strokeColor, fillColor);
        } else if ("Sirkel".equals(figurType)) {
            String fyllfarge = fyllFarge.getValue();
            Color fillColor = fargeTilColor(fyllfarge);

            nyFigur = new Sirkel(100, 100, 50, strokeColor, fillColor);
        } else if ("Tekst".equals(figurType)) {
            String content = tekstInnhold.getText();
            int size = Integer.parseInt(tekstStørrelse.getText());

            nyFigur = new Tekst(100, 100, content, size, strokeColor);
        }

        if (nyFigur != null) {
            nyFigur.tegn(tegnePane);
            figurer.add(nyFigur);
            settValgbarHendelse(nyFigur);
            nyFigur = null;
        }
    }

    /**
     * Metode for å "velge" figuren man trykker på med musen, for å få riktig info om riktig figur
     * @param figur er figur objektet man trykker på
     */
    private void settValgbarHendelse(Figur figur) {
        figur.shape.setOnMouseClicked(e -> {
            valgtFigur = figur;
            infoTextArea.setText(figur.getInfo());
            e.consume();
        });
    }

    /**
     * Metode for å oppdatere fargevalg av en allerede opprettet figur, og oppdatere infoen
     */
    private void oppdaterValgtFigur() {
        if (valgtFigur == null) return;

        String linjefarge = linjeFarge.getValue();
        Color strokeColor = fargeTilColor(linjefarge);
        valgtFigur.setStrokeColor(strokeColor);

        if (valgtFigur instanceof Rektangel || valgtFigur instanceof Sirkel) {
            String fyllfarge = fyllFarge.getValue();
            Color fillColor = fargeTilColor(fyllfarge);
            valgtFigur.setFillColor(fillColor);
        }

        if (valgtFigur instanceof Tekst) {
            String content = tekstInnhold.getText();
            int size = Integer.parseInt(tekstStørrelse.getText());

            ((Tekst) valgtFigur).setContent(content);
            ((Tekst) valgtFigur).setSize(size);
        }

        infoTextArea.setText(valgtFigur.getInfo());
    }

    /**
     * Slette valgt figur fra både panel og objekt listen
     */
    private void fjernValgtFigur() {
        if (valgtFigur != null) {
            tegnePane.getChildren().remove(valgtFigur.shape);
            figurer.remove(valgtFigur);
            valgtFigur = null;
            infoTextArea.clear();
        }
    }

    /**
     * Flytte objektet helt foran i panelet og i listen med objekter
     */
    private void flyttForrest() {
        if (valgtFigur != null) {
            valgtFigur.bringToFront();
            figurer.remove(valgtFigur);
            figurer.addFirst(valgtFigur);
        }
    }

    /**
     * Flytte objektet helt bakerst i panelet og i listen med objekter
     */
    private void flyttBakerst() {
        if (valgtFigur != null) {
            tegnePane.getChildren().remove(valgtFigur.shape);
            tegnePane.getChildren().add(0, valgtFigur.shape);
            figurer.remove(valgtFigur);
            figurer.addLast(valgtFigur);
        }
    }

    /**
     * Flytte objektet ett hakk frem i panelet og i listen med objekter
     */
    private void flyttEttFrem() {
        if (valgtFigur != null) {
            int currentIndex = tegnePane.getChildren().indexOf(valgtFigur.shape);
            if (currentIndex < tegnePane.getChildren().size() - 1) {
                tegnePane.getChildren().remove(valgtFigur.shape);
                tegnePane.getChildren().add(currentIndex + 1, valgtFigur.shape);

                int listIndex = figurer.indexOf(valgtFigur);
                if (listIndex < figurer.size() - 1) {
                    figurer.remove(listIndex);
                    figurer.add(listIndex + 1, valgtFigur);
                }
            }
        }
    }

    /**
     * Flytte objektet ett hakk tilbake i panelet og i listen med objekter
      */
    private void flyttEttTilbake() {
        if (valgtFigur != null) {
            int currentIndex = tegnePane.getChildren().indexOf(valgtFigur.shape);
            if (currentIndex > 0) {
                tegnePane.getChildren().remove(valgtFigur.shape);
                tegnePane.getChildren().add(currentIndex - 1, valgtFigur.shape);

                int listIndex = figurer.indexOf(valgtFigur);
                if (listIndex > 0) {
                    figurer.remove(listIndex);
                    figurer.add(listIndex - 1, valgtFigur); 
                }
            }
        }
    }

    /**
     * Metode for å fjerne den nyeste linjen som ble opprettet
     */
    private void fjernSisteLinje() {
        for (int i = figurer.size() - 1; i >= 0; i--) {
            if (figurer.get(i) instanceof Linje) {
                Figur linje = figurer.get(i);
                tegnePane.getChildren().remove(linje.shape);
                figurer.remove(linje);
                break;
            }
        }
    }

    /**
     * Metode for å gjøre om valgt farge til en faktisk farge som kan brukes i objektet figur
     * @param farge tekst fra nedtrekksmenyen
     * @return hvilken farge som er valgt
     */
    private Color fargeTilColor(String farge) {
        switch (farge) {
            case "Rød":
                return Color.RED;
            case "Blå":
                return Color.BLUE;
            case "Grønn":
                return Color.GREEN;
            case "Svart":
                return Color.BLACK;
            case "Rosa":
                return Color.PINK;
            case "Oransje":
                return Color.ORANGE;
            case "Ingen fyllfarge":
                return Color.TRANSPARENT;
            default:
                return Color.TRANSPARENT;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}