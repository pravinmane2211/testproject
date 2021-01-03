package table.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MysqlCon{  
	public static void main(String args[]){  
		try{  
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin","root","root");  
		//here sonoo is database name, root is username and password  
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select * from emplogin");  
		while(rs.next())  
		System.out.println(rs.getString(1)+"  "+rs.getString(2));  
		con.close();  
		}catch(Exception e){ System.out.println(e);}  
		}  
		}  
