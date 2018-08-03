package application;

import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import assetOperation.AssetOperation;
import assetRecord.Record;
import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import dataModel.*;

public class FXMLMainController implements Initializable {
	private DbConnection myConn = new DbConnection();
	private Model model;
	
	@FXML
	private Font x1;

	@FXML
	private Color x2;

	@FXML
	private Font x3;

	@FXML
	private Color x4;

	@FXML
	private void quit(ActionEvent event) {
		model.connection.connectionClose();
		System.exit(0);
	}

	
	@FXML
	private TableView<Record> tableView;

	@FXML
	private TableColumn<Record, String> colAssetId;

	@FXML
	private TableColumn<Record, String> colAssetName;

	@FXML
	private TableColumn<Record, String> colStatus;

	@FXML
	private TableColumn<Record, Date> colEndDate;

	@FXML
	private Button btnUpdateEndDate;

	@FXML
	private Button btnUpdateStatus;

	/**
	 * collect user information and connect to database
	 * 
	 * @param event
	 */
	@FXML
	private void connectDatabase(ActionEvent event) {
		String username = "";
		String password = "";
		
		while (true) {
			TextInputDialog userInput = new TextInputDialog();
			userInput.setTitle("Input username");
			userInput.getDialogPane().setContentText("Username: ");
			TextField tf = userInput.getEditor();
			Optional<String> result = userInput.showAndWait();

			if (result.isPresent()) {
				// validate input
				username = tf.getText();
				if (!username.isEmpty()) {
					break;
				} else {
					showAlert(AlertType.ERROR, "Input Error", "Input username");
				}
			} else {
				// canceled
				return;
			}

		}
		
		while (true) {
			
			TextInputDialog userInput = new TextInputDialog();
			userInput.setTitle("Input password");
			userInput.getDialogPane().setContentText("Password: ");
			TextField tf = userInput.getEditor();
			Optional<String> result = userInput.showAndWait();

			if (result.isPresent()) {
				// validate input
				password = tf.getText();
				if (!password.isEmpty()) {
					break;
				} else {
					showAlert(AlertType.ERROR, "Input Error", "Input password");
				}
			} else {
				// canceled
				return;
			}

		}
		
		myConn.getConnection(username, password);
		buildTable();
/*
		System.out.println("username: " + username);
		System.out.println("password: " + password);
*/
	}

	/**
	 * change the asset status based on user input
	 * 
	 * @param event
	 */
	@FXML
	private void updateStatus(ActionEvent event) {
		updateAsset(AssetOperation.STATUS);
	}

	/**
	 * Update the end date of the selected row records with user input date
	 * 
	 * @param event
	 */
	@FXML
	private void updateEndDate(ActionEvent event) {
		updateAsset(AssetOperation.END_DATE);
	}

	/**
	 * generate a new version(s) of the asset
	 * 
	 * @param event
	 */
	@FXML
	private void newVersion(ActionEvent event) {
		updateAsset(AssetOperation.NEW_VERSION);
	}

