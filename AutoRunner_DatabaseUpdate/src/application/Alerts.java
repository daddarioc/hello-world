package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {
	
	public static void showAlert(AlertType type, String title, String message) {
		Alert success = new Alert(type);
		success.setTitle(title);
		success.setContentText(message);
		success.showAndWait();
	}
}
