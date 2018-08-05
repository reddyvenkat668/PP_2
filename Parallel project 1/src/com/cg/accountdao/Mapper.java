package com.cg.accountdao;

public interface Mapper {
	public String create="insert into account(name,email,mobileno,balance) values(?,?,?,?)";
	public String getBalance="select balance from account where mobileno=?";
	public String getAccount="select * from account where mobileno=?";
	public String depositBalance="update account set balance=? where mobileno=?";

}
