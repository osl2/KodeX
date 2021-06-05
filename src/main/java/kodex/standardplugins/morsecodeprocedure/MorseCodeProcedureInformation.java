package kodex.standardplugins.morsecodeprocedure;

import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import kodex.plugininterface.ProcedureInformation;

public class MorseCodeProcedureInformation extends ProcedureInformation{

	 /**
	   * Creates a new instance of the ColorImageProcedureInformation class and sets the fields
	   * accordingly.
	   */
	  public MorseCodeProcedureInformation() {
	    this.icon = new Image(getClass().getResourceAsStream("img/mario_color.png"));
	    this.description =
	        "Umwandlung einer Zeichenkette in Morse-Code und umgekehrt.";
	    this.labels =
	        FXCollections.observableArrayList(
	            "7", "Kodierungsverfahren", "Kodieren und Dekodieren");
	  }
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Morse-Code";
	}

}
