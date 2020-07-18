package kodex.pluginutils.model.content;

import java.io.File;

import kodex.plugininterface.Content;

/**
 * This class holds data in string format. A BinaryString consists of only 1's and 0's.
 * Extending AbstractString, it adds validation and exporting capabilities to Javas String.
 */
public class BinaryString extends AbstractString {

    /**
     * Creates a new BinaryString
     */
    public BinaryString() {
    	super.data = "";
    }

	@Override
	protected File toFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isValid(Object input) {
		// TODO Auto-generated method stub
		return null;
	}

}