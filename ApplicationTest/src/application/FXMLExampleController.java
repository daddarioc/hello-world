package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import assetRecord.Record;
import database.DbConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class FXMLExampleController implements Initializable {

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
	private TableColumn<Record, String> colEndDate;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//set up the columns from the fxml to be bound to the record.property
		colAssetId.setCellValueFactory(new PropertyValueFactory<Record, String>("assetId"));
		colAssetName.setCellValueFactory(new PropertyValueFactory<Record, String>("assetName"));
		colStatus.setCellValueFactory(new PropertyValueFactory<Record, String>("status"));
		colEndDate.setCellValueFactory(new PropertyValueFactory<Record, String>("endDate"));
		
		//holds records to be put into the tableview
		ObservableList<Record> data;
		data = FXCollections.observableArrayList();

		//get records from the database
		ArrayList<Record> list = new ArrayList<Record>();
		list = getItemsToAdd();
		
		//add records to the table
		for (Record i : list) {
			System.out.println("Add row: " + i);
			data.add(i);
		}
		tableView.setItems(data);
	}

	/**
	 * connect to database and retrieve results 
	 * @return ArrayList<Record>
	 */
	private ArrayList<Record> getItemsToAdd() {
		ArrayList<Record> list = new ArrayList<Record>();
		DbConnection myConn = new DbConnection();

		myConn.getConnection();
		list = myConn.populateRecords();
		myConn.connectionClose();
		
		return list;
	}
}