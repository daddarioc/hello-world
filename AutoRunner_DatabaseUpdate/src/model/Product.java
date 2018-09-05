package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents product names from database
 * @author daddaric
 *
 */
public class Product {
	private IntegerProperty productId = new SimpleIntegerProperty();
	private IntegerProperty areaIdFk = new SimpleIntegerProperty();
	private StringProperty productName = new SimpleStringProperty();
	
	public Product(int productId, int areaIdFk, String productName) {
		setProductId(productId);
		setAreaIdFk(areaIdFk);
		setProductName(productName);
	}
	
	public int getProductId() {
		return productId.get();
	}
	public void setProductId(int productId) {
		this.productId.set(productId);
	}
	public IntegerProperty getProductIdProperty() {
		return productId;
	}
	
	
	public int getAreaIdFk() {
		return areaIdFk.get();
	}
	public void setAreaIdFk(int areaIdFk) {
		this.areaIdFk.set(areaIdFk);
	}
	public IntegerProperty getAreaIdFkProperty() {
		return areaIdFk;
	}
	
	public String getProductName() {
		return productName.get();
	}
	public void setProductName(String productName) {
		this.productName.set(productName);
	}
	public StringProperty getProductNameProperty() {
		return productName;
	}
	@Override
	public String toString() {
		return productName.get();
	}
	
	
}
