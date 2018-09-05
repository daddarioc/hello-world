package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dataModel.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

public class FXMLAssetPickerController implements Initializable {
	private Model model;
	
	@FXML
	private Pane assetPicker;
	
	@FXML
	private Button btnNext;
		
	/*@FXML
	private ListView<String> areaList;
	
	@FXML
	private ListView<String> productList;
	*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
				
		}
		catch (Exception e) {
			System.out.println("something went boom trying to get areas");
		}
		
		
	}

	/**
	 * Connect to DB and get a list of the selected area's related products in order to show their assets
	 * @param event
	 */
	/*@FXML
	private void showProducts(ActionEvent event) {
		
	}*/
	
    /**
     * Event handler fired when the user requests a previous vista.
     *
     * @param event the event that triggered the handler.
     */
    @FXML
    void nextPane(ActionEvent event) {
        Navigator.loadWindow(Navigator.ASSET_EDIT);
    }
    
    @FXML
    private void advanceToEdit(ActionEvent event) {
    	Navigator.loadWindow(Navigator.ASSET_EDIT);
    }
    
	public void initModel(Model model) {
		if (this.model != null) {
			throw new IllegalStateException("Model can only be intialized once");
		}
		this.model = model;
	}
}
