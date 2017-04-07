
import java.util.ArrayList;
import java.util.Scanner;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
@RequestScoped
public class Stocks {
    private String Symbol;
    private String LoginId;
    private int StockQuantity;
    private String TradeType;
    private double Proposedamt;

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String Symbol) {
        this.Symbol = Symbol;
    }

    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String LoginId) {
        this.LoginId = LoginId;
    }

    public int getStockQuantity() {
        return StockQuantity;
    }

    public void setStockQuantity(int StockQuantity) {
        this.StockQuantity = StockQuantity;
    }

    public String getTradeType() {
        return TradeType;
    }

    public void setTradeType(String TradeType) {
        this.TradeType = TradeType;
    }

    public double getProposedamt() {
        return Proposedamt;
    }

    public void setProposedamt(double Proposedamt) {
        this.Proposedamt = Proposedamt;
    }

    public Stocks(String Symbol, String LoginId, int StockQuantity, String TradeType, double Proposedamt) {
        this.Symbol = Symbol;
        this.LoginId = LoginId;
        this.StockQuantity = StockQuantity;
        this.TradeType = TradeType;
        this.Proposedamt = Proposedamt;
    }

    public Stocks() {
    }
    
    
    
    
     public String BuyStock()
    {   
       
        ArrayList<Integer> TranIds = new ArrayList<Integer> ();
         
        String sql = "Select TransactionId from PendingTransactions where TradeType = 'sell' and Symbol = '" + Symbol + "'";
        
       
            sql += " and Proposedamt <= "+ Proposedamt;
        
        
            sql += " order by Proposedamt asc";
                
            TranIds = Database.SelectQuery_ArrayListInteger(sql);
            
            int quantityProcessed = 0;
            if(TranIds.size() > 0)
            {
                
                for(Integer transaction : TranIds )
                {
                    if(quantityProcessed < StockQuantity)
                    {
                        sql = "Select StockQuantity from PendingTransactions where TransactionID =" + transaction + " and Symbol = '"+ Symbol +"'";

                        int availableQuantity = Database.QueryReturnsOneInt(sql);
                        int bought = 0;

                        if(availableQuantity > StockQuantity)
                        {
                            int diff = availableQuantity - StockQuantity;
                            sql = "Update pendingtransactions set stockQuantity =" + diff + " where transactionId = " + transaction;
                            Database.InsertUpdate(sql);
                          
                            bought = StockQuantity;
                            System.out.println("*** You have successfully bought "+ StockQuantity + " stocks! ***");
                        }
                        else if(availableQuantity < StockQuantity)
                        {
                            int diff = StockQuantity - availableQuantity;
                            sql = "delete from pendingtransactions where TransactionID = " + transaction + " and Symbol = '" + Symbol +"'" ;
                            Database.InsertUpdate(sql);
                            
                           
                            bought = availableQuantity;
                        
                        }   
                        else if(availableQuantity == StockQuantity)
                        {
                          sql = "delete from pendingtransactions where TransactionID =  " + transaction + " and Symbol = '" + Symbol + "'" ;
                          Database.InsertUpdate(sql);
                          bought = StockQuantity;
                        
                        }

                        sql = "insert into completedtransactions(LoginID, Symbol, StockQuantity, ProposedAmt, TradeType) values('"+ OnlineAccount.getID() +"','"+ Symbol +"',"+ bought+"," + Proposedamt + ",'buy')";
                        Database.InsertUpdate(sql);
                        
                        quantityProcessed += bought;
                     }
                    else
                        break;
                }
                
                if(StockQuantity > quantityProcessed)
                 {
                   int difference = StockQuantity - quantityProcessed;  
                   sql = "insert into Pendingtransactions(LoginID, Symbol, StockQuantity, TradeType, ProposedAmt) values('"+ OnlineAccount.getID() +"','"+ Symbol +"',"+ difference +",'buy'," + Proposedamt + ")";
                   Database.InsertUpdate(sql);  
                 }
            }
            else
            {
                sql = "insert into Pendingtransactions(LoginID, Symbol, StockQuantity,TradeType, ProposedAmt) values('"+ OnlineAccount.getID() +"','"+ Symbol +"',"+ StockQuantity +",'buy'," + Proposedamt + ")";
                Database.InsertUpdate(sql);
              
               return "TradeNotCompleted";
            }
        return "TradeCompleted";
    }
    
     
    public String SellStock()
    {
        
        ArrayList<Integer> TranIds = new ArrayList<Integer> ();
       
        
         
        String sql = "Select TransactionId from PendingTransactions where TradeType = 'buy' and Symbol = '" + Symbol + "'";
        
       
            sql += " and Proposedamt >= "+ Proposedamt;
        
        
        sql += " order by Proposedamt desc";
        
        TranIds = Database.SelectQuery_ArrayListInteger(sql);
        
         if(TranIds.size() > 0)
            {
                int soldProcessed = 0;
                 for(Integer transaction : TranIds )
                 {
                    if(soldProcessed < StockQuantity)
                    {
                    sql = "Select StockQuantity from PendingTransactions where TransactionID = " + transaction + " and Symbol = '" + Symbol +"'"; 
                    int availableBuyersQuantity = Database.QueryReturnsOneInt(sql);
                    
                     int sold = 0;
                     if(availableBuyersQuantity > StockQuantity)
                     {  
                        int diff = availableBuyersQuantity - StockQuantity;
                        
                        sql = "Update pendingtransactions set stockQuantity =" + diff + " where transactionId= " + transaction;
                        Database.InsertUpdate(sql);
                        sold = StockQuantity;
                        
                        System.out.println("*** You have successfully sold "+ StockQuantity + " stocks! ***");
                       
                      }
                     
                     else if(availableBuyersQuantity < StockQuantity)
                     {
                         int diff = StockQuantity - availableBuyersQuantity;
                         
                        sql = "delete from pendingtransactions where TransactionID =  " + transaction + " and Symbol = '"+ Symbol +"'";
                        Database.InsertUpdate(sql);
                        
                        
                        sold = availableBuyersQuantity;
                        
                        System.out.println("*** You have successfully sold " + sold + " stocks! The remaining stocks order has been added to pending orders. ***");
                        //StockUpdate(Symbol);
                        
                     }
                     else if(availableBuyersQuantity == StockQuantity)
                        {
                          sql = "delete from pendingtransactions where TransactionID =  " + transaction ;
                          Database.InsertUpdate(sql);
                          sold = StockQuantity;
                          System.out.println("*** You have successfully sold "+ availableBuyersQuantity + " stocks! You sold all the stocks to the buyer! ***");

                        }

                        sql = "insert into completedtransactions(LoginID, Symbol, StockQuantity, ProposedAmt, TradeType) values('"+ OnlineAccount.getID() +"','"+ Symbol +"',"+ sold+"," + Proposedamt + ",'sell')";
                        Database.InsertUpdate(sql);
                        
                        soldProcessed += sold; 
                        
                        
                     }
                        else
                            break;
                 }
                 
                 if(StockQuantity > soldProcessed)
                 {
                   int difference = StockQuantity - soldProcessed;  
                   sql = "insert into Pendingtransactions(LoginID, Symbol, StockQuantity, TradeType, ProposedAmt) values('"+ OnlineAccount.getID() +"','"+ Symbol +"',"+ difference +",'sell'," + Proposedamt + ")";
                   Database.InsertUpdate(sql);  
                 }
             
                    
                    
                 }
         else
            {
                sql = "insert into Pendingtransactions(LoginID, Symbol, StockQuantity,TradeType, ProposedAmt) values('"+ OnlineAccount.getID() +"','"+ Symbol +"',"+ StockQuantity +",'sell'," + Proposedamt + ")";
                Database.InsertUpdate(sql);
                
                return "TradeNotCompleted";
            }
                return "TradeCompleted";
            }
    
}
