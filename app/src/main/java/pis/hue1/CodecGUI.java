package pis.hue1;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

/**
 * Benutzeroberflaeche/GUI
 *
 * Hier wird eine geeignete Benutzeroberflaeche fuer beide Verschluesselungsverfahren implementiert.
 * Hierfuer wird die JavaFX-Klassenbibliothek und die zwei Wuerfel-Objekte und
 * ein Caesar-Objekt ueber die Schnittstelle Codec verwendet.
 *
 * @author Neha K.
 * @date 06.12.2020
 */
public class CodecGUI extends Application implements EventHandler {
    private Button verschluesselnButton;
    private Button entschluesselnButton;
    private TextField losungswort1TextField;
    private TextField losungswort2TextField;
    private TextArea klartextTextArea;
    private ComboBox verfahrenComboBox;
    private Label textOut1;
    private Label textOut2;
    private Label textDecOut1;
    private Label textDecOut2;
    private Label lsw1Label;
    private Label lsw2Label;
    private Label verfahrenLabel;
    Codec codec_1 = new Wuerfel();
    Codec codec_2 = new Wuerfel();
    Codec codec_3 = new Caesar();

    /**
     * Die main-Methode ruft den neuen Einstiegspunkt für eine JavaFX-Anwendung auf.
     * Dazu wird auf die Methode {@link #launch(String...)} verwiesen.
     * @param   args
     */
    public static void main(String[] args){
        Application.launch(args);
    }

    /**
     * Methode, die eine Scene definiert, dessen Hauptelement ein Group darstellt.
     * Das Objekt der Klasse Scene wird dem Objekt der Klasse Stage zugewiesen.
     * Die Größe und der Titel werden ebenso definiert.
     * Mittels stage.show wird das Fenster angezeigt.
     * @param   stage
     */
    @Override
    public void start(Stage stage) {

        stage.setTitle("PIS Hausübung 01, WiSe 20/21");

        verschluesselnButton = new Button("kodieren");
        entschluesselnButton = new Button("dekodieren");
        klartextTextArea = new TextArea("Text eingeben ..");
        klartextTextArea.setPrefColumnCount(20);
        klartextTextArea.setPrefRowCount(7);
        losungswort1TextField = new TextField("Losungswort1");
        losungswort2TextField = new TextField("Losungswort2");
        verfahrenComboBox = new ComboBox();
        verfahrenComboBox.setValue("Verschlüsselungsverfahren");
        verfahrenComboBox.getItems().addAll("Doppelwürfel", "Caesar");
        verfahrenLabel = new Label("Verschlüsselungsmethode auswählen:");
        lsw1Label = new Label("1. Losungswort:");
        lsw2Label = new Label("2. Losungswort:");
        textOut1 = new Label("[Kodierter Text mit 1. Losungswort]");
        textOut2 = new Label("[Kodierter Text mit 2. Losungswort]");
        textDecOut1 = new Label("[Dekodierter Text mit 1. Losungswort]");
        textDecOut2 = new Label("[Dekodierter Text mit 2. Losungswort]");

        verschluesselnButton.setOnAction(this::handle);
        entschluesselnButton.setOnAction(this::handle);
        verfahrenComboBox.setOnAction(this::handle);
        losungswort1TextField.setOnAction(this::handle);
        losungswort2TextField.setOnAction(this::handle);

        GridPane layout = new GridPane();
        layout.setVgap(10);
        layout.setHgap(10);
        layout.setPadding(new Insets(5,5,5,5));

        layout.add(lsw1Label,0,0);
        layout.add(losungswort1TextField, 0, 1);
        layout.add(lsw2Label, 0,2);
        layout.add(losungswort2TextField, 0, 3);
        layout.add(klartextTextArea, 0, 4);
        layout.add(textOut1, 0, 5);
        layout.add(textOut2, 0, 6);
        layout.add(textDecOut1, 1, 5);
        layout.add(textDecOut2, 1, 6);
        layout.add(verfahrenLabel, 1, 2);
        layout.add(verfahrenComboBox, 1, 3);
        layout.add(verschluesselnButton, 0, 7);
        layout.add(entschluesselnButton, 1, 7);

        Group root = new Group();
        root.getChildren().add(layout);
        Scene scene = new Scene(root, 590, 360);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Methode, durch die die Anwendung tatsaechlich zu einer Anwendung dargestellt wird.
     * Hierfuer beachtet man zu aller erst den angeklickten Verfahren im Combobox und entscheidet demnach,
     * ob kodiert oder dekodiert werden soll. Je nachdem, wird die jeweilige Objekte aufgerufen.
     *
     * Ausgegeben werden die jeweiligen Ergebnisse in einem Label.
     * @param   actionEvent
     */
    @Override
    public void handle(Event actionEvent) {
        String s = (String) verfahrenComboBox.getSelectionModel().getSelectedItem();
        switch (s) {
            case "Doppelwürfel":
                if(actionEvent.getSource() == verschluesselnButton) {
                    codec_1.setzeLosung(losungswort1TextField.getText());
                    textOut1.setText(codec_1.kodiere(klartextTextArea.getText()));
                    codec_2.setzeLosung(losungswort2TextField.getText());
                    textOut2.setText(codec_2.kodiere(codec_1.kodiere(klartextTextArea.getText())));
                } else if (actionEvent.getSource() == entschluesselnButton){
                    codec_1.setzeLosung(losungswort1TextField.getText());
                    textDecOut1.setText(codec_1.dekodiere(klartextTextArea.getText()));
                    codec_2.setzeLosung(losungswort2TextField.getText());
                    textDecOut2.setText(codec_2.dekodiere(codec_1.kodiere(klartextTextArea.getText())));
                }
                break;
            case "Caesar":
                if(actionEvent.getSource() == verschluesselnButton) {
                    codec_3.setzeLosung(losungswort1TextField.getText());
                    textOut1.setText(codec_3.kodiere(klartextTextArea.getText()));
                } else if (actionEvent.getSource() == entschluesselnButton){
                    codec_3.setzeLosung(losungswort1TextField.getText());
                    textDecOut1.setText(codec_3.dekodiere(klartextTextArea.getText()));
                }
                break;
        }
    }
}
