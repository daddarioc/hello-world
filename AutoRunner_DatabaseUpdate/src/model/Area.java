package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Contains area name and ids from database
 * @author daddaric
 *
 */
public class Area {
	private IntegerProperty areaId = new SimpleIntegerProperty();
	private StringProperty areaName = new SimpleStringProperty();
	
	public Area(int areaId, String areaName) {
		setAreaId(areaId);
		setAreaName(areaName);
	}
	
	public int getAreaId() {
		return areaId.get();
	}
	
	public void setAreaId(int areaId) {
		this.areaId.set(areaId);
	}
	
	public IntegerProperty getAreaIdProperty() {
		return areaId;
	}
	
	public String getAreaName() {
		return areaName.get();
	}
	
	public void setAreaName(String areaName) {
		this.areaName.set(areaName);
	}
	
	public StringProperty getAreaNameProperty() {
		return areaName;
	}

	@Override
	public String toString() {
		return areaName.get();
	}

	
}

