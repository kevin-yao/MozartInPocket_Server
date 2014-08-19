package mozartinpocket.database;

import java.sql.*;

public class DbConnection {
    static String oracleURL = "jdbc:mysql://localhost:3306/test";
	static String username = "root";
	static String password = "Yao88927";
	public static Connection getConnection(){
		Connection con=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con= DriverManager.getConnection(oracleURL,username, password);
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Fail to connect");
		}
		return con;
	}
	
}

