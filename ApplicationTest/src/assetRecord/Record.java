package assetRecord;

import java.sql.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * pojo for database assets
 * @author DAddariC
 *
 */
public class Record {
	private StringProperty assetId = new SimpleStringProperty();
	private StringProperty assetName = new SimpleStringProperty();
	private StringProperty status = new SimpleStringProperty();
	private ObjectProperty<Date> endDate = new SimpleObjectProperty<>();

	public Record(String assetId, String assetName, String status, Date endDate) {
		setAssetId(assetId);
		setAssetName(assetName);
		setStatus(status);
		setEndDate(endDate);
	}
	

	public String getAssetId() {
		return assetId.get();
	}


	public void setAssetId(String assetId) {
		this.assetId.set(assetId);
	}

	public StringProperty getAssetIdProperty() {
		return assetId;
	}
	
	public String getAssetName() {
		return assetName.get();
	}
	
	public StringProperty getAssetNameProperty() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName.set(assetName);
	}

	public String getStatus() {
		return status.get();
	}
	
	public StringProperty getStatusProperty() {
		return status;
	}

	public void setStatus(String status) {
		this.status.set(status);
	}

	public void setEndDate(Date endDate) {
		if (endDate != null) {
			this.endDate.set(endDate);	
		}
	}

	public Date getEndDate() {
		if (endDate != null) {
			return endDate.get();
		}
		else {
			return null;
		}
	}

	public ObjectProperty<Date> endDateProperty() {
		return endDate;
	}

	public String toString() {
		return assetId + ", " + assetName + ", " + status + ", " + endDate; 
	}
	
}
