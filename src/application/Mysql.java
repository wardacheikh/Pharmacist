package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {

	public static  Connection cn=null ;
	public static Connection getConection() {
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			 cn =DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacie","root","");
			 System.out.println("la conx est reussite");
			return cn ;

		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}


	}




}
