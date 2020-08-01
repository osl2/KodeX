package kodex.pluginutils.presenter.edit;

import java.util.function.UnaryOperator;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import kodex.plugininterface.ChainLinkEditPresenter;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.pluginutils.model.content.RGBByteList;
import kodex.pluginutils.model.content.RGBList;

/**
 * This class manages the edit view and is responsible for editing a RGB byte list.
 * 
 *  @author Raimon Gramlich
 */
public class RGBByteListEditPresenter extends ChainLinkEditPresenter {
  
  private RGBByteList content;
  
  private TextField redField;
  private TextField greenField;
  private TextField blueField;
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> redFormatter;
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> greenFormatter;
  
  /** The text formatter which only allows binary input. */
  private TextFormatter<String> blueFormatter;
  
  /** The length of one unit used to determine how many list elements belong together. */
  private int unitLength;
  
  private AnchorPane view;

  /**
   * Instantiates a new RGB byte list edit presenter.
   *
   * @param chainLinkPresenter the corresponding chain link presenter
   */
  public RGBByteListEditPresenter(ChainLinkPresenter chainLinkPresenter) {
    super(chainLinkPresenter);
    
    // only allows 0 or 1 as input
    UnaryOperator<TextFormatter.Change> filter = change -> {
      if (change.getControlNewText().matches("[0-1]+")) {
        return change;
      } else {
        return null;
      }
    };
    
    redFormatter = new TextFormatter<>(filter);
    greenFormatter = new TextFormatter<>(filter);
    blueFormatter = new TextFormatter<>(filter);
    
    redField = new TextField();
    redField.setTextFormatter(redFormatter);
    
    greenField = new TextField();
    greenField.setTextFormatter(greenFormatter);
    
    blueField = new TextField();
    blueField.setTextFormatter(blueFormatter);
    
    Label redLabel = new Label("R: ");
    Label greenLabel = new Label("G: ");
    Label blueLabel = new Label("B: ");
    
    HBox redBox = new HBox(redLabel, redField);
    HBox greenBox = new HBox(greenLabel, greenField);
    HBox blueBox = new HBox(blueLabel, blueField);
   
    view = new AnchorPane((new VBox(redBox, greenBox, blueBox)));
  }

  @Override
  public AnchorPane getView() {
    unitLength = (int) content.getHeader().get("unit-length");
    
    updateMarkedElement();
    
    return view;
  }

  @Override
  public void handleSubmit() {
    content.getList().set(markID * unitLength, redField.getText());
    content.getList().set(markID * unitLength + 1, greenField.getText());
    content.getList().set(markID * unitLength + 2, blueField.getText());
    
    chainLinkPresenter.updateChain();
  }

  @Override
  protected void updateMarkedElement() {
    
    content = (RGBByteList) chainLinkPresenter.getContent();
    
    redField.setText(String.valueOf(content.get(markID * unitLength)));
    greenField.setText(String.valueOf(content.get(markID * unitLength + 1)));
    blueField.setText(String.valueOf(content.get(markID * unitLength + 2)));
  }
}
