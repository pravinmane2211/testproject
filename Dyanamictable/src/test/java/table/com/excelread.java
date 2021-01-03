package table.com;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelread {

	 

	public static void main(String[] args) throws IOException {

		//excelread ex = new excelread();
		//ex.excelsheet();
       // ex.conn();
		 int batchSize = 7;
		 Connection connection = null;

	//public void conn() {
		try {
			File file = new File("Book11.xlsx");
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getLastRowNum();
			 connection  = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "root");
			//Statement stmt = con.createStatement();
			connection.setAutoCommit(false);
			 String sql = "INSERT INTO users(username, password) VALUES (?, ?)";
	         PreparedStatement statement = connection.prepareStatement(sql);
	         int count = 0;
	       //  int rowCount = sheet.getLastRowNum();
	 		for (int i = 1; i < rowCount + 1; i++) {
				Row row = sheet.getRow(i);
				for (int j = i; j <=row.getLastCellNum(); j++) 
				{
				   String username = row.getCell(0).getStringCellValue();
				   System.out.println(username);
				   String password = row.getCell(1).getStringCellValue();
				   System.out.println(password);
                }
				
	              
	             if (count % batchSize == 0) 
	             {
	                 statement.executeBatch();
	             }     
	         }
	         
	         workbook.close();
	         statement.executeBatch();
	         
	         connection.commit();
	        // connection.close();
		  } catch (IOException ex1) {
	            System.out.println("Error reading file");
	            ex1.printStackTrace();
	        } catch (SQLException ex2) {
	            System.out.println("Database error");
	            ex2.printStackTrace();
	        }
	}
}
				
				
				
				
				
				
				
				
				//double rs = stmt.executeUpdate("insert into users(username,password) value('" + username + "','" + password + "')");
            //System.out.println("Data is inserted");
			//stmt.close();
			//con.close();
		//} catch (Exception e) {
		//}
	//}

	/*public void excelsheet() throws IOException {
		File file = new File("Book11.xlsx");
		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int rowCount = sheet.getLastRowNum();
		for (int i = 1; i < rowCount + 1; i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
            System.out.print(row.getCell(j).getStringCellValue() + "|| ");
            String st= row.toString();
            username=row.getCell(j).getStringCellValue() + "|| ";
            password=st.substring(1);
           
			}
			 excelread e1 =new excelread();
			 e1.conn();
			System.out.println("inserted");
			 
*/
	//	}

	//}
//}