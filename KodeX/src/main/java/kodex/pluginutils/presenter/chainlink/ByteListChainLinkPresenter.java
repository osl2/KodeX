package kodex.pluginutils.presenter.chainlink;

import java.util.LinkedList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ChainStep;
import kodex.pluginutils.model.content.ByteList;
import kodex.pluginutils.presenter.edit.RGBByteListEditPresenter;

/**
 * The Class RGBByteListChainLinkPresenter.
 *
 * @author Raimon Gramlich
 */
public class ByteListChainLinkPresenter extends ChainLinkPresenter {

  /** The chain link name. */
  private static final String CHAIN_LINK_NAME = "Byte-Liste";

  /** The rgb byte list view. */
  private ListView<String> byteListView;

  /**
   * Instantiates a new RGB byte list chain link presenter.
   *
   * @param previous     the previous ChainLinkPresenter
   * @param previousStep the previous step
   * @param nextStep     the next step
   */
  public ByteListChainLinkPresenter(ChainLinkPresenter previous, ChainStep previousStep, ChainStep nextStep) {
    super(previous, previousStep, nextStep);
    chainLinkEditPresenter = new RGBByteListEditPresenter(this);
    // chainLinkHeaderPresenter = new RGBByteListHeaderPresenter(this.getContent());
    content = new ByteList();
  }

  @Override
  protected int calculateID() {
    return byteListView.getSelectionModel().getSelectedIndex();
  }

  @Override
  public AnchorPane getView() {
    byteListView = new ListView<>();

    List<Byte> list = ((ByteList) getContent()).getList();

    List<String> byteStringList = new LinkedList<>();

    for (int j = 0; j < list.size(); j++) {
      byteStringList.add(String.valueOf(list.get(j)));
    }

    byteListView.setItems(FXCollections.observableArrayList(byteStringList));

    // adds listener to list view items
    byteListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        handleMark();
      }
    });

    AnchorPane chainLinkPane = new AnchorPane();

    // add list view to chain link view
    chainLinkPane.getChildren().add(byteListView);

    return chainLinkPane;
  }

  @Override
  public String getName() {
    return CHAIN_LINK_NAME;
  }

  @Override
  protected void mark(int id) {
    byteListView.getSelectionModel().select(id);
    chainLinkEditPresenter.setMarkID(id);
  }
}
