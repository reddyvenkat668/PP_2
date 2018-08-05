package com.cg.accountdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.cg.account.bean.Account;
import com.cg.account.db.AccountDb;
import com.cg.account.exception.AccountException;

public class AccountDao implements IAccountDao {

	@Override
	public String createAccount(Account account) throws AccountException {
		System.out.println(account.getMobileNo());
		Connection connect=AccountDb.getConnection();
		PreparedStatement statement;
		try{
		connect.setAutoCommit(false);
		statement = connect.prepareStatement(Mapper.create);
		statement.setString(1, account.getName());
		statement.setString(2, account.getEmailId());
		statement.setString(3, account.getMobileNo());
		statement.setDouble(4, account.getBalance());
		int result=statement.executeUpdate();
		if(result==1){
			connect.commit();
			return account.getMobileNo();
		}else{
			throw new AccountException("Customer with the mobile Number "+account.getMobileNo()+" already exists in the database");
		}
		} catch (SQLException e) {
		
		throw new AccountException(e.getMessage());
	}
	}

	@Override
	public double showBalance(String mobileNo) throws AccountException {
		// TODO Auto-generated method stub
		Connection connect=AccountDb.getConnection();
		PreparedStatement statement;
		try{
			statement = connect.prepareStatement(Mapper.getBalance);
			statement.setString(1,mobileNo);
			ResultSet result=statement.executeQuery();
			connect.commit();
			if(result!=null)
			{
				result.next();
				return result.getDouble("balance");
			}
			else
			{
				throw new AccountException("Customer with the mobile Number "+mobileNo+" does not exists in the database");
			}
		}catch (SQLException e) {
			
			throw new AccountException(e.getMessage());
		}
			
	}

	@Override
	public Account deposit(String mobileNo,double depositAmount) throws AccountException {
		// TODO Auto-generated method stub
		Connection connect=AccountDb.getConnection();
		PreparedStatement statement;
		PreparedStatement statement1;
		double balance1=0;
		try{
			statement = connect.prepareStatement(Mapper.getAccount);
			statement.setString(1,mobileNo);
			ResultSet result=statement.executeQuery();
			connect.commit();
			if(result!=null)
			{
				
				result.next();
				Account account=new Account();
				double balance=result.getDouble("balance")+depositAmount;
				/*account.setName(result.getString("name"));
				account.setEmailId(result.getString("email"));
				account.setMobileNo(result.getString("mobileno"));
				account.setBalance(balance);
				statement1 = connect.prepareStatement(Mapper.depositBalance);
				statement1.setDouble(1,account.getBalance());
				statement1.setString(2,account.getMobileNo());*/
				account.setBalance(balance);
				double b= result.getDouble("balance");
				int result1=statement.executeUpdate();			
				if(result1==1)
				{
					connect.commit();
					b=account.getBalance();
				}
			
			else{
				throw new AccountException("Customer with the mobile Number "+mobileNo+" does not exists in the database");
			}
				return account;
			}
			else
			{
				throw new AccountException("Customer with the mobile Number "+mobileNo+" does not exists in the database");
			}
		}catch (SQLException e) {
			
			throw new AccountException(e.getMessage());
		}
	}

	@Override
	public Account withdraw(String mobileNo, double withdrawalAmount)
			throws AccountException {
		// TODO Auto-generated method stub
		Connection connect=AccountDb.getConnection();
		PreparedStatement statement;
		PreparedStatement statement1;
		double balance1=0;
		try{
			statement = connect.prepareStatement(Mapper.getAccount);
			statement.setString(1,mobileNo);
			ResultSet result=statement.executeQuery();
			connect.commit();
			if(result!=null)
			{
				
				result.next();
				Account account=new Account();
				if(account.getBalance()<withdrawalAmount){
					throw new AccountException("Insufficient Balance");
				}
				double balance=result.getDouble("balance")-withdrawalAmount;
				/*account.setName(result.getString("name"));
				account.setEmailId(result.getString("email"));
				account.setMobileNo(result.getString("mobileno"));*/
				account.setBalance(balance);
				statement1 = connect.prepareStatement(Mapper.depositBalance);
				statement1.setDouble(1,account.getBalance());
				statement1.setString(2,account.getMobileNo());
				int result1=statement1.executeUpdate();			
				if(result1==1)
				{
					connect.commit();
					balance1=account.getBalance();
				}
			
			else{
				throw new AccountException("Customer with the mobile Number "+mobileNo+" does not exists in the database");
			}
				return account;
			}
			else
			{
				throw new AccountException("Customer with the mobile Number "+mobileNo+" does not exists in the database");
			}
		}catch (SQLException e) {
			
			throw new AccountException(e.getMessage());
		}
		}

	@Override
	public Account transactionDetails(String mobileNo) throws AccountException {
		// TODO Auto-generated method stub
		Connection connect=AccountDb.getConnection();
		PreparedStatement statement;
		try{
		statement = connect.prepareStatement(Mapper.getAccount);
		statement.setString(1,mobileNo);
		ResultSet result=statement.executeQuery();
		connect.commit();
		if(result!=null)
		{
			result.next();
			Account account=new Account();
			account.setName(result.getString("name"));
			account.setEmailId(result.getString("email"));
			account.setMobileNo(result.getString("mobileno"));
			account.setBalance(result.getDouble("balance"));
		return account;
		}
		else
		{
			throw new AccountException("Customer with the mobile Number "+mobileNo+" does not exists in the database");
		}
	}catch (SQLException e) {
		
		throw new AccountException(e.getMessage());
	}
	}
	@Override
	public boolean fundTransfer(String sender, String receiver, double amount)
			throws AccountException {
		// TODO Auto-generated method stub
		
		Account balance=deposit(sender, amount);
		System.out.println(balance);
		Account balance1=withdraw(receiver, amount);
		System.out.println(balance1);
	return true;
	}

}

