package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.Alerts;
import model.Asset;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Pair;

public class DbConnection {
	private static Connection conn;
	private ResultSet rs;
	private Statement stmt;
	private static String ConnectionString = "jdbc:sqlserver://sqdatad99c03vc2;databaseName=ESEQA_Metrics;integratedSecurity=true;";
	private static String DB_USER;
	private static String DB_PASSWORD;
	protected static boolean stopOnError = true;
	
	/**
	 * generates a connection to the AutoRunner SQL Server Database
	 * @return connection
	 * @throws SQLException
	 */
	public static Connection getConnection(String DB_USER, String DB_PASSWORD) throws Exception  {   
		try {
			int timeout = 1; //in sec) 
			if (conn == null  || !conn.isValid(timeout)) {
				//TODO this never takes a bad login into consideration since it uses integrated authentication
				conn = DriverManager.getConnection(ConnectionString); //, DB_USER, DB_PASSWORD);
				//ProxyScheduler.getLogger().info("DB Connection Successful......");				
			}	
			return conn;
		} catch (SQLException se) {
			String errorMsg = "Unable to establish connection to the db. Connection string: " + ConnectionString + "; User: " + DB_USER;
			throw new Exception(errorMsg + "\n" + se.getMessage(), se);
		}
	}   
		
	/**
	 * using the given connection string executes the command and returns 
	 * a scrollable result set object
	 * the result set is not updatable and should be used only for data retrieve 
	 * @param conn
	 * @param command
	 * @param commit
	 * @return
	 * @throws Exception
	 */
	public static ResultSet executeQuery(Connection conn, StringBuffer command, Boolean commit) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;

		try          {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (!commit && stopOnError) {
				stmt.execute(command.toString());
			} else {
				stmt.execute(command.toString());
			}

			if (commit && !conn.getAutoCommit()) {
				conn.commit();
			}

			command = null;  
			if (!commit) 
				rs = stmt.getResultSet();			
			if ( rs != null) {
				return stmt.getResultSet();
			} else {
				if (commit) {
					System.out.println("No of DB Records Created/Updated: " + stmt.getUpdateCount());
					//ProxyScheduler.getLogger().info("No of DB Records Created/Updated: " + stmt.getUpdateCount());
				}
				else {
					System.out.println("Executing the sql command returned no record.");
					//ProxyScheduler.getLogger().info("Executing the sql command returned no record");
				}
				
				return null;
			}               
		} catch (SQLException e) {
			String errorMsg = "Error executing sql command: " + command;
			System.out.println("Exception: " + e.getMessage());
			//ProxyScheduler.getLogger().error("Exception: " + e.getMessage());
			throw new Exception(errorMsg + "\n" + e.getMessage(), e);
		}
	}

	/*public boolean getConnection(String username, String password) {
		try {
			
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		System.out.println("login successful");
		return true;
	}*/

	private void setConn(Connection conn) {
		this.conn = conn;
	}

	private ResultSet getRs() {
		return rs;
	}

	private void setRs(ResultSet rs) {
		this.rs = rs;
	}

	private Statement getStmt() {
		return stmt;
	}

	private void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public DbConnection() {

	}
	
	/**
	 * Return a list of areas from database
	 * @return
	 */
	public ArrayList<Pair<Integer, String>> getAreas() {
		ArrayList<Pair<Integer, String>> areaList = new ArrayList<Pair<Integer, String>>();
		
		String area = "";
		String query = "SELECT *\r\n" +
					   "FROM AM_AREA\r\n";
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				areaList.add(new Pair(rs.getString(1), rs.getString(2)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
		}
		
		return areaList;
	}
	
	/**
	 * Return a list of products from database
	 * @return
	 */
	public ArrayList<String> getProducts() {
		ArrayList<String> productList = new ArrayList<String>();
		
		return productList;
	}
	
	/**
	 * add database results to local record pojos and return arraylist
	 * @return
	 */
	public ArrayList<Asset> populateAssets() {
		ArrayList<Asset> list = new ArrayList<Asset>();
		Asset i = null;
		String query = "select AUTO_ASSET_ID, ASSET_NAME, STATUS_ID_FK, END_DATE\r\n" + 
				"from AM_AUTO_ASSET\r\n" + 
				"where AUTO_ASSET_ID in (6377, 6383, 6384, 6385, 6388)\r\n" + 
				"order by ASSET_NAME";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				i = new Asset(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4));
				list.add(i);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
		}
		
		return list;
	}
	
	public void updateRows(String update) {
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(update);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try { if (stmt != null) stmt.close(); } catch (SQLException e) {};
			try { if (rs != null) rs.close(); } catch (SQLException e) {};
		}
	}
	
	public void connectionClose() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
