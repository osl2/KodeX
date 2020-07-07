package kodex.presenter;

import java.io.File;
import java.io.IOException;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import kodex.plugininterface.ChainLinkPresenter;
import kodex.plugininterface.ProcedurePlugin;

/**
 * This Presenter is responsible for the Coding Chain. This Page shows the Coding Chain in its entirety
 * and is responsible for interactions regarding the display of said Coding Chain. These are creating
 * the view for a given Procedure-Plugin, hiding a Chain Link, and jumping to a Chain Link.
 * 
 * @author Yannick Neubert
 * 
 * @version 1.0
 */
public class ChainPresenter implements IPresenter {

    /**
     * The scrollable Pane in which the split pane is nested.
     */
    /**
     * The split pane in which the Chain Links are displayed.
     */
    

    /**
     * Creates a new ChainPresenter.
     */
	
	@FXML
	private ScrollPane viewScrollPane;
	
	@FXML
	private SplitPane chainSplitPane;
	
	private AnchorPane chainRootPane;
	
	private ChainLinkPresenter firstChainLinkPresenter;
	
	private ProcedureLayoutPresenter procedureLayoutPresenter;
	
    /**
     * This method is executed when the users clicks on the button to hide a Chain Link. It minimizes 
     * the Chain Link in the Split-Pane.
     */
    public ChainPresenter(ChainLinkPresenter chainLinkPresenter, ProcedureLayoutPresenter procedureLayoutPresenter) {
    	this.firstChainLinkPresenter = chainLinkPresenter;
		this.procedureLayoutPresenter = procedureLayoutPresenter;
		
		// loads the template file
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chainlayout.fxml"));
		fxmlLoader.setController(this);
		try {
			chainRootPane = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
    private class ChainItem extends AnchorPane {
		
		@FXML
		private Label titleLabel;
		
		@FXML
		private BorderPane chainLinkPane;
		
		@FXML
		private VBox informationBox;
		
		@FXML
		private FontIcon hideButtonIcon;
		
		private Boolean isHidden;
		
		private String hiddenIcon = "mdi-chevron-down";
		
		private String shownIcon = "mdi-chevron-right";
		
		private ChainLinkPresenter chainLinkPresenter;
		
		ChainItem(ChainLinkPresenter chainLinkPresenter) {
			this.chainLinkPresenter = chainLinkPresenter;
			
			isHidden = false; 
			
			try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("chianlinktemplate.fxml"));
	            loader.setController(this);
	            loader.setRoot(this);
	            loader.load();
	        } catch (IOException exc) {
	        	System.err.println("The file chianlinktemplate.fxml was not found!");
	        }
			
		}
		
		@FXML
		private void initialize() {
			// titleLabel.setText("Kodierungsstufe: " + chainLinkPresenter.getName());
			informationBox.getChildren().set(0, chainLinkPresenter.getChainLinkHeaderView());
		}
		
		@FXML
		private void handleEdit() {
			procedureLayoutPresenter.setEditPresenter(chainLinkPresenter.getChainLinkEditPresenter());
		}
		
		@FXML
		private void handleExport() {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Choose export location");
			File exportLocation = chooser.showSaveDialog(null);
			
			chainLinkPresenter.export(exportLocation);
		}
		
		@FXML
		private void handleHideChainLink() {
			toggleHide();
		}
		
		private void toggleHide() {
			if (isHidden) {
				hideButtonIcon.setIconLiteral(shownIcon);
				isHidden = false;
			} else {
				hideButtonIcon.setIconLiteral(hiddenIcon);
				isHidden = true;
			}
		}
	}
    
    /**
     * This method creates the view for a given Procedure-Plugin.
     * @param activeProcedure the active Procedure-Plugin.
     */
    public void createChainView(ProcedurePlugin activeProcedure) {		
    	ChainLinkPresenter chainLinkPresenter = firstChainLinkPresenter;
	
    	// add a newly created chain item for each ChainLinkPresenter
		int i = 0;
		while (chainLinkPresenter != null) {
			chainSplitPane.getItems().set(i, new ChainItem(chainLinkPresenter));
			chainLinkPresenter = chainLinkPresenter.getNext();
			i++;
		}	
    }

    /**
     * This method jumps to the Chain Link with the given ID.
     * @param id : The ID of the Chain Link.
     */
    public void jumpToChainLink(int id) {
    	double hValue;
    	double position;
    	
    	// TODO: calculate jump position
    	
    	// This sets the horizontal scroll position.
    	// viewScrollPane.setHvalue(hValue);
    	
    	// This sets the position of the vertical dividers between the Chain Links.
    	// chainSplitPane.setDividerPosition(id, position);
    }

    @Override
    public AnchorPane getView() {
        return chainRootPane;
    }

}