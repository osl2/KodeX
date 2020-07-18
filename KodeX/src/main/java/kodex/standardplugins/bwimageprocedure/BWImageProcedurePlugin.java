package kodex.standardplugins.bwimageprocedure;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.content.AbstractImage;
import kodex.pluginutils.model.content.AbstractMatrix;
import kodex.pluginutils.model.content.AbstractString;
import kodex.pluginutils.model.content.BinaryString;
import kodex.pluginutils.model.content.BlackWhiteImage;
import kodex.pluginutils.model.content.CharacterString;
import kodex.pluginutils.model.content.RGBMatrix;
import kodex.pluginutils.model.steps.ColorImageToRGBMatrix;
import kodex.pluginutils.model.steps.TextToBinaryString;
import kodex.standardplugins.bwimageprocedure.presenter.BWImageImportPresenter;

/**
 * This class is responsible for the administration of the specific
 * procedure "black and white image to binary sequence". 
 * This class holds a list of ChainLinks as attributes, i.e. the
 * different steps of this coding chain.
 * 
 * @author Patrick Spiesberger
 * 
 * @version 1.0
 *
 */
public class BWImageProcedurePlugin extends ProcedurePlugin {

    /* steps of this coding chain */
    private ChainLinkPresenter[] chainLinks; // [2..*]

    /**
     * Constructor of class BWImageProecedure. Sets all chainLinks
     */
    public  BWImageProcedurePlugin() {
    	chainLinks = new ChainLinkPresenter[5];
    }

    @Override
	public StringProperty pluginNameProperty() {
		return new SimpleStringProperty("Schwarz & Weiß Bild");
	}

	@Override
	public StringProperty pluginDescriptionProperty() {
		return new SimpleStringProperty("Verfahren");
	}

	@Override
	public ChainLinkPresenter getChainHead() {
		return chainLinks[0];
	}

	@Override
	public void initEncodeProcedure(Content content) {
		AbstractImage img = new BlackWhiteImage(); //TODO: Content integrieren
		AbstractMatrix mtx = new RGBMatrix(img.getHeight(), img.getHeight());
		new ColorImageToRGBMatrix().encode(img, mtx);
	}

	@Override
	public void initDecodeProcedure(Content content) {
		AbstractString charStr = new CharacterString(content.toString());
		AbstractString binStr = new BinaryString();
		new TextToBinaryString().decode(charStr, binStr);
	}

	@Override
	public ProcedureInformation createProcedureInformation() {
		return new BWImageProcedureInformation();
	}

	@Override
	public ImportPresenter createImportPresenter() {
		return new BWImageImportPresenter(this);
	}
}