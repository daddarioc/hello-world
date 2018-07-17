package assetRecord;

import java.sql.Date;


public class Record {
	private String assetId;
	private String assetName;
	private String status;
	private Date endDate;

	public Record(String assetId, String assetName, String status, Date endDate) {
		setAssetId(assetId);
		setAssetName(assetName);
		setStatus(status);
		setEndDate(endDate);
	}
	
	public String getAssetId() {
		return assetId;
	}


	private void setAssetId(String assetId) {
		this.assetId = assetId;
	}


	public String getAssetName() {
		return assetName;
	}


	private void setAssetName(String assetName) {
		this.assetName = assetName;
	}


	public String getStatus() {
		return status;
	}


	private void setStatus(String status) {
		this.status = status;
	}


	public String getEndDate() {
		if (endDate != null) {
			return endDate.toString();	
		}
		else {
			return "null";
		}
	}


	private void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String toString() {
		return assetId + ", " + assetName + ", " + status + ", " + endDate; 
	}
	
}
