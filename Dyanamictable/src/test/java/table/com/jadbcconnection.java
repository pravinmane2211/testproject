package table.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jadbcconnection {
	static Connection con;
	public static Connection commcon() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin","root","root");
		
		return con;
		
	}
	
	
	
	
	

}
