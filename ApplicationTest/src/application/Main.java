package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	private StackPane stackPane;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			/*stackPane = new StackPane();
			Label label = new Label("test label");
			label.setVisible(false);
			stackPane.getChildren().add(label);*/
			
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("testApplicationFXML.fxml"));
			
			Scene scene = new Scene(root, 1440, 768);
			primaryStage.setTitle("AutoAsset Update");

			primaryStage.setScene(scene);
			primaryStage.show();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
