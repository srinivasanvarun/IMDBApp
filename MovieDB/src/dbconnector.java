import java.sql.*;

public class dbconnector {
    public static Connection getConnection()
    {
        Connection connection = null;
        System.out.println("-------- Oracle JDBC Connection --------");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found!");
            e.printStackTrace();
            return null;
        }
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:orcldb", "Scott", "tiger");
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return null;
        }
        if (connection != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Unable to make connection!");
        }
        return connection;	
    }	 
}