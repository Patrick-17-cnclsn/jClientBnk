# MazeBank - Application Bancaire de Bureau

MazeBank est une application de bureau moderne développée en **JavaFX**, conçue pour la gestion bancaire simplifiée. Elle permet à la fois aux clients de gérer leurs comptes et aux administrateurs de superviser les activités bancaires.

## 🚀 Fonctionnalités principales

### Côté Client :
*   **Tableau de bord** : Vue d'ensemble du solde, des transactions récentes et des informations de compte.
*   **Comptes** : Gestion des comptes courants et d'épargne.
*   **Transactions** : Historique complet des dépôts, retraits et transferts.

### Côté Administrateur :
*   **Gestion des Clients** : Création de nouveaux clients et gestion des informations existantes.
*   **Dépôts** : Effectuer des dépôts directement sur les comptes des clients.
*   **Surveillance** : Liste complète des clients et accès aux détails de leurs comptes.

## 🛠️ Technologies utilisées

*   **Langage** : Java (JDK 25+)
*   **Interface Graphique** : JavaFX
*   **Gestionnaire de dépendances** : Maven
*   **Base de données** : SQLite (JDBC)
*   **Icônes** : Ikonli (FontAwesome)
*   **Styles** : CSS personnalisé pour une interface moderne

## 📋 Prérequis

*   Java JDK 25 ou supérieur.
*   Maven installé (ou utiliser le wrapper `mvnw`).

## ⚙️ Installation et Lancement

1.  **Cloner le dépôt** :
    ```bash
    git clone https://github.com/votre-repo/MazeBank.git
    cd MazeBank
    ```

2.  **Lancer l'application avec Maven** :
    ```bash
    mvn javafx:run
    ```

## 📂 Structure du Projet

Le projet suit une architecture **MVC** (Modèle-Vue-Contrôleur) :
*   `Models/` : Logique métier et accès aux données.
*   `Views/` : Définitions FXML et gestionnaires de vues.
*   `Controllers/` : Logique de contrôle pour chaque écran.
*   `resources/Style/` : Fichiers CSS pour l'apparence.

---
*Projet réalisé dans le cadre d'un portfolio BTS.*
