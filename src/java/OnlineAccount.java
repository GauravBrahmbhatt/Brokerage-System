
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GB
 */
@ManagedBean
@SessionScoped
public class OnlineAccount implements Serializable{
    
    
    public static String ID;
    public ArrayList<completedTransactions> orders;

    public static String getID() {
        return ID;
    }

    public static void setID(String ID) {
        OnlineAccount.ID = ID;
    }

    public ArrayList<completedTransactions> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<completedTransactions> orders) {
        this.orders = orders;
    }
    

    public String displayOrders() {
      
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/brokeragesystem";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        
        
        orders = new ArrayList<completedTransactions>();
        try {
            conn = DriverManager.getConnection(DB_URL, "root", "admin123");
            stat = conn.createStatement();
            rs = stat.executeQuery("Select * from (Select * from completedTransactions where loginId = '" + ID + "' order by TransactionID desc) TB limit 5");
            
                
            while (rs.next()) {
                orders.add(new completedTransactions(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5), rs.getString(6)));
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null)
                {
                rs.close();
                }
                if(stat != null)
                {
                stat.close();
                }
                if(conn != null)
                {
                conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            return "DisplayOrder.xhtml";
    }
    
    
}
