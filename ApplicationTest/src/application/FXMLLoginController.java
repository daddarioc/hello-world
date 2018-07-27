package application;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import utilities.*;
import database.*;

/**
 * Controller class for login window
 * @author DAddariC
 *
 */
public class FXMLLoginController {
	private DbConnection myConnection;
	
	@FXML
	private Button btnLogin;
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private void connectDatabase(ActionEvent event) {
		
		while (true) {
			String sUsername = username.getText();
			String sPassword = password.getText();
			
			if (sUsername.isEmpty() || sPassword.isEmpty()) {
				Alerts.showAlert(AlertType.ERROR, "Authentication Error", "Input a valid username and password.");
				return;
			}
			
			myConnection.getConnection(sUsername, sPassword);
			/*
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
			 */
		}
		
		/*while (true) {
			
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
		buildTable();*/
/*
		System.out.println("username: " + username);
		System.out.println("password: " + password);
*/
	}
	/**
	 * Handler fired when requesting new window
	 * @param event
	 */
	@FXML
	void nextPane(ActionEvent event) {
		Navigator.loadWindow(Navigator.ASSET_PICKER);
	}
	
}
