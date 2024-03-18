package MVC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class ConnectToSQLServer {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=atm;"
            + "encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "htoan";
    private static final String PASSWORD = "1";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    public static Account getAccount(long accountNumber) {
        String query = "SELECT * FROM Account WHERE account_number = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, accountNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long accNumber = resultSet.getLong("account_number");
                    String accName = resultSet.getString("account_name");
                    double balance = resultSet.getDouble("balance");
                    String password = resultSet.getString("password");
                    return new Account(accNumber, accName, balance, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveAccount(Account account) {
        String query = "UPDATE Account SET balance = ?, password = ? WHERE account_number = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, account.getBalance());
            statement.setString(2, account.getPassword());
            statement.setLong(3, account.getAccountNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void addTransaction(Transaction transaction) {
        String query = "INSERT INTO Menu (transaction_id, account_number, transaction_type, amount, transaction_date, recipient_account_number) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, transaction.getTransactionId());
            statement.setLong(2, transaction.getAccountNumber());
            statement.setString(3, transaction.getTransactionType());
            statement.setDouble(4, transaction.getAmount());
            statement.setTimestamp(5, new Timestamp(transaction.getTransactionDate().getTime()));
            statement.setLong(6, transaction.getRecipientAccountNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM Menu";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int transactionId = resultSet.getInt("transaction_id");
                long accountNumber = resultSet.getLong("account_number");
                String transactionType = resultSet.getString("transaction_type");
                double amount = resultSet.getDouble("amount");
                Date transactionDate = resultSet.getTimestamp("transaction_date");
                long recipientAccountNumber = resultSet.getLong("recipient_account_number");
                Transaction transaction = new Transaction(transactionId, accountNumber, transactionType, amount, transactionDate, recipientAccountNumber);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static void deleteTransaction(int transactionId) {
        String query = "DELETE FROM Menu WHERE transaction_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, transactionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
