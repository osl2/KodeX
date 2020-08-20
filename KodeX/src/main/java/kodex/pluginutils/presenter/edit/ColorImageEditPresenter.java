package kodex.pluginutils.presenter.edit;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import kodex.exceptions.InvalidInputException;
import kodex.model.I18N;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.ColorImage;

/**
 * This class manages the edit view and is responsible for editing a color image.
 * 
 *  @author Raimon Gramlich
 */
public class ColorImageEditPresenter extends ChainLinkEditPresenter {
  
  /** The content which this class edits. */
  private ColorImage content;
  
  /** The x coordinate of the selected pixel. */
  private int selectedX;
  
  /** The y coordinate of the selected pixel. */
  private int selectedY;
  
  /** The color picker used for user input. */
  private ColorPicker colorPicker;
  
  /** The edit view. */
  private AnchorPane view;
  
  /**
   * Instantiates a new color image edit presenter.
   *
   * @param chainLinkPresenter the chain link presenter
   */
  public ColorImageEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);
    
    view = new AnchorPane(new ColorPicker());
  }

  @Override
  public AnchorPane getView() {
    updateMarkedElement();
    
    return view;
  }

  @Override
  public void handleSubmit() throws InvalidInputException {
    Color newColor = colorPicker.getValue();
    boolean showWarning = false;
    
    // ignores opacity and uses only RGB values
    if (newColor.getOpacity() != 1) {
      newColor = new Color(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), 1d);
      showWarning = true;
    }
    
    content.setColor(selectedX, selectedY, newColor);
    chainLinkPresenter.updateChain();
    
    if (showWarning) {
      throw new InvalidInputException(AlertType.WARNING, I18N.get("alert.title.warning"),
          I18N.get("alert.input.invalid"), "Opacity changes are not supported and won't be saved.");
    }
  }
  
  protected void updateMarkedElement() {
    content = (ColorImage) chainLinkPresenter.getContent();
    WritableImage img = content.getImage();
    selectedX = markID % (int) Math.round(img.getWidth());
    selectedY = (markID / (int) Math.round(img.getWidth()));

    Color markedPixel = content.getColor(selectedX, selectedY);

    colorPicker = new ColorPicker(markedPixel);
    
    view.getChildren().set(0, colorPicker);  
  }
}
