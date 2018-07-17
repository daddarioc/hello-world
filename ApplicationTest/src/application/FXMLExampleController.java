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
		System.out.println("initializing...");

		colAssetId.setCellValueFactory(new PropertyValueFactory<Record, String>("assetId"));
		colAssetName.setCellValueFactory(new PropertyValueFactory<Record, String>("assetName"));
		colStatus.setCellValueFactory(new PropertyValueFactory<Record, String>("status"));
		colEndDate.setCellValueFactory(new PropertyValueFactory<Record, String>("endDate"));
		
		ObservableList<Record> data;

		data = FXCollections.observableArrayList();

		ArrayList<Record> list = new ArrayList<Record>();
		list = getItemsToAdd();
		
		for (Record i : list) {
			System.out.println("Add row: " + i);
			data.add(i);
		}
		
		tableView.setItems(data);
	}

	private ArrayList<Record> getItemsToAdd() {
		ArrayList<Record> list = new ArrayList<Record>();

		DbConnection myConn = new DbConnection();

		myConn.getConnection();
		list = myConn.populateRecords();
		myConn.connectionClose();
		
		return list;

		/*
		 * try { // build the table based on data received for (int i = 0; i <
		 * rs.getMetaData().getColumnCount(); i++) { final int j = i; TableColumn<?, ?>
		 * col = new TableColumn<Object, Object>(rs.getMetaData().getColumnName(i+1));
		 * 
		 * col.setCellValueFactory(new
		 * Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<
		 * String>>(){ public ObservableValue<String>
		 * call(CellDataFeatures<ObservableList, String> param) { return new
		 * SimpleStringProperty(param.getValue().get(j).toString()); } });
		 * 
		 * tableView.getColumns().addAll(col); }
		 * 
		 * // add the data to the table while (rs.next()) { ObservableList<String> row =
		 * FXCollections.observableArrayList();
		 * 
		 * for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
		 * row.add(rs.getString(i)); } System.out.println("row added: " + row);
		 * data.add(row); }
		 * 
		 * myConn.connectionClose(); } catch (SQLException e) { System.out.
		 * println("Exception encountered trying to parse table and row data:");
		 * e.printStackTrace(); }
		 */

	}
}