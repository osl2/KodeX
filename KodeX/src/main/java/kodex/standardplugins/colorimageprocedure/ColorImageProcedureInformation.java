package kodex.standardplugins.colorimageprocedure;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import kodex.plugininterface.ProcedureInformation;

/**
 * This class holds the information of the corresponding procedure plugin.
 * 
 * @author Raimon Gramlich
 * 
 * @version 1.0
 */
public class ColorImageProcedureInformation extends ProcedureInformation {
    /**
     * Creates a new instance of the ColorImageProcedureInformation class and sets the fields accordingly.
     */
    public ColorImageProcedureInformation() {
        this.procedureName = new SimpleStringProperty("Farbbildverfahren");
        this.icon = new Image(getClass().getResourceAsStream("img/mario_color.png"));
        this.shortDescription = new SimpleStringProperty("Das Kodierungsverfahren Bild-zu-Bitfolge.");
        this.description = new SimpleStringProperty("Es wird zunächst das Bild in eine Matrix umgewandelt." +
            	" Daraufhin wandelt man diese in eine Liste um. Am Ende erhält man eine Binärfolge.");
        this.tags = new SimpleListProperty<>(FXCollections.observableArrayList("Klasse 7", "Kodierungsverfahren", "Kodieren und Dekodieren"));
    }
}