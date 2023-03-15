//projects desing
//NegativeAmountException.java
public class NegativeAmountException extends Exception{
      NegativeAmountException(){
        super();
      }
      NegativeAmountException(String errMsg){
        super(errMsg);
      }
      NegativeAmountException(Exception e){
        super(e);
      }
}// 1st Exception....
 // InsuffcientFundsException.java

public class InsuffcientFundsException extends Exception {
    InsuffcientFundsException() {
        super();
    }

    InsuffcientFundsException(String errMsg) {
        super(errMsg);
    }

    InsuffcientFundsException(Exception e) {
        super(e);
    }
}// 2nd Exception
 // ATMCard .java

interface ATMCard {

    void deposite(double amt)
            throws NegativeAmountException;

    void withdraw(double amt)
            throws NegativeAmountException,
            InsuffcientFundsException;

    void currentBalance();

}

// SBIBankCard.java
public class SBIBankCard implements ATMCard {
    private double balance;

    public void deposite(double amt)
            throws NegativeAmountException {
        if (amt == 0)
            throw new NegativeAmountException(
                    "Dont pass  zero");

        if (amt <= 0)
            throw new NegativeAmountException(
                    "Dont pass -ve num or zero");

        balance = balance + amt;
    }

    public void withdraw(double amt)
            throws NegativeAmountException, InsuffcientFundsException {

        if (amt <= 0)
            throw new NegativeAmountException(
                    "Dont pass -ve num or zero");

        if (amt > balance)
            throw new InsuffcientFundsException(
                    "Insufficient Funds");

        balance = balance - amt;
    }

    public void currentBalance() {
        System.out.println(balance);
    }
}

// HDFCBankCard.java
public class HDFCBankCard implements ATMCard {
    private double balance;

    public void deposite(double amt)
            throws NegativeAmountException {
        if (amt == 0)
            throw new NegativeAmountException(
                    "Dont pass  zero");

        if (amt <= 0)
            throw new NegativeAmountException(
                    "Dont pass -ve num or Zero");

        balance = balance + amt;
    }

    public void withdraw(double amt)
            throws NegativeAmountException, InsuffcientFundsException {

        if (amt <= 0)
            throw new NegativeAmountException(
                    "Dont pass -ve num or zero");

        if (amt > balance)
            throw new InsuffcientFundsException(
                    "Insufficient Funds");

        balance = balance - amt;
    }

    public void currentBalance() {
        System.out.println(balance);
    }
}

// ATM.java
import java.util.Scanner;
import java.util.InputMismatchException;

class ATM {

    @SuppressWarnings("deprecation")
    public static void main(String... args) {

        Scanner scn = new Scanner(System.in);

        while (true) {
            System.out.print("\nInsert Card: ");
            String cardName = scn.nextLine();

            try {
                // using Reflection API
                Class<?> cls = Class.forName(cardName);
                Object obj = cls.newInstance();

                ATMCard card = (ATMCard) obj;
                System.out.println("Card reading completed successfully");
                System.out.println("Start transcations");

                loop: while (true) {
                    System.out.println("\nChoose Option: ");
                    System.out.println(" 1.Deposite");
                    System.out.println(" 2.Withdraw");
                    System.out.println(" 3.BalanceEnquiry");
                    System.out.println(" 4.Exit");

                    System.out.print("Enter Option Number: ");
                    int option = scn.nextInt();
                    scn.nextLine();

                    switch (option) {
                        case 1: {// deposite operation
                            while (true) {
                                try {
                                    System.out.print("\nEnter amount to deposite");
                                    double amt = scn.nextDouble();
                                    scn.nextLine();

                                    card.deposite(amt);
                                    System.out.println(amt + " deposited successfully");
                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.println("Error:Enter only Number");
                                    scn.nextLine();
                                } catch (NegativeAmountException e) {
                                    System.out.println("Error:" + e.getMessage());
                                }
                                scn.nextLine();
                            }
                            break;
                        }
                        case 2: {// withdraw Operation
                            while (true) {
                                try {
                                    System.out.print("\nEnter amount to withdraw: ");
                                    double amt = scn.nextDouble();
                                    scn.nextLine();

                                    card.withdraw(amt);
                                    System.out.println("Collect your Cash");
                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.println("Error: Enter only Number");
                                    scn.nextLine();
                                } catch (NegativeAmountException e) {
                                    System.out.println("Error:" + e.getMessage());
                                } catch (InsuffcientFundsException e) {
                                    System.out.println("Error:" + e.getMessage());
                                }
                            }
                            break;

                        }
                        case 3: {
                            System.out.println("Current Balance: ");
                            card.currentBalance();
                            break;
                        }
                        case 4: {
                            System.out.println("\n****************Thank You :-),Visit again****************");
                            break loop;
                        }
                        default: {
                            System.out.println("Invalid Option,choose correct Option");
                        }
                    }
                }
            } // Outer try closed
            catch (ClassNotFoundException e) {
                System.out.println(cardName + ".class file is not available");
            } catch (InstantiationException e) {
                System.out.println(cardName + "class does not contain no-param Constructor");
            } catch (IllegalAccessException e) {
                System.out.println(cardName + "class does not contain visible no_param constructor");
            } catch (ClassCastException e) {
                System.out.println("Invalid Card");
            }
        }
    }

}
