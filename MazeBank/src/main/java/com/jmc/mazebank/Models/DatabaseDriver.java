package com.jmc.mazebank.Models;

import java.sql.*;
import java.time.LocalDate;

/**
 * Handles all database operations for the MazeBank application.
 * Uses SQLite as the backend database.
 */
public class DatabaseDriver {
    private static final String DB_PATH = "identifier.sqlite";
    private Connection conn;

    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            ensureSchema();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Client Methods
     */

    /**
     * Retrieves client data from the database.
     * @param pAddress The payee address of the client.
     * @param password The password of the client.
     * @return A ResultSet containing the client's data.
     */
    public ResultSet getClientData(String pAddress, String password) {
        ResultSet resultSet = null;
        try {
            final String sql = "SELECT * FROM Clients WHERE PayeeAddress=? AND Password=?;";
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, pAddress);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching client data", e);
        }
        return resultSet;
    }

    /*
     * Admin Methods
     */

    /**
     * Retrieves admin data from the database.
     * @param username The username of the admin.
     * @param password The password of the admin.
     * @return A ResultSet containing the admin's data.
     */
    public ResultSet getAdminData(String username, String password) {
        ResultSet resultSet = null;
        try {
            final String sql = "SELECT * FROM Admins WHERE Username=? AND Password=?;";
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching admin data", e);
        }
        return resultSet;
    }

    /**
     * Creates a new client in the database.
     */
    public void createClient(String fName, String lName, String pAddress, String password, LocalDate date) {
        final String sql = "INSERT INTO Clients(FirstName, LastName, PayeeAddress, Password, dateOfBirth) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
            statement.setString(1, fName);
            statement.setString(2, lName);
            statement.setString(3, pAddress);
            statement.setString(4, password);
            statement.setString(5, date.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating client", e);
        }
    }

    /**
     * Creates a new checking account.
     */
    public void createCheckingAccount(String owner, String number, double tLimit, double balance) {
        final String sql = "INSERT INTO CheckingAccounts(Owner, AccountNumber, \"Limit\", Balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
            statement.setString(1, owner);
            statement.setString(2, number);
            statement.setDouble(3, tLimit);
            statement.setDouble(4, balance);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating checking account", e);
        }
    }

    /**
     * Creates a new saving account.
     */
    public void createSavingAccount(String owner, String number, double wLimit, double balance) {
        final String sql = "INSERT INTO SavingAccounts(Owner, AccountNumber, WithdrawalLimit, Balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
            statement.setString(1, owner);
            statement.setString(2, number);
            statement.setDouble(3, wLimit);
            statement.setDouble(4, balance);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating saving account", e);
        }
    }

    /*
     * Utility Methods
     */

    /**
     * Gets the last sequence ID for the Clients table.
     */
    public int getLastClientId() {
        final String sql = "SELECT seq FROM sqlite_sequence WHERE name='Clients';";
        try (Statement statement = this.conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getInt("seq");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching last client ID", e);
        }
    }


    public ResultSet getAllClientsData() {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getCheckingAccountDta(String pAddress) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CheckingAccounts WHERE Owner='" + pAddress + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getSavingAccountDta(String pAddress) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM SavingAccounts WHERE Owner='" + pAddress + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /*
     * Transaction Methods
     */

    /**
     * Creates a new transaction in the database.
     */
    public void createTransaction(String sender, String receiver, double amount, LocalDate date, String message) {
        final String sql = "INSERT INTO Transactions(Sender, Receiver, Amount, Date, Message) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
            statement.setString(1, sender);
            statement.setString(2, receiver);
            statement.setDouble(3, amount);
            statement.setString(4, date.toString());
            statement.setString(5, message);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating transaction", e);
        }
    }

    /**
     * Retrieves all transactions for a specific client (as sender or receiver).
     */
    public ResultSet getTransactionsByClient(String pAddress) {
        ResultSet resultSet = null;
        try {
            final String sql = "SELECT * FROM Transactions WHERE Sender=? OR Receiver=? ORDER BY Date DESC;";
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, pAddress);
            preparedStatement.setString(2, pAddress);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching transactions", e);
        }
        return resultSet;
    }

    /**
     * Retrieves all transactions from the database.
     */
    public ResultSet getAllTransactions() {
        ResultSet resultSet = null;
        try {
            Statement statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Transactions ORDER BY Date DESC;");
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all transactions", e);
        }
        return resultSet;
    }

    /**
     * Transfers money from sender's checking account to receiver's saving account.
     * Creates a transaction record and updates both account balances.
     */
    public boolean sendMoney(String senderAddress, String receiverAddress, double amount, String message) {
        try {
            this.conn.setAutoCommit(false);

            // Deduct from sender's checking account
            final String deductSql = "UPDATE CheckingAccounts SET Balance = Balance - ? WHERE Owner = ?";
            try (PreparedStatement deductStmt = this.conn.prepareStatement(deductSql)) {
                deductStmt.setDouble(1, amount);
                deductStmt.setString(2, senderAddress);
                int rowsAffected = deductStmt.executeUpdate();
                if (rowsAffected == 0) {
                    this.conn.rollback();
                    return false;
                }
            }

            // Add to receiver's saving account
            final String addSql = "UPDATE SavingAccounts SET Balance = Balance + ? WHERE Owner = ?";
            try (PreparedStatement addStmt = this.conn.prepareStatement(addSql)) {
                addStmt.setDouble(1, amount);
                addStmt.setString(2, receiverAddress);
                int rowsAffected = addStmt.executeUpdate();
                if (rowsAffected == 0) {
                    this.conn.rollback();
                    return false;
                }
            }

            // Create transaction record
            createTransaction(senderAddress, receiverAddress, amount, LocalDate.now(), message);

            this.conn.commit();
            this.conn.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            try {
                this.conn.rollback();
                this.conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Error sending money", e);
        }
    }

    /**
     * Deposits money to a client's saving account (Admin function).
     */
    public boolean depositToSavingAccount(String clientAddress, double amount) {
        final String sql = "UPDATE SavingAccounts SET Balance = Balance + ? WHERE Owner = ?";
        try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
            statement.setDouble(1, amount);
            statement.setString(2, clientAddress);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error depositing to saving account", e);
        }
    }

    /**
     * Deposits money to a client's checking account (Admin function).
     */
    public boolean depositToCheckingAccount(String clientAddress, double amount) {
        final String sql = "UPDATE CheckingAccounts SET Balance = Balance + ? WHERE Owner = ?";
        try (PreparedStatement statement = this.conn.prepareStatement(sql)) {
            statement.setDouble(1, amount);
            statement.setString(2, clientAddress);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error depositing to checking account", e);
        }
    }



    /**
     * Ensures that all necessary tables exist in the database.
     */
    private void ensureSchema() throws SQLException {
        try (Statement statement = this.conn.createStatement()) {
            // Fix schema if necessary
            try {
                statement.executeUpdate("ALTER TABLE SavingAccounts RENAME COLUMN WithdrawaLimit TO WithdrawalLimit;");
                System.out.println("Renamed WithdrawaLimit to WithdrawalLimit in SavingAccounts");
            } catch (SQLException e) {
                // Column might already be renamed or table doesn't exist yet
            }

            // Clients Table
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Clients (
                        Id INTEGER PRIMARY KEY AUTOINCREMENT,
                        PayeeAddress TEXT NOT NULL UNIQUE,
                        Password TEXT NOT NULL,
                        FirstName TEXT,
                        LastName TEXT,
                        Balance REAL DEFAULT 0,
                        dateOfBirth TEXT
                    );
                    """);

            // Admins Table
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Admins (
                        Id INTEGER PRIMARY KEY AUTOINCREMENT,
                        Username TEXT NOT NULL UNIQUE,
                        Password TEXT NOT NULL
                    );
                    """);

            // CheckingAccounts Table
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS CheckingAccounts (
                        Id INTEGER PRIMARY KEY AUTOINCREMENT,
                        Owner TEXT NOT NULL,
                        AccountNumber TEXT NOT NULL UNIQUE,
                        "Limit" REAL,
                        Balance REAL
                    );
                    """);

            // SavingAccounts Table
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS SavingAccounts (
                        Id INTEGER PRIMARY KEY AUTOINCREMENT,
                        Owner TEXT NOT NULL,
                        AccountNumber TEXT NOT NULL UNIQUE,
                        WithdrawalLimit REAL,
                        Balance REAL
                    );
                    """);

            // Transactions Table
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Transactions (
                        Id INTEGER PRIMARY KEY AUTOINCREMENT,
                        Sender TEXT NOT NULL,
                        Receiver TEXT NOT NULL,
                        Amount REAL NOT NULL,
                        Date TEXT NOT NULL,
                        Message TEXT
                    );
                    """);
        }
    }

    /**
     * Initialise des données de test pour le projet BTS
     * ULTRA SIMPLE - Initialise UNE SEULE FOIS, puis garde les données
     */
    public void initTestData() {
        try {
            Statement statement = this.conn.createStatement();

            // Vérifier si des clients avec des balances > 0 existent déjà
            ResultSet rs = statement.executeQuery(
                "SELECT COUNT(*) as count FROM CheckingAccounts WHERE Balance > 0"
            );

            if (rs.next() && rs.getInt("count") > 0) {
                System.out.println("✅ Données existantes détectées - Conservation des données");
                return; // Ne rien faire, garder les données existantes
            }

            System.out.println("🔄 Initialisation des données de test (première fois)...");

            // SUPPRIMER UNIQUEMENT LES DONNÉES VIDES
            statement.executeUpdate("DELETE FROM Transactions");
            statement.executeUpdate("DELETE FROM CheckingAccounts");
            statement.executeUpdate("DELETE FROM SavingAccounts");
            statement.executeUpdate("DELETE FROM Clients");
            statement.executeUpdate("DELETE FROM Admins");

            // Créer un admin
            statement.executeUpdate("INSERT INTO Admins (Username, Password) VALUES ('admin', 'admin123')");

            // Créer des clients
            statement.executeUpdate("INSERT INTO Clients (FirstName, LastName, PayeeAddress, Password, dateOfBirth) VALUES ('Jean', 'Dupont', '@jdupont_1', '1234', '1990-01-15')");
            statement.executeUpdate("INSERT INTO Clients (FirstName, LastName, PayeeAddress, Password, dateOfBirth) VALUES ('Marie', 'Martin', '@mmartin_2', '1234', '1992-05-20')");
            statement.executeUpdate("INSERT INTO Clients (FirstName, LastName, PayeeAddress, Password, dateOfBirth) VALUES ('Pierre', 'Bernard', '@pbernard_3', '1234', '1988-11-10')");

            // Créer des comptes Checking avec des balances
            statement.executeUpdate("INSERT INTO CheckingAccounts (Owner, AccountNumber, Limit, Balance) VALUES ('@jdupont_1', '3201 1234', 10, 1500.50)");
            statement.executeUpdate("INSERT INTO CheckingAccounts (Owner, AccountNumber, Limit, Balance) VALUES ('@mmartin_2', '3201 5678', 10, 2300.75)");
            statement.executeUpdate("INSERT INTO CheckingAccounts (Owner, AccountNumber, Limit, Balance) VALUES ('@pbernard_3', '3201 9012', 10, 850.00)");

            // Créer des comptes Saving avec des balances
            statement.executeUpdate("INSERT INTO SavingAccounts (Owner, AccountNumber, WithdrawalLimit, Balance) VALUES ('@jdupont_1', '3201 4321', 2000.00, 5000.00)");
            statement.executeUpdate("INSERT INTO SavingAccounts (Owner, AccountNumber, WithdrawalLimit, Balance) VALUES ('@mmartin_2', '3201 8765', 3000.00, 7500.25)");
            statement.executeUpdate("INSERT INTO SavingAccounts (Owner, AccountNumber, WithdrawalLimit, Balance) VALUES ('@pbernard_3', '3201 2109', 1500.00, 3200.50)");

            // Créer quelques transactions
            statement.executeUpdate("INSERT INTO Transactions (Sender, Receiver, Amount, Date, Message) VALUES ('@jdupont_1', '@mmartin_2', 100.00, '2024-01-15', 'Remboursement restaurant')");
            statement.executeUpdate("INSERT INTO Transactions (Sender, Receiver, Amount, Date, Message) VALUES ('@mmartin_2', '@pbernard_3', 50.00, '2024-01-16', 'Cadeau anniversaire')");

            System.out.println("✅ Données de test initialisées avec succès !");
            System.out.println("Clients créés : @jdupont_1, @mmartin_2, @pbernard_3");
            System.out.println("Mot de passe pour tous : 1234");
            System.out.println("Admin : admin / admin123");

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation des données de test");
            e.printStackTrace();
        }
    }
}

