package szeljic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

public class DBConnection {
	
	private static Connection connection = null;
	
	public static Connection openConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:test.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		createCommandTable();
		
		System.out.println("Opened database successfully");
		
		return connection;
	}
	
	protected static void createCommandTable() {
		
		try {
			if(connection != null && !connection.isClosed()) {
				
				Statement stmt = null;
				stmt = connection.createStatement();
				
				String query = "CREATE TABLE IF NOT EXISTS COMMAND " +
								"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
								"NAME TEXT NOT NULL, " +
								"COMMAND TEXT NOT NULL, " +
								"CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_STAMP, " +
								"UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_STAMP)";
				
				stmt.executeUpdate(query);
				stmt.close();
			} else {
				openConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//SELECT * FROM table_name WHERE column1 = value1 AND column2 = value2 AND ...
	public static ResultSet select(String tableName, ArrayList<String> listOfColumns, ArrayList<String> listOfValues) throws Exception {
		
		openConnection();
		
		String query = "SELECT * FROM " + tableName + " WHERE ";
		
		if(listOfColumns.size() != listOfValues.size()) {
			throw new Exception("Columns don't match values!");
		}
		
		Statement stmt1 = connection.createStatement();
		ResultSet resultSet = stmt1.executeQuery("SELECT * FROM " + tableName + " WHERE 1 = 2");
		ResultSetMetaData metaData = resultSet.getMetaData();
		
		boolean hasColumnName;
		HashMap<String, Integer> metaDataMap = new HashMap<>();
		for(int i = 0; i < listOfColumns.size(); i++) {
			
			listOfColumns.set(i, listOfColumns.get(i).toUpperCase());
			
			hasColumnName = false;
			for(int j = 1; j <= metaData.getColumnCount(); j++) {
				if(listOfColumns.get(i).equalsIgnoreCase(metaData.getColumnName(j))) {
					hasColumnName = true;
					metaDataMap.put(metaData.getColumnName(j).toUpperCase(), metaData.getColumnType(j));
					System.out.println(metaDataMap);
				}
			}
			
			if(!hasColumnName) {
				throw new Exception("Not correct column name of table!");
			}
			
			switch (metaDataMap.get(listOfColumns.get(i))) {
			
			case Types.INTEGER: case Types.DECIMAL: case Types.NUMERIC: case Types.BIGINT: case Types.TINYINT: case Types.SMALLINT: case Types.BIT:
			case Types.DOUBLE: case Types.FLOAT: case Types.REAL:
				query += listOfColumns.get(i) + " = " + Integer.parseInt(listOfValues.get(i));
				if(i < listOfValues.size() - 1) {
					query += " AND ";
				}
				break;

			case Types.CHAR: case Types.LONGNVARCHAR: case Types.LONGVARCHAR: case Types.NCHAR: case Types.NVARCHAR: case Types.VARCHAR:
				query += listOfColumns.get(i) + " = " + "'" + listOfValues.get(i) + "'";
				if(i < listOfValues.size() - 1) {
					query += " AND ";
				}
				break;
				
			case Types.TIMESTAMP: case Types.TIMESTAMP_WITH_TIMEZONE:
				query += listOfColumns.get(i) + " = " + "'" + listOfValues.get(i) + "'";
				if(i < listOfValues.size() - 1) {
					query += " AND ";
				}
				break;
				
			case Types.DATE:
				query += listOfColumns.get(i) + " = " + "'" + listOfValues.get(i) + "')";
				if(i < listOfValues.size() - 1) {
					query += " AND ";
				}
				break;
				
			case Types.TIME: case Types.TIME_WITH_TIMEZONE:
				query += listOfColumns.get(i) + " = " + "'" + listOfValues.get(i) + "'";
				if(i < listOfValues.size() - 1) {
					query += " AND ";
				}
				break;
			
			default:
				throw new Exception("Unknow data type!");
			}
			
		}
		System.out.println(metaDataMap);
		System.out.println(query);
		
		Statement stmt2 = connection.createStatement();
		
		ResultSet resultSet2 = stmt2.executeQuery(query);
		
		return resultSet2;
	}
	
	//INSERT INTO table_name (column1, column2, ...) VALUES(value1, value2, ...)
	public static boolean insert(String tableName, ArrayList<String> listOfColumns, ArrayList<String> listOfValues) throws Exception {
		
		openConnection();
		
		if(listOfColumns.size() != listOfValues.size()) {
			throw new Exception("Columns don't match values!");
		}
		
		//get all columns names
		Statement stmt1 = connection.createStatement();
		ResultSet resultSet = stmt1.executeQuery("SELECT * FROM " + tableName + " WHERE 1 = 2");
		
		ResultSetMetaData metaData = resultSet.getMetaData();
		
		System.out.println(metaData.getColumnCount());
		
		String query = "INSERT INTO " + tableName + "(";
		
		boolean hasColumnName;
		
		HashMap<String, Integer> metaDataMap = new HashMap<>();
		
		for (int i = 0; i < listOfColumns.size(); i++) {
			
			listOfColumns.set(i, listOfColumns.get(i).toUpperCase());
			
			hasColumnName = false;
			for(int j = 1; j <= metaData.getColumnCount(); j++) {
				if(listOfColumns.get(i).equalsIgnoreCase(metaData.getColumnName(j))) {
					hasColumnName = true;
					metaDataMap.put(metaData.getColumnName(j).toUpperCase(), metaData.getColumnType(j));
					System.out.println(metaDataMap);
				}
			}
			
			if(!hasColumnName) {
				throw new Exception("Not correct column name of table!");
			}
			
			if(i < listOfColumns.size() - 1) {
				query += listOfColumns.get(i) + ", ";
			} else {
				query += listOfColumns.get(i);
			}
			
		}
		
		query += ") VALUES(";
		
		for (int i = 0; i < listOfValues.size(); i++) {
				
				switch (metaDataMap.get(listOfColumns.get(i))) {
				
				case Types.INTEGER: case Types.DECIMAL: case Types.NUMERIC: case Types.BIGINT: case Types.TINYINT: case Types.SMALLINT: case Types.BIT:
				case Types.DOUBLE: case Types.FLOAT: case Types.REAL:
					query += Integer.parseInt(listOfValues.get(i));
					if(i < listOfValues.size() - 1) {
						query += ", ";
					}
					break;

				case Types.CHAR: case Types.LONGNVARCHAR: case Types.LONGVARCHAR: case Types.NCHAR: case Types.NVARCHAR: case Types.VARCHAR:
					query += "'" + listOfValues.get(i) + "'";
					if(i < listOfValues.size() - 1) {
						query += ", ";
					}
					break;
					
				case Types.TIMESTAMP: case Types.TIMESTAMP_WITH_TIMEZONE:
					query += "datetime('" + listOfValues.get(i) + "', 'localtime')";
					if(i < listOfValues.size() - 1) {
						query += ", ";
					}
					break;
					
				case Types.DATE:
					query += "datetime('" + listOfValues.get(i) + "', 'localtime')";
					if(i < listOfValues.size() - 1) {
						query += ", ";
					}
					break;
					
				case Types.TIME: case Types.TIME_WITH_TIMEZONE:
					query += "time('" + listOfValues.get(i) + "', 'localtime')";
					if(i < listOfValues.size() - 1) {
						query += ", ";
					}
					break;
				
				default:
					throw new Exception("Unknow data type!");
				}
				
		}
		
		query += ")";
		System.out.println(query);
		Statement stmt = connection.createStatement();
		
		stmt.executeUpdate(query);
		
		closeConnection();
		
		return true;
	}
	
	//UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE ID = id
	public static boolean update(String tableName, ArrayList<String> listOfColumns, ArrayList<String> listOfValues, int id) throws Exception {
		
		String query = "UPDATE " + tableName + " SET ";
		
		if(listOfColumns.size() != listOfValues.size()) {
			throw new Exception("Columns don't match values!");
		}
		
		//get all columns names
		Statement stmt1 = connection.createStatement();
		ResultSet resultSet = stmt1.executeQuery("SELECT * FROM " + tableName + " WHERE 1 = 2");
		
		ResultSetMetaData metaData = resultSet.getMetaData();
		
		System.out.println(metaData.getColumnCount());
		
		boolean hasColumnName;
		
		HashMap<String, Integer> metaDataMap = new HashMap<>();
		
		for (int i = 0; i < listOfColumns.size(); i++) {
			

			listOfColumns.set(i, listOfColumns.get(i).toUpperCase());
			
			hasColumnName = false;
			for(int j = 1; j <= metaData.getColumnCount(); j++) {
				if(listOfColumns.get(i).equalsIgnoreCase(metaData.getColumnName(j))) {
					hasColumnName = true;
					metaDataMap.put(metaData.getColumnName(j).toUpperCase(), metaData.getColumnType(j));
					System.out.println(metaDataMap);
				}
			}
			
			if(!hasColumnName) {
				throw new Exception("Not correct column name of table!");
			}
			
			switch (metaDataMap.get(listOfColumns.get(i))) {
			
			case Types.INTEGER: case Types.DECIMAL: case Types.NUMERIC: case Types.BIGINT: case Types.TINYINT: case Types.SMALLINT: case Types.BIT:
			case Types.DOUBLE: case Types.FLOAT: case Types.REAL:
				query += listOfColumns.get(i) + " = " + Integer.parseInt(listOfValues.get(i));
				if(i < listOfValues.size() - 1) {
					query += ", ";
				}
				break;

			case Types.CHAR: case Types.LONGNVARCHAR: case Types.LONGVARCHAR: case Types.NCHAR: case Types.NVARCHAR: case Types.VARCHAR:
				query += listOfColumns.get(i) + " = " + "'" + listOfValues.get(i) + "'";
				if(i < listOfValues.size() - 1) {
					query += ", ";
				}
				break;
				
			case Types.TIMESTAMP: case Types.TIMESTAMP_WITH_TIMEZONE:
				query += listOfColumns.get(i) + " = " + "'" + listOfValues.get(i) + "'";
				if(i < listOfValues.size() - 1) {
					query += ", ";
				}
				break;
				
			case Types.DATE:
				query += listOfColumns.get(i) + " = " + "'" + listOfValues.get(i) + "')";
				if(i < listOfValues.size() - 1) {
					query += ", ";
				}
				break;
				
			case Types.TIME: case Types.TIME_WITH_TIMEZONE:
				query += listOfColumns.get(i) + " = " + "'" + listOfValues.get(i) + "'";
				if(i < listOfValues.size() - 1) {
					query += ", ";
				}
				break;
			
			default:
				throw new Exception("Unknow data type!");
			}
			
		}
		
		query += " WHERE ID =" + id;
		
		Statement stmt2 = connection.createStatement();
		stmt2.executeQuery(query);
		
		return true;
	}
	
	//SELECT * FROM table_name
	public static ResultSet selectAll(String tableName) {
		
		openConnection();
		
		String query = "SELECT * FROM " + tableName;
		System.out.println(query);
		
		Statement stmt;
		ResultSet resultSet = null;
		try {
			stmt = connection.createStatement();
			resultSet = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		closeConnection();
		
		System.out.println(resultSet);
		
		return resultSet;
	}
	
	//SELECT * FROM table_name WHERE id = value
	public static ResultSet selectById(String tableName, int id) {
		openConnection();
		
		String query = "SELECT * FROM " + tableName + " WHERE ID = " + id;
		
		Statement stmt;
		ResultSet resultSet = null;
		try {
			stmt = connection.createStatement();
			resultSet = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		closeConnection();
		
		return resultSet;
	}
	
	//DELETE * FROM table WHERE id = value
	public static boolean remove(String tableName, int id) {
		
		openConnection();
		
		String query = "DELETE FROM " + tableName + " WHERE ID = " + id;
		Statement stmt = null;
		
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
