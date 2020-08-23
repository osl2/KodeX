package kodex.standardplugins.greyscaleimageprocedure;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.Content;
import kodex.plugininterface.ImportPresenter;
import kodex.plugininterface.ProcedureInformation;
import kodex.plugininterface.ProcedurePlugin;
import kodex.pluginutils.model.steps.ByteListToBinaryString;
import kodex.pluginutils.model.steps.ByteMatrixToByteList;
import kodex.pluginutils.model.steps.GreyScaleImageToByteMatrix;
import kodex.pluginutils.presenter.chainlink.BinaryStringPresenter;
import kodex.pluginutils.presenter.chainlink.ByteListChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.ByteMatrixChainLinkPresenter;
import kodex.pluginutils.presenter.chainlink.GreyScaleImageChainLinkPresenter;
import kodex.presenter.PresenterManager;
import kodex.standardplugins.greyscaleimageprocedure.presenter.GreyScaleImageImportPresenter;

/**
 * This class is responsible for the administration of the specific procedure
 * "greyscale image to binary sequence". This class holds a list of ChainLinks
 * as attributes, i.e. the different steps of this coding chain.
 *
 * @author Patrick Spiesberger
 * @version 1.0
 */
public class GreyScaleImageProcedurePlugin extends ProcedurePlugin {

  /* steps of this coding chain */
  private ChainLinkPresenter[] chainLinks; // [2..*]

  /** Constructor of class GreyScaleImageProecedurePlugin. Sets all chainLinks */
  public GreyScaleImageProcedurePlugin() {

  }
  
  /**
   * Initialize the procedure plugin.
   */
  private void initialize() {
    chainLinks = new ChainLinkPresenter[4];

    GreyScaleImageToByteMatrix greyScaleImageToByteMatrix = new GreyScaleImageToByteMatrix();
    ByteMatrixToByteList byteMatrixToByteList = new ByteMatrixToByteList();
    ByteListToBinaryString byteListToBinaryString = new ByteListToBinaryString();

    chainLinks[0] = new GreyScaleImageChainLinkPresenter(null, null, greyScaleImageToByteMatrix);
    chainLinks[1] = new ByteMatrixChainLinkPresenter(chainLinks[0], 
        greyScaleImageToByteMatrix, byteMatrixToByteList);
    chainLinks[2] = new ByteListChainLinkPresenter(chainLinks[1], 
        byteMatrixToByteList, byteListToBinaryString);
    chainLinks[3] = new BinaryStringPresenter(chainLinks[2], byteListToBinaryString, null);

    // set next for chain links
    for (int i = 0; i < chainLinks.length - 1; i++) {
      chainLinks[i].setNext(chainLinks[i + 1]);
    }
  }
  
  @Override
  public ImportPresenter createImportPresenter(PresenterManager pm) {
    initialize();
    return new GreyScaleImageImportPresenter(this, pm);
  }

  @Override
  public ProcedureInformation createProcedureInformation() {
    return new GreyScaleProcedureInformation();
  }

  @Override
  public ChainLinkPresenter getChainHead() {
    return chainLinks[0];
  }

  @Override
  public ChainLinkPresenter getChainTail() {
    return chainLinks[chainLinks.length - 1];
  }

  @Override
  public void initDecodeProcedure(Content<?> content) {
    chainLinks[chainLinks.length - 1].updateChain();
  }

  @Override
  public void initEncodeProcedure(Content<?> content) {
    chainLinks[0].updateChain();
  }

  @Override
  public StringProperty pluginDescriptionProperty() {
    return new SimpleStringProperty("Kodierungsverfahren");
  }

  @Override
  public StringProperty pluginNameProperty() {
    return new SimpleStringProperty("Graustufen Bild");
  }
}
