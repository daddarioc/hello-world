package view;

import java.sql.Date;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Asset;

public class AssetPickerController {
	//reference to main application
	private MainApp mainApp;
	
	@FXML
	private Button btnNewVersion;
	
	@FXML
	private ListView<Asset> lstAssets = new ListView<Asset>();
	
	@FXML
	private TableView<Asset> tblAssets;

	@FXML
	private TableColumn<Asset, String> colAssetId;

	@FXML
	private TableColumn<Asset, String> colAssetName;

	@FXML
	private TableColumn<Asset, String> colStatus;

	@FXML
	private TableColumn<Asset, Date> colEndDate;
	
	public AssetPickerController() {
		
	}
	
	/**
	 * Perform a new version operation on the asset (end-date, choose a new start date and status for a new row insert of the copied asset
	 */
	@FXML
	private void assetNewVersion() {
		//get the selected rows
		this.mainApp.setAssetsToUpdate(tblAssets.getSelectionModel().getSelectedItems());
		
		//show the update screen
		this.mainApp.showAssetUpdateDialog();
	}
	
	@FXML
	private void initialize() {
		//tie all of the columns to the asset data
		colAssetId.setCellValueFactory(cellData -> cellData.getValue().getAssetIdProperty());
		colAssetName.setCellValueFactory(cellData -> cellData.getValue().getAssetNameProperty());
		colStatus.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
		colEndDate.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
		
		//enable multiple-row selection in the table
		tblAssets.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	public void setMainApp(MainApp mainApp) {
		//set the model to here
		this.mainApp = mainApp;
		
		//populate the table with asset rows
		tblAssets.setItems(mainApp.getAssetData());
	}
}