	private void updateAsset(AssetOperation OPERATION) {
		ObservableList<Record> records;
		StringBuilder assets = new StringBuilder();
		records = tableView.getSelectionModel().getSelectedItems();
		StringBuilder query = new StringBuilder();
		int newStatus = 0;

		// parse asset ids for update string
		for (Record r : records) {
			if (assets.length() != 0) {
				assets.append(",");
			}
			assets.append(r.getAssetId());
		}

		switch (OPERATION) {
		case END_DATE:
			StringBuilder newEndDate = new StringBuilder();

			// prompt user for new date string until valid date is input
			while (true) {
				try {
					TextInputDialog dateInput = new TextInputDialog();
					dateInput.setTitle("Update End Date");
					dateInput.getDialogPane().setContentText("End Date (MM/DD/YYYY):");
					TextField tf = dateInput.getEditor();
					Optional<String> result = dateInput.showAndWait();
					if (result.isPresent()) {
						// validate date string
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						sdf.setLenient(false);
						java.util.Date date = sdf.parse(tf.getText());
						newEndDate.append(sdf.format(date) + " 00:00:00");
						break;
					} else {
						// cancel
						return;
					}

				} catch (ParseException e) {
					e.printStackTrace();
					showAlert(AlertType.ERROR, "Input Error", "Input valid date in format MM/DD/YYYY only");
				}
			}
			System.out.println("new date is: " + newEndDate);

			query.append("update AM_AUTO_ASSET\r\n" + "set END_DATE='" + newEndDate + "'\r\n"
					+ "where AUTO_ASSET_ID in (" + assets + ")");

			System.out.println("Updating End Date for:\n" + query);

			// perform database update
			databaseOperation(query.toString());

			// display new date to user
			Alert success = new Alert(AlertType.INFORMATION);
			success.setTitle("Success");
			success.setContentText("New asset(s) END_DATE: " + newEndDate);
			success.show();

			// refresh tableview with new data
			buildTable();
			break;
		case STATUS:
			// prompt user for new date string until valid date is input
			while (true) {

				TextInputDialog statusInput = new TextInputDialog();
				statusInput.setTitle("Update Asset Status");
				statusInput.getDialogPane().setContentText("New Status (1-3):");
				TextField tf = statusInput.getEditor();
				Optional<String> result = statusInput.showAndWait();

				if (result.isPresent()) {
					// validate input
					newStatus = Integer.parseInt(tf.getText());
					if (newStatus >= 1 && newStatus <= 3) {
						break;
					} else {
						showAlert(AlertType.ERROR, "Input Error", "Input valid status (1-Live, 2-Repair, 3-Retire) ");
					}
				} else {
					// canceled
					return;
				}

			}
			System.out.println("new status is: " + newStatus);

			query.append("update AM_AUTO_ASSET\r\n" + "set STATUS_ID_FK='" + newStatus + "'\r\n"
					+ "where AUTO_ASSET_ID in (" + assets + ")");

			System.out.println("Updating status for:\n" + query);

			// perform database update
			databaseOperation(query.toString());

			// display new date to user
			showAlert(AlertType.INFORMATION, "Success", "New STATUS_ID_FK: " + newStatus);
			// refresh tableview with new data
			buildTable();

			break;
		case NEW_VERSION:
			// make a copy of the current row, and create a new record. which fields should
			// be changed with the new record
			// clear end date, make startdate or live date today, make status 1?
			String tQuery = "insert into AM_AUTO_ASSET (ASSET_NAME, CREATE_HRS, MANUAL_HRS_SAVED, LIVE_DATE, SPRINT_LIVE, MAINTENANCE_TIME, TOOL_ID_FK, PRODUCT_ID_FK, OWNER_ID_FK, INTENT_ID_FK, STATUS_ID_FK, START_DATE, ESTIMATED_RUN_TIME_SECONDS, AUTO_RUN_TIMEOUT_SECONDS, TEST_COMPLETE_PROJECT_NAME, SELENIUM_METHOD, SELENIUM_CLASS, VERSION_CONTROL_TOOL, VERSION_CONTROL_LOCATION, AUTO_ASSET_NUMBER, ASSET_DEPLOY_LOCATION)\r\n"
					+ "select ASSET_NAME, CREATE_HRS, MANUAL_HRS_SAVED, LIVE_DATE, SPRINT_LIVE, MAINTENANCE_TIME, TOOL_ID_FK, PRODUCT_ID_FK, OWNER_ID_FK, INTENT_ID_FK, STATUS_ID_FK, START_DATE, ESTIMATED_RUN_TIME_SECONDS, AUTO_RUN_TIMEOUT_SECONDS, TEST_COMPLETE_PROJECT_NAME, SELENIUM_METHOD, SELENIUM_CLASS, VERSION_CONTROL_TOOL, VERSION_CONTROL_LOCATION, AUTO_ASSET_NUMBER, ASSET_DEPLOY_LOCATION  \r\n"
					+ "FROM AM_AUTO_ASSET\r\n" + "where AUTO_ASSET_ID in (1111)";

			break;
		}
	}

	private void showAlert(AlertType type, String title, String message) {
		Alert success = new Alert(type);
		success.setTitle(title);
		success.setContentText(message);
		success.showAndWait();
	}

	private Optional<String> getInput() {
		Optional<String> result;

		TextInputDialog statusInput = new TextInputDialog();
		statusInput.setTitle("Update Asset Status");
		statusInput.getDialogPane().setContentText("New Status (1-3):");
		TextField tf = statusInput.getEditor();
		result = statusInput.showAndWait();

		return result;
	}

	private void databaseOperation(String query) {
		// perform database update
		myConn.updateRows(query);
		//myConn.connectionClose();
	}

	/**
	 * generate column for the table
	 * 
	 * @param property
	 * @param title
	 * @param type
	 * @return
	 */
	private <T> TableColumn<Record, T> createTableColumn(String property, String title, Class<T> type) {
		TableColumn<Record, T> col = new TableColumn<>(title);

		col.setCellValueFactory(new PropertyValueFactory<>(property));
		col.setEditable(false);
		col.setPrefWidth(100);

		return col;
	}

	private void buildTable() {
		// holds records to be put into the tableview
		ObservableList<Record> data;
		data = FXCollections.observableArrayList();

		// get records from the database
		ArrayList<Record> list = new ArrayList<Record>();
		list = getItemsToAdd();

		// add records to the table
		for (Record i : list) {
			System.out.println("Add row: " + i);
			data.add(i);
		}
		tableView.setItems(data);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// set up columns
		colAssetId = createTableColumn("assetId", "Asset ID", String.class);
		colAssetName = createTableColumn("assetName", "Name", String.class);
		colStatus = createTableColumn("status", "Status", String.class);
		colEndDate = createTableColumn("endDate", "End Date", Date.class);

		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableView.getColumns().addAll(colAssetId, colAssetName, colStatus, colEndDate);
		/*
		 * this was how cell values were populated prior to using StringProperty
		 * variables colAssetId.setCellValueFactory(new PropertyValueFactory<Record,
		 * String>("assetId")); colAssetName.setCellValueFactory(new
		 * PropertyValueFactory<Record, String>("assetName"));
		 * colStatus.setCellValueFactory(new PropertyValueFactory<Record,
		 * String>("status")); colEndDate.setCellValueFactory(new
		 * PropertyValueFactory<Record, String>("endDate"));
		 */

	}

	/**
	 * connect to database and retrieve results
	 * 
	 * @return ArrayList<Record>
	 */
	private ArrayList<Record> getItemsToAdd() {
		ArrayList<Record> list = new ArrayList<Record>();

		list = myConn.populateRecords();
		//myConn.connectionClose();

		return list;
	}
	
	public void initModel(Model model) {
		if (this.model != null) {
			throw new IllegalStateException("Model can only be intialized once");
		}
		this.model = model;
	}
}