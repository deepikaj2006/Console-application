import java.util.Scanner;

public class Admin {

    public static void menu(Scanner sc) {

        boolean exit = false;

        while (!exit) {
            System.out.println("\n------ ADMIN MENU ------");
            System.out.println("1. View Users");
            System.out.println("2. Create User");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewUsers();
                    break;

                case 2:
                    createUser(sc);
                    break;

                case 3:
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void viewUsers() {
        if (Atm.accounts.isEmpty()) {
            System.out.println("No users available.");
            return;
        }

        System.out.println("\n--- USER LIST ---");
        for (User user : Atm.accounts) {
            System.out.println("Account No: " + user.getAccountNo() +
                    " | PIN: " + user.getPin() +
                    " | Balance: " + user.getBalance());
        }
    }

    public static void createUser(Scanner sc) {

        long accNo = UserService.generateAccountNumber();
        System.out.println("Generated Account Number: " + accNo);

        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();

        System.out.print("Enter Initial Deposit: ");
        double deposit = sc.nextDouble();

        if (deposit < UserService.MIN_BALANCE) {
            System.out.println("Minimum opening balance is " + UserService.MIN_BALANCE);
            return;
        }

        UserService.saveUser(accNo, pin, deposit);
        System.out.println("User created successfully.");
    }
}