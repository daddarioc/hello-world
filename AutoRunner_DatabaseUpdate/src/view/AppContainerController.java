package view;

import application.MainApp;
import javafx.fxml.FXML;

public class AppContainerController {
	MainApp mainApp; // this likely won't really be used since the AppContainer is the root
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void closeApplication() {
		System.exit(0);
	}
}
