package view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.Area;
import model.Asset;
import model.Product;

public class ProductPickerController {
	//reference to main application
	private MainApp mainApp;
	private ObservableList<Area> areaData = FXCollections.observableArrayList();
	private ObservableList<Product> productData = FXCollections.observableArrayList();
		
	@FXML
	private ListView<Area> lstArea = new ListView<Area>();
	
	@FXML
	private ListView<Product> lstProduct = new ListView<Product>();
	
	@FXML
	private Button btnContinue;
	
	public ProductPickerController() {
		
	}
	
	@FXML
	private void showAssetPicker(ActionEvent event) {
		getAssets();
		mainApp.showAssetPicker();
	}
	
	@FXML
	private void initialize() {
		//get all the window specific stuff done in here, like setting up columns grids etc.
		//ArrayList<Pair<Integer, String>> areaList = model.connection.getAreas();
		
		//load area and product lists
		lstArea.setItems(areaData);
		//lstProduct.setItems(productData);
	}
	
	
	@FXML
	private void loadProducts(MouseEvent e) {
		int areaId = lstArea.getSelectionModel().getSelectedItem().getAreaId();
		ObservableList<Product> products = FXCollections.observableArrayList();
		
		for (Product p : productData) {
			if (p.getAreaIdFk() == areaId) {
				products.add(p);
			}
		}
		
		lstProduct.setItems(products);
	}
	
	/**
	 * Load Areas from data base
	 */
	public void getAreas() {
		Connection conn = mainApp.getConnection();
		String query = "select * from AM_AREA order by AREA_NAME";
		Statement stmt = null;
		ResultSet rs = null;
		Area area = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				area = new Area(rs.getInt(1), rs.getString(2));
				System.out.println(area);
				areaData.add(area);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
		}

	}
	
	/**
	 * Load Products from database
	 */
	public void getProducts() {
		Connection conn = mainApp.getConnection();
		String query = "select * from AM_PRODUCT";
		Statement stmt = null;
		ResultSet rs = null;
		Product product = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				product = new Product(rs.getInt(1), rs.getInt(2), rs.getString(3));
				System.out.println(product);
				productData.add(product);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
		}

	}
	
	/**
	 * Load assets from data base into main model.assetData
	 */
	public void getAssets() {
		Connection conn = mainApp.getConnection();
		String query = "select AUTO_ASSET_ID, ASSET_NAME, STATUS_ID_FK, END_DATE\r\n" + 
					   "from AM_AUTO_ASSET\r\n" +
					   "where PRODUCT_ID_FK=" + lstProduct.getSelectionModel().getSelectedItem().getProductId() + " and (STATUS_ID_FK=1 or STATUS_ID_FK=2) and END_DATE is null\r\n" + 
					   "order by ASSET_NAME";
		System.out.println("Select query is: " + query);
		//modify items retrieved" + " AND END_DATE is null AND STATUS_ID_FK=1\r\n" +
		Statement stmt = null;
		ResultSet rs = null;
		Asset asset = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				asset = new Asset(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4));
				System.out.println(asset);
				mainApp.assetData.add(asset);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
		}

	}

	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		//this is where you'd get data from the main app (i.e. model-view-controller) so things you set there can be used here
	}
}
