package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import assetRecord.Record;
import database.DbConnection;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("testApplicationFXML.fxml"));
			// BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 1440, 768);

			primaryStage.setTitle("JavaFX Test Application");

			primaryStage.setScene(scene);
			primaryStage.show();
			
			//buildTable();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
