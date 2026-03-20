-- Script pour initialiser des données de test pour MazeBank
-- Projet BTS - Client Lourd

-- Supprimer les anciennes données (optionnel)
DELETE FROM Transactions;
DELETE FROM CheckingAccounts;
DELETE FROM SavingAccounts;
DELETE FROM Clients;
DELETE FROM Admins;

-- Créer un admin de test
INSERT INTO Admins (ID, Username, Password) VALUES (1, 'admin', 'admin123');

-- Créer des clients de test
INSERT INTO Clients (FirstName, LastName, PayeeAddress, Password, dateOfBirth) 
VALUES ('Jean', 'Dupont', '@jdupont_1', '1234', '1990-01-15');

INSERT INTO Clients (FirstName, LastName, PayeeAddress, Password, dateOfBirth) 
VALUES ('Marie', 'Martin', '@mmartin_2', '1234', '1992-05-20');

INSERT INTO Clients (FirstName, LastName, PayeeAddress, Password, dateOfBirth) 
VALUES ('Pierre', 'Bernard', '@pbernard_3', '1234', '1988-11-10');

-- Créer des comptes Checking avec des balances
INSERT INTO CheckingAccounts (Owner, AccountNumber, Limit, Balance) 
VALUES ('@jdupont_1', '3201 1234', 10, 1500.50);

INSERT INTO CheckingAccounts (Owner, AccountNumber, Limit, Balance) 
VALUES ('@mmartin_2', '3201 5678', 10, 2300.75);

INSERT INTO CheckingAccounts (Owner, AccountNumber, Limit, Balance) 
VALUES ('@pbernard_3', '3201 9012', 10, 850.00);

-- Créer des comptes Saving avec des balances
INSERT INTO SavingAccounts (Owner, AccountNumber, WithdrawalLimit, Balance) 
VALUES ('@jdupont_1', '3201 4321', 2000.00, 5000.00);

INSERT INTO SavingAccounts (Owner, AccountNumber, WithdrawalLimit, Balance) 
VALUES ('@mmartin_2', '3201 8765', 3000.00, 7500.25);

INSERT INTO SavingAccounts (Owner, AccountNumber, WithdrawalLimit, Balance) 
VALUES ('@pbernard_3', '3201 2109', 1500.00, 3200.50);

-- Créer quelques transactions de test
INSERT INTO Transactions (Sender, Receiver, Amount, Date, Message) 
VALUES ('@jdupont_1', '@mmartin_2', 100.00, '2024-01-15', 'Remboursement restaurant');

INSERT INTO Transactions (Sender, Receiver, Amount, Date, Message) 
VALUES ('@mmartin_2', '@pbernard_3', 50.00, '2024-01-16', 'Cadeau anniversaire');

INSERT INTO Transactions (Sender, Receiver, Amount, Date, Message) 
VALUES ('@pbernard_3', '@jdupont_1', 75.50, '2024-01-17', 'Partage facture');

