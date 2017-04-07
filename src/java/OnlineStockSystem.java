
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

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
public class OnlineStockSystem {
    
     public void signUp() {

        Scanner input = new Scanner(System.in);
        int ssn;
        String name;
        String loginId;
        String pwd;
        String question1 = "Which was your first school?";
        String question2 = "Which city were you born in?";
        String answer1 = "";
        String answer2 = "";
        String decision;
        double deposit;

        System.out.println("Please enter your SSN: ");
        ssn = input.nextInt();

        System.out.println("Please enter your full name: ");
        name = input.next();

        System.out.println("Please enter your Login ID: ");
        loginId = input.next();

        System.out.println("Please enter your password: ");
        pwd = input.next();

        System.out.println(question1);
        answer1 = input.next();

        System.out.println(question2);
        answer2 = input.next();

        System.out.println("Do you want to deposit initial amount? ");
        System.out.println("1. Yes");
        System.out.println("2. No");

        decision = input.next();

        if (decision.equals("1")) {
            System.out.println("Enter the deposit amount: ");
            deposit = input.nextDouble();
        } else {
            deposit = 0.0;
        }

        String sqlQuery = "Select newAccNo from NewAccountNo";
        int newAccNo = Database.QueryReturnsOneInt(sqlQuery);

        String sql = "insert into UserAccount values (" + newAccNo + "," + ssn + ",'" + name + "', '" + loginId + "', '" + pwd + "'," + deposit + ", false, '" + question1 + "','" + answer1 + "','" + question2 + "','" + answer2 + "')";
        if (Database.InsertUpdate(sql)) {
            newAccNo++;
            sql = "update newaccountno set NewAccNo = " + newAccNo;
            Database.InsertUpdate(sql);
            System.out.println("Account Created Successfully");
        } else {
            System.out.println("Account creation FAILED.Please try again.");
        }
    }

    public static void login() {
        Scanner input = new Scanner(System.in);

        String loginId = "";
        String pwd = "";
        boolean isWrongPassword = false;
        

        System.out.println("Please enter Login ID: ");
        loginId = input.next();

        

        String sql = "Select 1 from useraccount where LoginId = '" + loginId + "'";
        
        while(!Database.IsRowPresent(sql))
        {
            System.out.println("Invalid LoginID!");
            System.out.println("Please enter Login ID: ");
            loginId = input.next();
            sql = "Select 1 from useraccount where LoginId = '" + loginId + "'";
        
        }
        
        sql = "Select lockedout from useraccount where loginId='" + loginId + "'";
            
        if(Database.QueryReturnsOneInt(sql) == 1)
        {
            PasswordRetrieval();
            return;
        }
        
        System.out.println("Please enter your password: ");
        pwd = input.next();
        sql = "Select 1 from useraccount where LoginId = '" + loginId + "' and Password = '" + pwd + "'";
        while(!Database.IsRowPresent(sql))
        {
            System.out.println("Invalid Password");
            if(isWrongPassword)
            {
                sql = "update useraccount set lockedout = 1  where LoginID = '" + loginId + "'";
                Database.InsertUpdate(sql);
                PasswordRetrieval();
                return;
            }
            else
            {
                isWrongPassword = true;
                System.out.print("Please enter the valid password: ");
                pwd = input.next();
                sql = "Select 1 from useraccount where LoginId = '" + loginId + "' and Password = '" + pwd + "'";
            }
        }
        
        OnlineAccount.setID(loginId);
      
       
        
            
    }

    public static void PasswordRetrieval() {
        System.out.println("\n-----Password Retrieval-----\n");
        String loginId = "";
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your LoginID: ");
        loginId = input.next();
        String[] arr = new String[2];

        String sql = "Select loginID from useraccount where loginId ='" + loginId + "'";

        if (Database.IsRowPresent(sql)) {
            arr = PasswordRetrieval.passRetrieval(loginId);
            System.out.println(arr[0]);
            String answer = input.next();

            if (arr[1].equals(answer)) {
                sql = "Select password from useraccount where loginId ='" + loginId + "'";
                String retPass = Database.QueryReturnsOneString(sql);
                System.out.println("Your Password is: " + retPass);
                sql = "update useraccount set lockedout = 0 where loginID = '" + loginId + "'";
                Database.InsertUpdate(sql);

            }
        } else {
            System.out.println("Your login Id is invalid!");
        }

    }

    
}
