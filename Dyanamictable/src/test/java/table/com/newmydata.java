package table.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class newmydata {
	public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
		  
	        int batchSize = 7;

	        Connection connection = null;
	        try {
		File file = new File("Book11.xlsx");
		FileInputStream fis = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		//int rowCount = sheet.getLastRowNum();
		 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "root");
		 connection.setAutoCommit(false);
		 String sql = "INSERT INTO emp(username, password) VALUES (?, ?)";
         PreparedStatement statement = connection.prepareStatement(sql);  
         int count = 0;
         rowIterator.next(); // skip the header row
         
         while (rowIterator.hasNext()) {
             Row nextRow = rowIterator.next();
             Iterator<Cell> cellIterator = nextRow.cellIterator();

             while (cellIterator.hasNext()) {
                 Cell nextCell = cellIterator.next();

                 int columnIndex = nextCell.getColumnIndex();

                 switch (columnIndex) {
                 case 0:
                     String username = nextCell.getStringCellValue();
                     statement.setString(1, username);
                     break;
                 case 1:
                     String password = nextCell.getStringCellValue();
                     statement.setString(2, password);
                     break;
                 }
             }
             
             statement.addBatch();
              
             if (count % batchSize == 0) {
                 statement.executeBatch();
                       }     
         }
         
         workbook.close();
         statement.executeBatch();
         
         connection.commit();
         connection.close();
        

	        } catch (IOException ex1) {
	            System.out.println("Error reading file");
	            ex1.printStackTrace();
	        } catch (SQLException ex2) {
	            System.out.println("Database error");
	            ex2.printStackTrace();
	        }
	}
	}
         
