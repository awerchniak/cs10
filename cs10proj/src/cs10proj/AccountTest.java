package cs10proj;
/**
 * AccountTest.java
 * 
 * Modification of Scot Drysdale's program of the same name to test 
 * my program, ATMSavingsAccount.java
 * 
 * Andy Werchniak 01/09/2016
 * 
 */
public class AccountTest {
  public static void main(String[] args) {
    SavingsAccount momsSavings = new SavingsAccount(0.5);
    TimeDepositAccount collegeFund = new TimeDepositAccount(1.0, 10000.00, 3, 0.5);
    ATMSavingsAccount harrysATM = new ATMSavingsAccount(0.5);

    momsSavings.deposit(10000.00);

    momsSavings.transfer(harrysATM, 2000);

    harrysATM.withdraw(200);
    harrysATM.withdraw(300);
    harrysATM.withdraw(80);
    harrysATM.withdraw(400);

    endOfMonth(momsSavings);
    endOfMonth(collegeFund);
    endOfMonth(harrysATM);

    collegeFund.transfer(harrysATM, 980);

    System.out.println("Mom's savings. " + momsSavings);
    // (10000 - 2000) * .5 % interest = 8040
    System.out.println("The college fund. " + collegeFund);
    // (10000 * 1% interest) * 0.5% penalty - 980 = 9069.50
    System.out.println("Harry's ATMSavings. " + harrysATM);
    // 2000 - 200 - 300 - 80 - 400 + (0.5/100)*1020 - (4-2)*1.50 + 980 = 2002.1
  }

  // Handles end-of-month operations. Overloaded method, because
  // checking account does different things than savings account.

  /** 
   * Handle end of month interest for a savings account
   * @param savings the savings account to handle
   */
  public static void endOfMonth(SavingsAccount savings) {
    savings.addPeriodicInterest();
  }

  /** 
   * Handles end of month fee deduction for an ATMSavings
   * @param ATM the ATMSavings account to handle
   */
  public static void endOfMonth(ATMSavingsAccount ATM){
  	ATM.addPeriodicInterest();
  }
}