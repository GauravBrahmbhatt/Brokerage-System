/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GB
 */

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@SessionScoped
public class UserAccount implements Serializable{
    
    private int accNo;
    private int ssn;
    private String name;
    private String loginId;
    private double deposit;
    private String password;
    private String question1;
    private String question2;
    private String answer1;
    private String answer2;
    private ArrayList<String> questions = getAllQuestions();
    private String[] ques;
    private String errMsg = "";
    int counter = 0;
    private String answer;
    private String retrievedPassword;

    public int getAccNo() {
        return accNo;
    }

    public void setAccNo(int accNo) {
        this.accNo = accNo;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String[] getQues() {
        return ques;
    }

    public void setQues(String[] ques) {
        this.ques = ques;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRetrievedPassword() {
        return retrievedPassword;
    }

    public void setRetrievedPassword(String retrievedPassword) {
        this.retrievedPassword = retrievedPassword;
    }
    
    
    
    

    public String signUp() {

        Database.driverLoad();
        String sqlQuery = "Select newAccNo from NewAccountNo";
        int newAccNo = Database.QueryReturnsOneInt(sqlQuery);

        String sql = "insert into UserAccount values (" + newAccNo + "," + ssn + ",'" + name + "', '" + loginId + "', '" + password + "'," + deposit + ", false, '"+ question1 + "','" + answer1 + "','" + question2 + "','" + answer2 + "')";
        if (Database.InsertUpdate(sql)) {
            newAccNo++;
            sql = "update newaccountno set NewAccNo = " + newAccNo;
            Database.InsertUpdate(sql);
            System.out.println("Account Created Successfully");
        } else {
            System.out.println("Account creation FAILED.Please try again.");
        }
        return "confirmationreg.xhtml";
    }
    
    public ArrayList getAllQuestions()
    {
        Database.driverLoad();
       String sql = "Select question from questions";
       return Database.SelectQuery_ArrayListString(sql);
    }
    
   
    
    public String login() {
       
        boolean isWrongPassword = false;
        String page = "";

        String sql = "Select 1 from useraccount where LoginId = '" + loginId + "'";
        
        if(!Database.IsRowPresent(sql))
        {
            errMsg = "Invalid Login ID. Please enter the correct Login ID.";
            page = "login";
        }
        if(Database.IsRowPresent(sql))
                {
                     sql = "Select lockedout from useraccount where loginId='" + loginId + "'";
            
                    if(Database.QueryReturnsOneInt(sql) == 1)
                    {
                        PasswordRetrieval();
                        page = "passwordretrieval";
                    }
                    else
        {
            sql = "Select 1 from useraccount where LoginId = '" + loginId + "' and Password = '" + password + "'";
            if(!Database.IsRowPresent(sql))
            {
                if(counter!= 2){
                    counter++;
                    errMsg = "Password Incorrect. Please enter the correct password.";
                    page = "login";
                }
                else{
                    counter = 0;
                    sql = "update useraccount set lockedout = 1  where LoginID = '" + loginId + "'";
                    Database.InsertUpdate(sql);
                    PasswordRetrieval();
                    page = "passwordretrieval";
                }
            }
            else
            { 
                sql = "Select 1 from useraccount where LoginId = '" + loginId + "' and Password = '" + password + "'";
                page = "StockSystem";
               
            }
        }

                }
       
          OnlineAccount.setID(loginId);

          return page;
            
    }
      
      
    public void PasswordRetrieval() {
        System.out.println("\n-----Password Retrieval-----\n");
       
        ques = new String[2];

        String sql = "Select loginID from useraccount where loginId ='" + loginId + "'";

        if (Database.IsRowPresent(sql)) {
            ques = PasswordRetrieval.passRetrieval(loginId);

        } else {
            System.out.println("Your login Id is invalid!");
        }

    }
    
    public String changePassword(){
        if (ques[1].equals(answer)) {
                String sql = "Select password from useraccount where loginId ='" + loginId + "'";
                String retPass = Database.QueryReturnsOneString(sql);
                System.out.println("Your Password is: " + retPass);
                sql = "update useraccount set lockedout = 0 where loginID = '" + loginId + "'";
                Database.InsertUpdate(sql);
                retrievedPassword = "Your password is "+ retPass;
            }
        return "passwordretrieval";
    }
    
    public String logout(){
        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        return "index";
    }
    
    public String backToLogin(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        return "login";
    }
     public String backToTrade(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        return "Trade";
    }
      public String backToStockSystem(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        return "StockSystem";
    }
}
