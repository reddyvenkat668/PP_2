package com.cg.account.ui;

import com.cg.account.bean.Account;
import com.cg.account.exception.AccountException;
import com.cg.accountservice.AccountService;
import com.cg.accountservice.IAccountService;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		IAccountService service=new AccountService();
		Account account=new Account();
		account.setName("Venkat");
		account.setEmailId("venkat@gmail.com");
		account.setMobileNo("9999999999");
		account.setBalance(100000);
		//System.out.println(account.getName());
		
		try {
			String m=service.createAccount(account);
			System.out.println(m);
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

	}

}
