# Cahier des Charges - Projet MazeBank

## 1. Présentation du Projet
MazeBank est une application de gestion bancaire (Client Lourd) développée pour simplifier les opérations courantes entre une banque et ses clients. Ce projet s'inscrit dans un contexte d'apprentissage pour le développement d'applications desktop modernes.

## 2. Objectifs du Projet
*   Proposer une interface utilisateur fluide et intuitive (Material Design).
*   Gérer les opérations bancaires de base (dépôts, consultations, transactions).
*   Sécuriser l'accès aux comptes via un système de connexion différencié (Admin/Client).
*   Utiliser une base de données locale pour la persistance des informations.

## 3. Spécifications Fonctionnelles

### 3.1. Gestion des Utilisateurs
*   **Authentification** : Formulaire de connexion sécurisé.
*   **Espace Client** : 
    *   Consultation du solde (Compte Courant & Épargne).
    *   Visualisation de l'historique des transactions.
    *   Possibilité de réaliser des virements/transactions.
*   **Espace Administrateur** :
    *   Création de nouveaux comptes clients.
    *   Gestion de la liste des clients.
    *   Dépôt direct sur les comptes clients.

### 3.2. Gestion des Comptes
*   Un client peut posséder un **Compte Courant** (Checking Account) et/ou un **Compte d'Épargne** (Saving Account).
*   Chaque compte est associé à une adresse "Payee" unique (ex: `@hassan1`).

## 4. Spécifications Techniques

### 4.1. Stack Technique
*   **Java (v25+)** : Langage de programmation principal.
*   **JavaFX** : Framework pour l'interface graphique.
*   **FXML & CSS** : Séparation de la structure et du style.
*   **SQLite** : Système de gestion de base de données relationnelle léger et local.
*   **Ikonli / FontAwesome** : Bibliothèque pour les icônes vectorielles.

### 4.2. Architecture
L'application utilise le pattern **MVC** (Modèle-Vue-Contrôleur) complété par une **ViewFactory** pour la gestion de la navigation entre les fenêtres.

## 5. Contraintes et Besoins
*   **Performance** : Temps de réponse immédiat lors des accès en base de données.
*   **Portabilité** : Exécution sur n'importe quelle machine disposant d'un JRE compatible.
*   **Maintenabilité** : Code structuré et documenté (KDoc/Commentaires).

