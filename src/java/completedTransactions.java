/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author GB
 */
@ManagedBean
@Named(value = "completedTransactions")
@RequestScoped
public class completedTransactions {

    /**
     * Creates a new instance of completedTransactions
     */
    private int TransactionID;
    private String LoginID;
    private String Symbol;
    private int StockQty;
    private double ProposedAmt;
    private String TradeType;

    public int getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(int TransactionID) {
        this.TransactionID = TransactionID;
    }

    public String getLoginID() {
        return LoginID;
    }

    public void setLoginID(String LoginID) {
        this.LoginID = LoginID;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String Symbol) {
        this.Symbol = Symbol;
    }

    public int getStockQty() {
        return StockQty;
    }

    public void setStockQty(int StockQty) {
        this.StockQty = StockQty;
    }

    public double getProposedAmt() {
        return ProposedAmt;
    }

    public void setProposedAmt(double ProposedAmt) {
        this.ProposedAmt = ProposedAmt;
    }

   
    public String getTradeType() {
        return TradeType;
    }

    public void setTradeType(String TradeType) {
        this.TradeType = TradeType;
    }

        
    public completedTransactions() {
        
    }

    public completedTransactions(int TransactionID, String LoginID, String Symbol, int StockQty, double ProposedAmt, String TradeType) {
        this.TransactionID = TransactionID;
        this.LoginID = LoginID;
        this.Symbol = Symbol;
        this.StockQty = StockQty;
        this.ProposedAmt = ProposedAmt;
        this.TradeType = TradeType;
    }
    
    
}
