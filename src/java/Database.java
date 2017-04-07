
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GB
 */
public class Database {
    
     private static final String DB_URL = "jdbc:mysql://localhost:3306/brokeragesystem";
     private static final String userName = "root";
     private static final String password = "admin123";
    
    
    public static boolean IsRowPresent(String query){ 
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        boolean isRow = false;
        try
        {
            conn = DriverManager.getConnection(DB_URL, userName, password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            if(rs.next())
            {
                isRow = true;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try{}
            catch(Exception e)
            {
                e.printStackTrace();;
            }
        }
        return isRow;
    }
    
    public static boolean InsertUpdate (String query){
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        boolean isSuccess = false;
       
        try{
            
            conn = DriverManager.getConnection(DB_URL, userName, password);
            stat = conn.createStatement();
            stat.executeUpdate(query);
            
            isSuccess = true;
          }
        catch(SQLException e)
        {
            e.printStackTrace();
                   
        }
        finally
        {
            try
            {
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }
    
    public static int QueryReturnsOneInt(String query)
    {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        int input = -1;
       
        try{
            
            conn = DriverManager.getConnection(DB_URL, userName, password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            if(rs.next()){
            input = rs.getInt(1);
            }
            
          }
        catch(SQLException e)
        {
            e.printStackTrace();
                   
        }
        finally
        {
            try
            {
               if(conn != null){
                   conn.close();
               } 
               if(stat != null){
                   conn.close();
               } 
               if(rs != null){
                   conn.close();
               } 
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return input;
       
    }
    
     public static String QueryReturnsOneString(String query)
    {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        String input = "s";
       
        try{
            
            conn = DriverManager.getConnection(DB_URL, userName, password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            if(rs.next())
            {
            input = rs.getString(1);
            }
          }
        catch(SQLException e)
        {
            e.printStackTrace();
                   
        }
        finally
        {
            try
            {
               if(conn != null){
                   conn.close();
               } 
               if(stat != null){
                   conn.close();
               } 
               if(rs != null){
                   conn.close();
               } 
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return input;
       
    }
     
      public static double QueryReturnsDouble(String query)
    {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        double input = -1;
       
        try{
            
            conn = DriverManager.getConnection(DB_URL, userName, password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            if(rs.next()){
            input = rs.getDouble(1);
            }
            
          }
        catch(SQLException e)
        {
            e.printStackTrace();
                   
        }
        finally
        {
            try
            {
               if(conn != null){
                   conn.close();
               } 
               if(stat != null){
                   conn.close();
               } 
               if(rs != null){
                   conn.close();
               } 
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return input;
       
    }
     
     public static ArrayList<Integer> SelectQuery_ArrayListInteger(String query)
     {
         
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        ArrayList<Integer> input = new ArrayList<>();
       
        try{
            
            conn = DriverManager.getConnection(DB_URL, userName, password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            while(rs.next())
            {
                input.add(rs.getInt(1)) ;
            }
          }
        catch(SQLException e)
        {
            e.printStackTrace();
                   
        }
        finally
        {
            try
            {
               if(conn != null){
                   conn.close();
               } 
               if(stat != null){
                   conn.close();
               } 
               if(rs != null){
                   conn.close();
               } 
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return input;
     }
     
      public static ArrayList<String> SelectQuery_ArrayListString(String query)
     {
         
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        ArrayList<String> input = new ArrayList<>();
       
        try{
            
            conn = DriverManager.getConnection(DB_URL, userName, password);
            stat = conn.createStatement();
            rs = stat.executeQuery(query);
            while(rs.next())
            {
                input.add(rs.getString(1)) ;
            }
          }
        catch(SQLException e)
        {
            e.printStackTrace();
                   
        }
        finally
        {
            try
            {
               if(conn != null){
                   conn.close();
               } 
               if(stat != null){
                   conn.close();
               } 
               if(rs != null){
                   conn.close();
               } 
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return input;
     }
     
     public static void driverLoad()
     {
          try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            System.out.println("Internal Error! Please try again later.");
        }
         
     }
    
}
