package kodex.pluginutils.model.content;

import java.io.File;

/**
 * This class holds data in string format. A CharacterString consists of only alphabetical
 * characters. Extending AbstractString, it adds validation and exporting capabilities to Javas
 * String.
 */
public class CharacterString extends AbstractString {

  public CharacterString() {
    super.data = "";
  }

  /** Creates a new CharacterString. */
  public CharacterString(String str) {
    super.data = str;
  }

  @Override
  public boolean isValid(String input) {
    if (input == null) {
      return false;
    }
    return true;
  }

  @Override
  protected File toFile() {
    // TODO Auto-generated method stub
    return null;
  }
}
