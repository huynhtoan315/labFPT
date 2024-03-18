package MVC;

import java.text.NumberFormat;
import java.util.Scanner;

// Định nghĩa lớp Account để mô phỏng thông tin tài khoản ngân hàng
class Account {
    private long accountNumber;
    private String accountName;
    private double balance;
    private String password;

    public Account(long accountNumber, String accountName, double balance, String password) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
        this.password = password;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String formattedBalance = currencyFormat.format(balance);
        return accountNumber + "-" + accountName + "-" + formattedBalance;
    }

    public double deposit(double amount) {
        if (amount >= 0) {
            balance += amount;
            return amount;
        } else {
            return 0;
        }
    }

    public double withdraw(double amount) {
        if (balance >= 50000 && amount <= balance) {
            balance -= amount;
            return amount;
        } else {
            return 0;
        }
    }

    public void transfer(Account recipient, double amount) {
        if (balance >= amount && amount >= 0) {
            recipient.balance += amount;
            balance -= amount;
        }
    }

    public boolean login(String inputPassword) {
        return inputPassword.equals(this.password);
    }

    public void checkBalance() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String formattedBalance = currencyFormat.format(balance);
        System.out.println("So du trong tai khoan cua ban la: " + formattedBalance);
    }
}

