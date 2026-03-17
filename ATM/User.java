import java.util.ArrayList;

public class User {

    private long accountNo;
    private int pin;
    private double balance;
    private ArrayList<String> transactions;

    public User(long accountNo, int pin, double balance) {
        this.accountNo = accountNo;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public long getAccountNo() {
        return accountNo;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
        transactions.add("PIN changed");
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add("Deposited: +" + amount);
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add("Withdrawn: -" + amount);
            return true;
        }
        return false;
    }

    public void addTransaction(String message) {
        transactions.add(message);
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }
}