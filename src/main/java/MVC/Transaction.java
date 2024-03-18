package MVC;

import java.util.Date;

public class Transaction {
    private int transactionId;
    private long accountNumber;
    private String transactionType;
    private double amount;
    private Date transactionDate;
    private long recipientAccountNumber;

    public Transaction(int transactionId, long accountNumber, String transactionType, double amount, Date transactionDate, long recipientAccountNumber) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.recipientAccountNumber = recipientAccountNumber;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public long getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountNumber=" + accountNumber +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", recipientAccountNumber=" + recipientAccountNumber +
                '}';
    }
}
