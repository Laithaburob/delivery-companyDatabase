package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;


public class DataBase_Tools {
	private static String Driver ="com.mysql.jdbc.Driver";
	private static String Hostname = "jdbc:mysql://localhost:" ;
	private static String port ="3306";
	private static String DataBase_Name = "";
	private static String username ="";
	private static String password = "";
	public static void setConnection(String username ,String password) {
		DataBase_Tools.username = username;
		DataBase_Tools.password = password;
	}
	public static Connection getConnection()  {		
		try {
			Class.forName(Driver);
			Connection conn = DriverManager.getConnection(Hostname+port+"/"+DataBase_Name,username,password);
			
			return conn;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return null;
	}
	public static void setDataBase_Name(String name) {
		DataBase_Name = name;
	}
	public static String getDataBase_Name() {
		return DataBase_Name;
	}
	public static String getUsername() {
		return username;
	}
	public static boolean Database_is_exists() throws Exception {
		Connection conn = DataBase_Tools.getConnection();
		PreparedStatement ps = conn.prepareStatement("SHOW DATABASES LIKE \"Company\";");
		ResultSet rs = ps.executeQuery();
		
		if (rs.next() == true) {
			return true;
		}
		return false;
	}
	
}
