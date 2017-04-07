
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GB
 */
public class PasswordRetrieval {
    
     public static String[] passRetrieval(String loginID) 
    {
        final String DB_URL = ""jdbc:mysql://localhost:3306/brokeragesystem";
        //Random generator
        Random item = new Random();
        int quesId = item.nextInt(2) + 1;
        String quesCol = "";
        String ansCol = "";
        
        if (quesId == 1) {
                quesCol = "Question1";
                ansCol = "Answer1";
        } else 
            {
                quesCol = "Question2";
                ansCol = "Answer2";
            }
       
        String query = "Select " + quesCol + "," + ansCol + " from useraccount where loginID ='" + loginID + "'";
       
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        String[] arr = new String[2];
        //check for question and answer column
        try 
        {
           conn = DriverManager.getConnection(DB_URL,"root", "admin123");
           stat = conn.createStatement();
           rs = stat.executeQuery(query);
          
            if(rs.next())
            {
                arr[0] = rs.getString(1);
                arr[1] = rs.getString(2);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try{}
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return arr;
        }
    
}
