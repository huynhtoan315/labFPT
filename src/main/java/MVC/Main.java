package MVC;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Account currentAccount = null;

    public static void main(String[] args) {
        boolean flag = true;

        do {
            printMainMenu();

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    login();
                    break;

                case 0:
                    System.out.println("Tam biet!");
                    flag = false;
                    currentAccount = null;
                    break;

                default:
                    System.out.println("Chon khong hop le. Vui long chon lai.");
            }

        } while (flag);
    }

    private static void printMainMenu() {
        if (currentAccount != null) {
            System.out.println("Chon chuc nang:");
            System.out.println("1. Nap tien");
            System.out.println("2. Rut tien");
            System.out.println("3. Chuyen khoan ");
            System.out.println("4. Kiem tra so du");
            System.out.println("0. Đang xuat");
        } else {
            System.out.println("Chon chuc nang:");
            System.out.println("1. Dang Nhap");
            System.out.println("0. Thoat");
        }
    }

    private static void login() {
        System.out.print("Nhap so tai khoan muon dang nhap: ");
        long loginAccountNumber = scanner.nextLong();

        Account account = ConnectToSQLServer.getAccount(loginAccountNumber);
        if (account != null) {
            System.out.print("Nhap mat khau : ");
            String inputPassword = scanner.next();
            if (account.login(inputPassword)) {
                System.out.println("Dang nhap thanh cong");
                currentAccount = account;
                loggedInMenu();
            } else {
                System.out.println("Mat khau khong dung . Dang nhap that bai.");
            }
        } else {
            System.out.println("So tai khoan khong ton tai . Dang nhap that bai.");
        }
    }

    private static void loggedInMenu() {
        boolean flag = true;

        do {
            printMainMenu();

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    deposit();
                    break;

                case 2:
                    withdraw();
                    break;

                case 3:
                    transfer();
                    break;

                case 4:
                    currentAccount.checkBalance();
                    break;
                case 0:
                    System.out.println("Dang xuat thanh cong");
                    currentAccount = null;
                    flag = false;
                    break;

                default:
                    System.out.println("Chon khong hop le , vui long chon lai.");
            }

        } while (flag);
    }

    private static void deposit() {
        System.out.print("Nhap so tien ban muon nap vao tai khoan: ");
        double amount = scanner.nextDouble();
        double depositedAmount = currentAccount.deposit(amount);
        if (depositedAmount > 0) {
            ConnectToSQLServer.saveAccount(currentAccount);
            System.out.println("Ban da nap " + depositedAmount + " vao tai khoan.");
        } else {
            System.out.println("So tien nap vao khong hop le!");
        }
    }

    private static void withdraw() {
        System.out.print("Nhap so tien ban muon rut: ");
        double amount = scanner.nextDouble();
        double withdrawnAmount = currentAccount.withdraw(amount);
        if (withdrawnAmount > 0) {
            ConnectToSQLServer.saveAccount(currentAccount);
            System.out.println("Ban da rut " + withdrawnAmount + " tu tai khoan.");
        } else {
            System.out.println("So tien rut khong hop le hoac khong du tu tai khoan!");
        }
    }

    private static void transfer() {
        System.out.print("Nhap so tai khoan nguoi nhan: ");
        long recipientAccountNumber = scanner.nextLong();
        Account recipient = ConnectToSQLServer.getAccount(recipientAccountNumber);

        if (recipient != null) {
            System.out.print("Nhap so tien ban muon chuyen: ");
            double amount = scanner.nextDouble();
            currentAccount.transfer(recipient, amount);
            ConnectToSQLServer.saveAccount(currentAccount);
            ConnectToSQLServer.saveAccount(recipient);
            System.out.println("Chuyen khoan thanh cong.");
        } else {
            System.out.println("So tai khoan nguoi nhan khong hop le!");
        }
    }
    

}

