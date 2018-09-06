package com.espn.eseqa.autorunnerdbupdate.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.espn.eseqa.autorunnerdbupdate.database.DbConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AssetOperations {
	/**
	 * Get a list of assets correpsonding to the passed in product
	 * @param conn
	 * @param product
	 * @return
	 */
	public static ObservableList<Asset> retrieveDbAssets(Connection conn, int product) {
		ObservableList<Asset> assetList = FXCollections.observableArrayList();
		Asset asset = null;
		ResultSet rs = null;
		StringBuffer query = new StringBuffer("select AUTO_ASSET_ID, ASSET_NAME, STATUS_ID_FK, END_DATE\r\n" + 
		   "from AM_AUTO_ASSET\r\n" +
		   "where PRODUCT_ID_FK=" + product + " and (STATUS_ID_FK=1 or STATUS_ID_FK=2) and END_DATE is null\r\n" + 
		   "order by ASSET_NAME");
		
		try {
			rs = DbConnection.executeQuery(conn, query, false);
			
			while (rs.next()) {
				asset = new Asset(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4));
				System.out.println(asset);
				//mainApp.assetData.add(asset);
				assetList.add(asset);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return assetList;
	}
}
