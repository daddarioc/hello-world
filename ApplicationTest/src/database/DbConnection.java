package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import assetRecord.Record;

public class DbConnection {
	private ResultSet rs;
	private Connection conn;
	
	public ResultSet getRs() {
		return rs;
	}

	private void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public DbConnection() {

	}
	
	public ArrayList<Record> populateRecords() {
		ArrayList<Record> list = new ArrayList<Record>();
		Record i = null;
		
		try {
			while (rs.next()) {
				i = new Record(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4));
				list.add(i);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void connectionClose() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getConnection() {
		try {
			String url = "jdbc:sqlserver://sqdatad99c03vc2;databaseName=ESEQA_Metrics;integratedSecurity=true;";
			conn = DriverManager.getConnection(url, "daddaric", "b33rLike03");
		
			Statement stmt = conn.createStatement();
			this.rs = stmt.executeQuery("select AUTO_ASSET_ID, ASSET_NAME, STATUS_ID_FK, END_DATE from AM_AUTO_ASSET where TOOL_ID_FK=2 and PRODUCT_ID_FK=6 and AUTO_ASSET_ID in (5533, 5410, 5414, 5437, 5413, 5420) order by ASSET_NAME");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	
}
