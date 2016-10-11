package cs10proj;

/**
 * Bailey Bros. ATM Savings Account Model
 * Subclass to SavingsAccount.java as written in class
 * @author Andy Werchniak on 01/09/2016
 */

public class ATMSavingsAccount extends SavingsAccount {
//Inherits methods deposit, getBalance, and toString() from BankAccount.java
	//Also inherits private double balance from BankAccount, but only access it through getBalance method
	//Inherits private double interestRate from SavingsAccount, but cannot access it
	
	private final static int FREE_WITHDRAWALS = 2;	//Set the number of free withdrawals the user gets per period
	private int withdrawals, i;											//number of withdrawals count and an index for iteration
	
	public ATMSavingsAccount(double rate){
		super(rate);			//Call superclass constructor
		withdrawals = 0;
	}
	
	public ATMSavingsAccount(double rate, double initialAmount){
		super(rate, initialAmount);		//Call superclass constructor
		withdrawals = 0;
	}
	
	public void withdraw(double amount) {
		super.withdraw(amount);	//call superclass withdraw
		withdrawals++;					//increment withdrawal count
	}
	
	public void transfer(BankAccount other, double amount){
		super.transfer(other, amount);	//call superclass transfer
		withdrawals++;									//increment withdrawal count
	}
	
	public void addPeriodicInterest(){
		super.addPeriodicInterest();											//call superclass method
		for(i=0; i<(withdrawals-FREE_WITHDRAWALS); i++)		//charge for the correct number of withdrawals
			super.withdraw(1.50);
		withdrawals = 0;																	//reset withdrawal count
	}
}