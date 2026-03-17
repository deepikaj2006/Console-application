import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserService {

    public static final double MIN_BALANCE = 500.0;
    private static final String FILE_NAME = "accounts.txt";

    public static void saveUser(long accNo, int pin, double balance) {
        try {
            User user = new User(accNo, pin, balance);
            user.addTransaction("Account created with balance: " + balance);
            Atm.accounts.add(user);

            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));
            bw.write(accNo + "," + pin + "," + balance);
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    public static boolean login(long accNo, int pin) {
        for (User user : Atm.accounts) {
            if (user.getAccountNo() == accNo && user.getPin() == pin) {
                user.addTransaction("User logged in");
                return true;
            }
        }
        return false;
    }

    public static void deposit(long accNo, double amount) {

        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }

        for (User user : Atm.accounts) {
            if (user.getAccountNo() == accNo) {
                user.deposit(amount);
                saveAccounts();
                System.out.println("Deposit successful.");
                System.out.println("Updated Balance: " + user.getBalance());
                return;
            }
        }

        System.out.println("Account not found.");
    }

    public static void withdraw(long accNo, double amount) {

        if (amount <= 0) {
            System.out.println("Invalid withdraw amount.");
            return;
        }

        for (User user : Atm.accounts) {
            if (user.getAccountNo() == accNo) {

                if (user.getBalance() - amount < MIN_BALANCE) {
                    System.out.println("Minimum balance of " + MIN_BALANCE + " must be maintained.");
                    return;
                }

                if (user.withdraw(amount)) {
                    saveAccounts();
                    System.out.println("Withdraw successful.");
                    System.out.println("Updated Balance: " + user.getBalance());
                } else {
                    System.out.println("Insufficient balance.");
                }
                return;
            }
        }

        System.out.println("Account not found.");
    }

    public static void changePin(long accNo, int newPin) {
        for (User user : Atm.accounts) {
            if (user.getAccountNo() == accNo) {
                user.setPin(newPin);
                saveAccounts();
                System.out.println("PIN changed successfully.");
                return;
            }
        }

        System.out.println("Account not found.");
    }

    public static void showBalance(long accNo) {
        for (User user : Atm.accounts) {
            if (user.getAccountNo() == accNo) {
                user.addTransaction("Balance checked: " + user.getBalance());
                System.out.println("Current Balance: " + user.getBalance());
                return;
            }
        }

        System.out.println("Account not found.");
    }

    public static void miniStatement(long accNo) {
        for (User user : Atm.accounts) {
            if (user.getAccountNo() == accNo) {
                System.out.println("\n--- MINI STATEMENT ---");
                if (user.getTransactions().isEmpty()) {
                    System.out.println("No transactions found.");
                } else {
                    for (String transaction : user.getTransactions()) {
                        System.out.println(transaction);
                    }
                }
                return;
            }
        }

        System.out.println("Account not found.");
    }

    public static void loadAccounts() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                long accNo = Long.parseLong(parts[0]);
                int pin = Integer.parseInt(parts[1]);
                double balance = Double.parseDouble(parts[2]);

                Atm.accounts.add(new User(accNo, pin, balance));
            }

            br.close();

        } catch (IOException e) {
            System.out.println("accounts.txt not found. Starting with empty records.");
        }
    }

    public static void saveAccounts() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

            for (User user : Atm.accounts) {
                bw.write(user.getAccountNo() + "," + user.getPin() + "," + user.getBalance());
                bw.newLine();
            }

            bw.close();

        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public static long generateAccountNumber() {
        if (Atm.accounts.isEmpty()) {
            return 1001;
        }

        User lastUser = Atm.accounts.get(Atm.accounts.size() - 1);
        return lastUser.getAccountNo() + 1;
    }
}