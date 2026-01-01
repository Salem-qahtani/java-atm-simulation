package java.atm.simulation;

public class Account {
    private int number;
    private String name;
    private double balance;
    private int numViewBalance;
    private int numDeposits;
    private int numWithdraws;
    private int numTransfers;
    private double totalInAccount;
    private double totalOutAccount;

    private static double totalCharges = 0;
    private static int totalViewBalance = 0;
    private static int totalDeposits = 0;
    private static int totalWithdraws = 0;
    private static int totalTransfers = 0;

    public Account(int number, String name, double balance) {
        this.number = number;
        this.name = name;
        this.balance = balance;
        this.numViewBalance = 0;
        this.numDeposits = 0;
        this.numWithdraws = 0;
        this.numTransfers = 0;
        this.totalInAccount = 0;
        this.totalOutAccount = 0;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            totalInAccount += amount;
            numDeposits++;
            totalDeposits++;
            return true;
        }
        return false;
    }

    private boolean isAvailable(double amount) {
        return balance >= amount;
    }

    public boolean withdraw(double amount) {
        double fee = amount * 0.01;
        if (isAvailable(amount + fee)) {
            balance -= (amount + fee);
            totalOutAccount += amount;
            totalCharges += fee;
            numWithdraws++;
            totalWithdraws++;
            return true;
        }
        return false;
    }

    public boolean transfer(Account recipient, double amount) {
        double fee = amount * 0.025;
        if (isAvailable(amount + fee)) {
            balance -= (amount + fee);
            recipient.deposit(amount);
            totalOutAccount += amount;
            totalCharges += fee;
            numTransfers++;
            totalTransfers++;
            return true;
        }
        return false;
    }

    public void displayBalance() {
        System.out.println("Account Number: " + number + ", Name: " + name + ", Balance: " + balance);
        numViewBalance++;
        totalViewBalance++;
    }

    public void displayStatistics() {
        System.out.println("Statistics for Account " + name + ":");
        System.out.println("View Balance: " + numViewBalance);
        System.out.println("Deposits: " + numDeposits);
        System.out.println("Withdrawals: " + numWithdraws);
        System.out.println("Transfers: " + numTransfers);
        System.out.println("Total In: " + totalInAccount);
        System.out.println("Total Out: " + totalOutAccount);
    }

    public void reset() {
        numViewBalance = 0;
        numDeposits = 0;
        numWithdraws = 0;
        numTransfers = 0;
        totalInAccount = 0;
        totalOutAccount = 0;
    }

    public static void displayTotalCharges() {
        System.out.println("Total Charges: " + totalCharges);
    }

    public static void displayPopularOperations() {
        System.out.println("Total View Balance: " + totalViewBalance);
        System.out.println("Total Deposits: " + totalDeposits);
        System.out.println("Total Withdrawals: " + totalWithdraws);
        System.out.println("Total Transfers: " + totalTransfers);
    }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public int getNumViewBalance() { return numViewBalance; }
    public void setNumViewBalance(int numViewBalance) { this.numViewBalance = numViewBalance; }

    public int getNumDeposits() { return numDeposits; }
    public void setNumDeposits(int numDeposits) { this.numDeposits = numDeposits; }

    public int getNumWithdraws() { return numWithdraws; }
    public void setNumWithdraws(int numWithdraws) { this.numWithdraws = numWithdraws; }

    public int getNumTransfers() { return numTransfers; }
    public void setNumTransfers(int numTransfers) { this.numTransfers = numTransfers; }

    public double getTotalInAccount() { return totalInAccount; }
    public void setTotalInAccount(double totalInAccount) { this.totalInAccount = totalInAccount; }

    public double getTotalOutAccount() { return totalOutAccount; }
    public void setTotalOutAccount(double totalOutAccount) { this.totalOutAccount = totalOutAccount; }

    public static double getTotalCharges() { return totalCharges; }
    public static void setTotalCharges(double charges) { totalCharges = charges; }

    public static int getTotalViewBalance() { return totalViewBalance; }
    public static void setTotalViewBalance(int viewBalanceCount) { totalViewBalance = viewBalanceCount; }

    public static int getTotalDeposits() { return totalDeposits; }
    public static void setTotalDeposits(int depositCount) { totalDeposits = depositCount; }

    public static int getTotalWithdraws() { return totalWithdraws; }
    public static void setTotalWithdraws(int withdrawCount) { totalWithdraws = withdrawCount; }

    public static int getTotalTransfers() { return totalTransfers; }
    public static void setTotalTransfers(int transferCount) { totalTransfers = transferCount; }
}
