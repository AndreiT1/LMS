
package libraryassistant.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Andrei
 */
public final class DatabaseHandler {
    private static DatabaseHandler handler ; 
    private static String DB_URL = "jdbc:mysql://localhost:3306/newDB?useSSL=false";
    private static Connection conn = null;
    private static Statement stmt = null;
    
    
    
    private DatabaseHandler(){
        createConnection() ;            
        }
    
     void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver") ;
            conn = DriverManager.getConnection(DB_URL,"root","admin");
        } catch (Exception e) {
            e.printStackTrace();
        }
     }
     
     public static DatabaseHandler getInstance()
    {
        if(handler==null)
        {
            handler = new DatabaseHandler();
        }
        return handler;
    }
     
     public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            
            System.out.println("Exception at execAction:dataHandler" + ex.getLocalizedMessage());
            return false;
        } 

    }

     public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {
        }
        return result;
    }
     public Connection getConnection(){
         return conn;
     }
}

