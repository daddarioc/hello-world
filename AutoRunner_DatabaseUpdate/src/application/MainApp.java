package application;
	
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import database.DbConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Asset;
import view.AssetPickerController;
import view.AssetUpdateController;
import view.LoginController;
import view.ProductPickerController;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	//contains assets from a given product to load into the tableview
	public ObservableList<Asset> assetData = FXCollections.observableArrayList();
	
	//contains assets selected from tableview to be updated in the database
	public ObservableList<Asset> assetsToUpdate = FXCollections.observableArrayList();
	
	private Connection conn;
	private ResultSet rs;
	private Statement stmt;
	
	
	
	/**
	 * Initial loading of the application, called by main()
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("AutoRunner Database Update");
			
			initRootLayout();
			showLogin();
			
		/*	Default javafx project setup
		 * BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Runs at application close; make sure any open database connections are closed on exit
	 */
	@Override
	public void stop() {
		try {
			if (conn != null) {
				conn.close();
			}	
		}
		catch (Exception e) {
			System.out.println("Exception closing database connection: " + e.getMessage());
		}
	}
	
	/**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/AppContainer.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the login overview inside the root layout.
     */
    public void showLogin() {
        try {
            // Load login 
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/Login.fxml"));
            GridPane login = (GridPane) loader.load();
            
            // Set login into the center of root layout.
            rootLayout.setCenter(login);
            
            LoginController loginController = loader.getController();
            loginController.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the area/product picker inside the root layout.
     */
    public void showProductPicker() {
        try {
            // Load area/product
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/ProductPicker.fxml"));
            GridPane login = (GridPane) loader.load();
            
            // Set area/product into the center of root layout.
            rootLayout.setCenter(login);
            
            ProductPickerController pickerController = loader.getController();
            pickerController.setMainApp(this);
            pickerController.getAreas();
            pickerController.getProducts();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the asset picker inside the root layout.
     */
    public void showAssetPicker() {
        try {
            // Load asset picker
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/AssetPicker.fxml"));
            GridPane login = (GridPane) loader.load();
            
            // Set picker into the center of root layout.
            rootLayout.setCenter(login);
            
            AssetPickerController assetController = loader.getController();
            assetController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the asset update dialog inside the root layout.
     */
    public void showAssetUpdateDialog() {
        try {
            // Load asset picker
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/AssetUpdateDialog.fxml"));
            AnchorPane update = (AnchorPane) loader.load();
            
            // Set picker into the center of root layout.
            rootLayout.setCenter(update);
            
            AssetUpdateController updateController = loader.getController();
            updateController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public ObservableList<Asset> getAssetsToUpdate() {
		return assetsToUpdate;
	}

	public void setAssetsToUpdate(ObservableList<Asset> assetsToUpdate) {
		this.assetsToUpdate = assetsToUpdate;
	}

	private ObservableList<Asset> getAssetData() {
		return assetData;
	}

	private void setAssetData(ObservableList<Asset> assetData) {
		this.assetData = assetData;
	}
	
	/**
	 * Establish connection to database, return Connection
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() {
		return conn;
	}

	public void connectToDatabase(String username, String password) throws Exception {
		Connection conn = null;
		
		try {
			conn = DbConnection.getConnection(username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		setConnection(conn);
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public static void main(String[] args) {
		launch(args);
	}
}

