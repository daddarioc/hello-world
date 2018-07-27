package application;

import java.io.IOException;

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
		FXMLLoader loader = new FXMLLoader();
		
		//Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainFXML.fxml"));
		//Pane mainPane = (Pane) FXMLLoader.load(getClass().getClassLoader().getResource(Navigator.MAIN));
		Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(Navigator.MAIN));
		MainController mainController = loader.getController();
		
		Navigator.setMainController(mainController);
		Navigator.loadWindow(Navigator.LOGIN);
		
		return mainPane;
	}

}
