
package bankingapplication2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Bank {
    private String name;

    public Bank(String name) {
        this.name = name;
    }
    
    public void listAccount(){
        Connection connection = BankingConnection.connect();
        
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM account";
            ResultSet results = statement.executeQuery(sql);
            
            while (results.next()){
                System.out.println(results.getString(1)+" "+results.getString(2)
                +" "+results.getString(3));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void openAccount(int accountNumber, String accountName, double balance){
        Connection connection = BankingConnection.connect();
        String sql = "INSERT INTO account(accNumber, accName, accBalance)"
                    + "VALUES(?,?,?)";
  
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountNumber);
            preparedStatement.setString(2, accountName);
            preparedStatement.setDouble(3, balance);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void closeAccount(int accountNumber){
        Connection connection = BankingConnection.connect();
        String sql = "DELETE from account WHERE accNumber=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    public void depositMoney(int accountNumber,double amount){
        Account account = getAccount(accountNumber);
        account.deposit(amount);
        System.out.println(account.getBalance());
        
        Connection connection = BankingConnection.connect();
        String sql = "UPDATE account SET accBalance=? WHERE accNumber=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountNumber());
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    
    public void withdrawMoney(int accountNumber,double amount){
        Account account = getAccount(accountNumber);
        account.withdraw(amount);
        System.out.println(account.getBalance());
        
        Connection connection = BankingConnection.connect();
        String sql = "UPDATE account SET accBalance=? WHERE accNumber=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountNumber());
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Account getAccount(int accountNumber) {
        Account account = null;
        Connection connection = BankingConnection.connect();
        String sql = "SELECT * FROM account WHERE accNumber=?";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountNumber);
            ResultSet results = preparedStatement.executeQuery();
            
            results.next();
            
            account = new Account(results.getInt(1), results.getString(2), results.getDouble(3));
            
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
        return account;

        
    }
}
