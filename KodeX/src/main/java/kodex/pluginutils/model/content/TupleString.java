package kodex.pluginutils.model.content;

import java.io.File;

import javafx.scene.control.Alert.AlertType;
import kodex.InvalidInputException;
import kodex.model.I18N;
import kodex.model.Tuple;
import kodex.plugininterface.Content;

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
  public boolean isValid(String input) throws InvalidInputException {

    if (input == null) {
      throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
          I18N.get("alert.input.invalid"), 
          "Content validation input is empty");
    }
    
    String[] inputStrings = input.split(" ");
    
    tuples = new Tuple[inputStrings.length];

    for (int i = 0; i < inputStrings.length; i++) {

      String[] tupleParts = inputStrings[i].split(":");
      
      if (tupleParts.length != 2) {
        throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
            I18N.get("alert.input.invalid"), 
            "Invalid input, input contains unpaired elements");
      }

      if (!isValidLetter(tupleParts[0])) {
        throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
            I18N.get("alert.input.invalid"), 
            "Invalid input, no letter at position " + 0);
      }

      if (!isValidNumber(tupleParts[1])) {
        throw new InvalidInputException(AlertType.ERROR, I18N.get("alert.title.error"), 
            I18N.get("alert.input.invalid"), 
            "Invalid input, no number at position " + 1);
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
