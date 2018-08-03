package application;

import java.io.IOException;

import dataModel.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			/*stackPane = new StackPane();
			Label label = new Label("test label");
			label.setVisible(false);
			stackPane.getChildren().add(label);*/
			
			//Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainFXML.fxml"));
			
			//Scene scene = new Scene(root, 1440, 768);
			primaryStage.setTitle("AutoAsset Update");
			primaryStage.setScene(createScene(loadMainPane()));
			primaryStage.show();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * Creates the main application scene.
     *
     * @param mainPane the main application layout.
     *
     * @return the created scene.
     */
    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);

        scene.getStylesheets().setAll(
            getClass().getResource("application.css").toExternalForm()
        );

        return scene;
    }
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private Pane loadMainPane() throws IOException {
		
		
		//Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainFXML.fxml"));
		//Pane mainPane = (Pane) FXMLLoader.load(getClass().getClassLoader().getResource(Navigator.MAIN));
		
		
		//set up the main window controller
		FXMLLoader loader = new FXMLLoader();
		Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.MAIN));
		MainController mainController = loader.getController();
		
		Navigator.setMainController(mainController);
		Navigator.loadWindow(Navigator.LOGIN);
		
		/*//set up login pane
		FXMLLoader loginLoader = new FXMLLoader();
		Pane loginPane = (Pane) loginLoader.load(getClass().getResourceAsStream(Navigator.LOGIN));
		FXMLLoginController loginController = loginLoader.getController();
		//set up asset pane
		FXMLLoader assetLoader = new FXMLLoader();
		Pane assetPane = (Pane) assetLoader.load(getClass().getResourceAsStream(Navigator.ASSET_PICKER));
		FXMLAssetPickerController assetController = assetLoader.getController();
		//set up editor pane
		FXMLLoader editorLoader = new FXMLLoader();
		Pane editorPane = (Pane) editorLoader.load(getClass().getResourceAsStream(Navigator.ASSET_EDIT));
		FXMLMainController editorController = editorLoader.getController();*/
		
		FXMLLoader loginLoader = new FXMLLoader(getClass().getResource(Navigator.LOGIN));
		FXMLLoginController loginController = loginLoader.getController();

		//set up asset pane
		FXMLLoader assetLoader = new FXMLLoader(getClass().getResource(Navigator.ASSET_PICKER));
		FXMLAssetPickerController assetController = assetLoader.getController();
		
		//set up editor pane
		FXMLLoader editorLoader = new FXMLLoader(getClass().getResource(Navigator.ASSET_EDIT));
		FXMLMainController editorController = editorLoader.getController();

		Model model = new Model();
		
		
		loginController.initModel(model);
		assetController.initModel(model);
		editorController.initModel(model);
		
		//mainPane.getChildren().add(loginController);
	
		return mainPane;
	}

}
