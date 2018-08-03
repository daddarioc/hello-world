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
import dataModel.*;

/**
 * Controller class for login window
 * @author DAddariC
 *
 */
public class FXMLLoginController {
	private Model model; 
	
	@FXML
	private Button btnLogin;
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private void connectDatabase(ActionEvent event) {
		String sUsername = "";
		String sPassword = "";
		
		while (true) {
			sUsername = username.getText();
			sPassword = password.getText();
			
			if (sUsername.isEmpty() || sPassword.isEmpty()) {
				Alerts.showAlert(AlertType.ERROR, "Authentication Error", "Input a valid username and password.");
				return;
			}
	
			break;
		}

		model.connection.getConnection(sUsername, sPassword);
		Navigator.loadWindow(Navigator.ASSET_PICKER);
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
	
	/**
	 * Handler fired when requesting new window
	 * @param event
	 */
	@FXML
	void nextPane(ActionEvent event) {
		Navigator.loadWindow(Navigator.ASSET_PICKER);
	}
	
	public void initModel(Model model) {
		if (this.model != null) {
			throw new IllegalStateException("Model can only be intialized once");
		}
		this.model = model;
	}
	
}
