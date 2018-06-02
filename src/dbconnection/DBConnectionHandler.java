package dbconnection;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
 
 
public class DBConnectionHandler {
 
    Connection con = null;
 
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");//Mysql Connection
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        	// sms code starts
        	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthbot","root","123");      	
            //con= DriverManager.getConnection("jdbc:mysql://localhost:3306/healthbot?" +"user=admin&password=admin");
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthbot", "admin", "admin");//mysql database
         } catch (SQLException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}