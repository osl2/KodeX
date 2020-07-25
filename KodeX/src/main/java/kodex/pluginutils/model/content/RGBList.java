package kodex.pluginutils.model.content;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javafx.scene.paint.Color;

/**
 * This class holds data in LinkedList format. An RGBList consists of colors.
 * Extending AbstractList, it adds validation and exporting capabilities to Java's List.
 */
public class RGBList extends AbstractList<Color> {

    /**
     * Creates a new RGBList
     */
    public RGBList() {
    	super.list = new LinkedList<Color>();
    }

    @Override
    public boolean isValid(Object input) {
    	@SuppressWarnings("unused")
    	//the "unused warning" is suppressed since it has no negative effects.
    	//it's caused by not using the variable object in any logic.
    	//this is due to the fact that as long as the class cast works there remains nothing to be validated.
		RGBList object;
    	
    	if (input == null) {
			System.out.println("Invalid import, no import to validate");
			return false;
		}
    	
    	try {
			object = ((RGBList) input);
		} catch (ClassCastException e) {
			System.out.println("Invalid import, import is of wrong type");
			return false;
		}
    	
    	return true;
    }

	@Override
	public void export(File file) {
		try {
			
			if (!file.getAbsolutePath().endsWith(".txt"))
				file = new File(file.getAbsoluteFile() + ".txt");
			
			FileWriter writer = new FileWriter(file);

			//header
			writer.write("HEADER\n");
			@SuppressWarnings("unchecked")
			//the "unchecked warning" is suppressed since it has no negative effects
			//it's caused by the lambda expression used in writing the header in combination with
			//this class extending a generic superclass. There appears to be no way of avoiding this.
			HashMap<String, Object> map = (HashMap<String, Object>) header;
			map.forEach( (key, value) -> { try {
				writer.write(key + " " + value + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} });
			
			//content
			writer.write("CONTENT\n");
			for (int y = 0; y < size(); y++) {
				
				String row = get(y).toString().substring(0, 8);

				if (y != size() - 1) 
					writer.write(row + "\n");
				else
					writer.write(row);
			}
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}