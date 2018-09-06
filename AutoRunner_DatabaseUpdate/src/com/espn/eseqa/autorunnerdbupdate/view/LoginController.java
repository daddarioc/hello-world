package com.espn.eseqa.autorunnerdbupdate.view;

import java.sql.Connection;
import java.sql.SQLException;

import com.espn.eseqa.autorunnerdbupdate.application.Alerts;
import com.espn.eseqa.autorunnerdbupdate.application.MainApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/*import utilities.*;
import database.*;
import dataModel.*;
*/
/**
 * Controller class for login window
 * 
 * @author DAddariC
 *
 */
@Deprecated //CD 9/5/18 - This isn't needed right now as SQL Server we're using is using integrated authentication in Windows
public class LoginController {
	// private Model model; this was for the multi-pane version of the app...tbd if
	// needed

	@FXML
	private Button btnLogin;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	// reference to main application
	private MainApp mainApp;

	public LoginController() {

	}

	/**
	 * Show the product picker pane
	 * 
	 * @param e
	 */
	@FXML
	private void showProductPicker(ActionEvent event) {
		mainApp.showProductPicker();
	}

	private void initialize() {
		// get all the window specific stuff done in here, like setting up columns grids
		// etc.

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// this is where you'd get data from the main app (i.e. model-view-controller)
		// so things you set there can be used here
	}

	/**
	 * Set the mainApp connection object after getting login details, then advance to area/product screen
	 * @param event
	 */
	@FXML
	private void connectDatabase(ActionEvent event) {
		try {
			String sUsername = "";
			String sPassword = "";
			Connection conn = null;
	
			mainApp.connectToDatabase(sUsername, sPassword);
			conn = mainApp.getConnection();
			mainApp.setConnection(conn);
			mainApp.showProductPicker();
		}
		catch (Exception e) {
			e.printStackTrace();
			Alerts.showAlert(AlertType.ERROR, "Fatal Error", "Authentication problem. Exception:\n" + e.getMessage());
			System.exit(0);
		}
		
		
		/* CD 9/5/18 - Older user authentication code, probably need this for Mac/linux.
		 * while (true) {
			sUsername = username.getText();
			sPassword = password.getText();

			if (sUsername.isEmpty() || sPassword.isEmpty()) {
				Alerts.showAlert(AlertType.ERROR, "Authentication Error", "Input a valid username and password.");
				return;
			} else {
				try {
					mainApp.connectToDatabase(sUsername, sPassword);
					conn = mainApp.getConnection();
					mainApp.setConnection(conn);
					
					break;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Alerts.showAlert(AlertType.ERROR, "Connection Error", e.getMessage());
					return;
				}

			}

		}
		mainApp.showProductPicker();
*/
		

		/*
		 * model.connection.getConnection(sUsername, sPassword);
		 * Navigator.loadWindow(Navigator.ASSET_PICKER);
		 */
		/*
		 * TextInputDialog userInput = new TextInputDialog();
		 * userInput.setTitle("Input username");
		 * userInput.getDialogPane().setContentText("Username: "); TextField tf =
		 * userInput.getEditor(); Optional<String> result = userInput.showAndWait();
		 * 
		 * if (result.isPresent()) { // validate input username = tf.getText(); if
		 * (!username.isEmpty()) { break; } else { showAlert(AlertType.ERROR,
		 * "Input Error", "Input username"); } } else { // canceled return; }
		 */

	}

	/**
	 * Handler fired when requesting new window
	 * 
	 * @param event
	 */
	/*
	 * @FXML void nextPane(ActionEvent event) {
	 * Navigator.loadWindow(Navigator.ASSET_PICKER); }
	 * 
	 * public void initModel(Model model) { if (this.model != null) { throw new
	 * IllegalStateException("Model can only be intialized once"); } this.model =
	 * model; }
	 */

}
