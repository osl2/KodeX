package kodex.pluginutils.model.content;

import java.io.File;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import kodex.model.I18N;
import kodex.model.Tuple;
import kodex.plugininterface.Content;
import kodex.presenter.PresenterManager;

public class TupleString extends Content<String> {

  private Tuple<String, Integer>[] tuples;

  /**
   * Get the contained tuples.
   *
   * @return the tuples
   */
  public Tuple<String, Integer>[] getTuples() {
    return tuples;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean isValid(String input) {

    String[] inputStrings = input.split(" ");
    
    tuples = new Tuple[inputStrings.length];

    for (int i = 0; i < inputStrings.length; i++) {

      String[] tupleParts = inputStrings[i].split(":");
      
      Alert alert = new Alert(AlertType.ERROR);
      alert.titleProperty().bind(I18N.createStringBinding("alert.error.title"));
      alert.headerTextProperty().bind(I18N.createStringBinding("alert.input.invalid"));

      if (tupleParts.length != 2) {
        alert.setContentText("Import is not a tupel");
        PresenterManager.showAlertDialog(alert);
        return false;
      }

      if (!isValidLetter(tupleParts[0])) {
        alert.setContentText("invalid letter at position 0");
        PresenterManager.showAlertDialog(alert);
        return false;
      }

      if (!isValidNumber(tupleParts[1])) {
        alert.setContentText("invalid letter at position 1");
        PresenterManager.showAlertDialog(alert);
        return false;
      }

      tuples[i] = new Tuple<>(tupleParts[0], Integer.parseInt(tupleParts[1]));
    }

    return true;
  }

  /**
   * Checks if the given string is a valid letter.
   *
   * @param input The input string.
   * @return True if the string is a valid letter.
   */
  private boolean isValidLetter(String input) {

    char[] charArray = input.toCharArray();

    if (charArray.length != 1) {
      return false;
    }

    char firstElement = charArray[0];

    return Character.isLetter(firstElement);
  }

  /**
   * Checks if the given string is a valid integer.
   *
   * @param input The input string.
   * @return True if the string is a valid integer.
   */
  private boolean isValidNumber(String input) {
    return input.matches("^[1-9]\\d*$");
  }

  /**
   * Set the contained tuples array.
   *
   * @param tuples the tuples to set
   */
  public void setTuples(Tuple<String, Integer>[] tuples) {
    this.tuples = tuples;
  }

  @Override
  public void export(File file) {
    // TODO Auto-generated method stub
  }
  
}
