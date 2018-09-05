package view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Iterator;

import application.Alerts;
import application.MainApp;
import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Asset;

public class AssetUpdateController {
	//reference to main application
	private MainApp mainApp;
	
	@FXML 
	private Button btnFinish;
	
	@FXML
	private Button btnCancel;
	
	@FXML
	private Label lblAssetCount;
	
	@FXML
	private DatePicker calNewStartDate;
	
	@FXML
	private DatePicker calEndDate;
	
	@FXML
	private ComboBox<String> cmbNewStatus;
	
	private Stage dialogStage;
	
	public void setMainApp(MainApp mainApp) {
		//set the model to here
		this.mainApp = mainApp;
		String size = String.valueOf(this.mainApp.getAssetsToUpdate().size());
		lblAssetCount.setText(size);
	}
	
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void closeDialog() {
        dialogStage.close();
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
	@FXML
	private void initialize() {
		ZoneId zoneId = ZoneId.of("America/Montreal");
		LocalDate today = LocalDate.now(zoneId);
		calEndDate.setValue(today);
		
		LocalDate tomorrow = LocalDate.now(zoneId).plusDays(1);
		calNewStartDate.setValue(tomorrow);
		
		ObservableList<String> statusOptions = FXCollections.observableArrayList("LIVE", "REPAIR", "RETIRED");
		cmbNewStatus.setItems(statusOptions);
		cmbNewStatus.setValue("LIVE");
	}
	
	/**
	 * Make database changes for the selected assets per user choices
	 */
	@FXML
	private void buildSql() {
		String endDate = "";
		String newStartDate = "";
		String newStatus = "";
		

		//build the list of assetIds to be updated
		StringBuilder assetsIn = new StringBuilder("(");
		Iterator it = mainApp.assetsToUpdate.iterator();
		Asset a;
		
		while (it.hasNext()) {
			a = (Asset) it.next();
			assetsIn.append(a.getAssetId());
			
			if (it.hasNext()) {
				assetsIn.append(",");
			}
		}
		assetsIn.append(")");
		System.out.println("The assets to update are: " + assetsIn);
		

		//get the dates for end & start
		endDate = calEndDate.getValue().toString();
		System.out.println("The end date is: " + endDate);
		
		newStartDate = calNewStartDate.getValue().toString();
		System.out.println("The new start date is: " + newStartDate);
		
		//get the status from the combo
		switch (cmbNewStatus.getValue()) {
		case "LIVE":
			newStatus = "1";
			break;
		case "REPAIR":
			newStatus = "2";
			break;
		case "RETIRE":
			newStatus = "3";
			break;
		default:
			Alerts.showAlert(AlertType.ERROR, "Error", "Something went wrong.");
			return;
		}
		System.out.println("The new status is: " + newStatus);
		
		//build end date query
		StringBuffer endDateQuery = new StringBuffer("update AM_AUTO_ASSET\r\n" + 
								"set END_DATE='" + endDate + " 23:59:59'\r\n" + 
								"where AUTO_ASSET_ID in " + assetsIn);
		System.out.println("The end date query is: " + endDateQuery);
		
		//build insert and update query
		StringBuffer insertQuery = new StringBuffer("INSERT into AM_AUTO_ASSET (ASSET_NAME, CREATE_HRS, MANUAL_HRS_SAVED, LIVE_DATE, SPRINT_LIVE, MAINTENANCE_TIME, TOOL_ID_FK, PRODUCT_ID_FK, OWNER_ID_FK, INTENT_ID_FK, STATUS_ID_FK, START_DATE, END_DATE, ESTIMATED_RUN_TIME_SECONDS, AUTO_RUN_TIMEOUT_SECONDS, TEST_COMPLETE_PROJECT_NAME, SELENIUM_METHOD, SELENIUM_CLASS, VERSION_CONTROL_TOOL, VERSION_CONTROL_LOCATION, AUTO_ASSET_NUMBER, ASSET_DEPLOY_LOCATION)\r\n" +
							"SELECT ASSET_NAME, CREATE_HRS, MANUAL_HRS_SAVED, LIVE_DATE, SPRINT_LIVE, MAINTENANCE_TIME, TOOL_ID_FK, PRODUCT_ID_FK, OWNER_ID_FK, INTENT_ID_FK, " + newStatus + ", '" + newStartDate + " 00:00:00', null, ESTIMATED_RUN_TIME_SECONDS, AUTO_RUN_TIMEOUT_SECONDS, TEST_COMPLETE_PROJECT_NAME, SELENIUM_METHOD, SELENIUM_CLASS, VERSION_CONTROL_TOOL, VERSION_CONTROL_LOCATION, AUTO_ASSET_NUMBER, ASSET_DEPLOY_LOCATION\r\n" +
							"FROM AM_AUTO_ASSET\r\n" +
							"WHERE AUTO_ASSET_ID in " + assetsIn + "\r\n");
		System.out.println("The insert query is: " + insertQuery);
				
		//end date the assets and generate the new db records
		try {
			DbConnection.executeQuery(mainApp.getConnection(), endDateQuery, true);
			DbConnection.executeQuery(mainApp.getConnection(), insertQuery, true);
			Alerts.showAlert(AlertType.CONFIRMATION, "Database Updated", "End dated and created new versions of " + mainApp.assetsToUpdate.size() + " assets.");
			
			//go back and show the table of the assets again for the same product
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeDialog();
		}
		

		/*
		 * get the list of assets from model
		 * get the new options from the gridpane
		 * get the connection
		 * end date the current assets
		 * do a sql insert of each asset
		 * rebuild the assetData that was selected
		 * show the assetPicker screen again with the updated rows (use the stuff from the older version app)
		 */
	}
	
	/**
	 * run query
	 */
	public void executeQuery(String query, boolean commit) {
		Connection conn = mainApp.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			
			if (!commit) {
				stmt.executeQuery(query);
				rs = stmt.getResultSet();
				System.out.println("result set: " + rs);
			}
			else {
				stmt.executeQuery(query);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
		}

	}
	
}

