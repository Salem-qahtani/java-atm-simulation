package java.atm.simulation;

import java.util.Scanner;

public class ATM_Simulation {

    private static final String ADMIN_PIN = "1234";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Add as many accounts as you want
        Account[] accounts = {
                new Account(12345, "Alice", 5000.00),
                new Account(67890, "Bob", 3000.00),
                new Account(54321, "Charlie", 7000.00)
        };

        while (true) {
            System.out.println("\n=== Welcome to the ATM ===");
            System.out.println("1. Login (Account Number)");
            System.out.println("2. Administrator Login");
            System.out.println("3. Exit");

            int choice = readInt(input, "Enter your choice: ");

            switch (choice) {
                case 1:
                    int accNumber = readInt(input, "Enter your account number: ");
                    Account current = findAccountByNumber(accounts, accNumber);

                    if (current == null) {
                        System.out.println("Invalid account number.");
                        break;
                    }

                    System.out.println("Login successful. Welcome, " + current.getName() + "!");
                    accountOperations(input, current, accounts);
                    break;

                case 2:
                    adminMenu(input);
                    break;

                case 3:
                    System.out.println("Exiting the program. Goodbye!");
                    input.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // ================= Account Operations =================

    private static void accountOperations(Scanner input, Account current, Account[] accounts) {
        while (true) {
            System.out.println("\n--- Account Operations (" + current.getName() + ") ---");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Logout (Show Statistics)");

            int choice = readInt(input, "Enter operation: ");

            switch (choice) {
                case 1:
                    current.displayBalance();
                    break;

                case 2:
                    double deposit = readPositiveDouble(input, "Enter amount to deposit: ");
                    System.out.println(current.deposit(deposit)
                            ? "Deposit successful."
                            : "Deposit failed.");
                    break;

                case 3:
                    double withdraw = readPositiveDouble(input, "Enter amount to withdraw: ");
                    System.out.println(current.withdraw(withdraw)
                            ? "Withdrawal successful."
                            : "Insufficient funds.");
                    break;

                case 4:
                    transferFlow(input, current, accounts);
                    break;

                case 5:
                    current.displayStatistics();
                    return;

                default:
                    System.out.println("Invalid operation.");
            }
        }
    }

    // ================= Transfer =================

    private static void transferFlow(Scanner input, Account sender, Account[] accounts) {
        if (accounts.length <= 1) {
            System.out.println("No available accounts to transfer to.");
            return;
        }

        System.out.println("\nAvailable recipient accounts:");
        for (Account acc : accounts) {
            if (acc != sender) {
                System.out.println("- " + acc.getName()
                        + " | Account Number: " + acc.getNumber());
            }
        }

        int recipientNumber = readInt(input, "Enter recipient account number: ");
        Account recipient = findAccountByNumber(accounts, recipientNumber);

        if (recipient == null || recipient == sender) {
            System.out.println("Invalid recipient account.");
            return;
        }

        double amount = readPositiveDouble(input, "Enter amount to transfer: ");
        if (sender.transfer(recipient, amount)) {
            System.out.println("Transfer successful to " + recipient.getName() + ".");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    // ================= Admin =================

    private static void adminMenu(Scanner input) {
        if (!adminLogin(input)) {
            System.out.println("Admin access denied.");
            return;
        }

        while (true) {
            System.out.println("\n=== Administrator Menu ===");
            System.out.println("1. View Total Charges");
            System.out.println("2. View Popular Operations");
            System.out.println("3. Exit");

            int choice = readInt(input, "Enter your choice: ");

            switch (choice) {
                case 1:
                    Account.displayTotalCharges();
                    break;
                case 2:
                    Account.displayPopularOperations();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static boolean adminLogin(Scanner input) {
        int attempts = 3;

        while (attempts-- > 0) {
            System.out.print("Enter Admin PIN: ");
            if (ADMIN_PIN.equals(input.nextLine().trim())) {
                System.out.println("Admin login successful.");
                return true;
            }
            System.out.println("Wrong PIN. Attempts left: " + attempts);
        }
        return false;
    }

    // ================= Helpers =================

    private static Account findAccountByNumber(Account[] accounts, int number) {
        for (Account acc : accounts) {
            if (acc.getNumber() == number) return acc;
        }
        return null;
    }

    private static int readInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readPositiveDouble(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(input.nextLine().trim());
                if (val > 0) return val;
                System.out.println("Amount must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
