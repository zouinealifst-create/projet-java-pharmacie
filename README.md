# 💊 Système de Gestion de Pharmacie

Application de bureau Java (Swing + MySQL) permettant la gestion complète d'une pharmacie : clients, médicaments, ventes, ordonnances, fournisseurs, commandes et stock.

---

## 📋 Table des matières

- [Aperçu du projet](#aperçu-du-projet)
- [Technologies utilisées](#technologies-utilisées)
- [Architecture du projet](#architecture-du-projet)
- [Fonctionnalités](#fonctionnalités)
- [Prérequis](#prérequis)
- [Installation et configuration](#installation-et-configuration)
- [Base de données](#base-de-données)
- [Compilation et exécution](#compilation-et-exécution)
- [Structure des packages](#structure-des-packages)
- [Modèle de données](#modèle-de-données)

---

## 📌 Aperçu du projet

Ce projet est une application Java Swing de gestion de pharmacie, développée selon le patron de conception **MVC (Modèle - Vue - Contrôleur)**. Elle offre une interface graphique complète avec plusieurs onglets pour gérer toutes les opérations quotidiennes d'une pharmacie.

---

## 🛠️ Technologies utilisées

| Technologie | Version | Rôle |
|---|---|---|
| **Java** | 17+ | Langage principal |
| **Java Swing** | (JDK intégré) | Interface graphique (GUI) |
| **MySQL** | 8.x | Base de données relationnelle |
| **MySQL Connector/J** | 9.7.0 | Driver JDBC Java ↔ MySQL |
| **Git** | — | Contrôle de version |

---

## 🏗️ Architecture du projet

Le projet suit le pattern **MVC** :

```
src/
├── Main/
│   └── Main.java               ← Point d'entrée de l'application
├── model/                      ← Couche Modèle (entités métier)
│   ├── Client.java
│   ├── Medicament.java
│   ├── Fournisseur.java
│   ├── Vente.java
│   ├── LigneVente.java
│   ├── Commande.java
│   ├── LigneCommande.java
│   ├── Ordonnance.java
│   └── Pharmacie.java
├── dao/                        ← Couche DAO (accès base de données)
│   ├── Utilitaire.java         ← Connexion MySQL (Singleton)
│   ├── ClientDao.java
│   ├── MedicamentDao.java
│   ├── VenteDao.java
│   ├── CommandeDao.java
│   ├── FournisseurDao.java
│   ├── OrdonnanceDao.java
│   └── PharmacieDao.java
├── controller/                 ← Couche Contrôleur (logique métier)
│   ├── ClientController.java
│   ├── MedicamentController.java
│   ├── VenteController.java
│   ├── CommandeController.java
│   ├── FournisseurController.java
│   ├── OrdonnanceController.java
│   ├── StockController.java
│   ├── DashboardController.java
│   └── PharmacieController.java
└── view/                       ← Couche Vue (interface graphique)
    ├── GestionPharmacieView.java  ← Fenêtre principale (JTabbedPane)
    ├── ClientPanel.java
    ├── MedicamentPanel.java
    ├── VentePanel.java
    ├── FournisseurPanel.java
    ├── OrdonnancePanel.java
    ├── StockPanel.java
    ├── DashboardPanel.java
    ├── PharmaciePanel.java
    └── ViewUtil.java           ← Utilitaires d'interface

lib/
└── mysql-connector-j-9.7.0.jar ← Driver MySQL

factures/                       ← Factures générées automatiquement (.txt)
```

---

## ✨ Fonctionnalités

### 🏥 Pharmacie
- Enregistrement et modification des informations de la pharmacie (nom, adresse, téléphone)

### 👥 Clients
- Ajouter / Modifier / Supprimer un client
- Rechercher un client
- Consulter l'historique des achats par client

### 💊 Médicaments
- Ajouter / Modifier / Supprimer un médicament
- Gestion des attributs : nom commercial, composition, forme, dosage, prix, date de péremption
- Recherche par nom

### 🛒 Ventes
- Créer une vente avec sélection du client
- Ajouter des lignes de produits au panier
- Gestion de l'ordonnance (optionnelle) directement depuis la vente
- Validation de la vente avec mise à jour automatique du stock
- Annulation de vente
- **Génération automatique de facture** (fichier `.txt` dans le dossier `factures/`)
- Historique complet des ventes

### 📋 Ordonnances
- Enregistrement des ordonnances par client et médecin
- Liaison automatique avec la vente

### 🏭 Fournisseurs
- Ajouter / Modifier / Supprimer un fournisseur
- Rechercher par nom

### 📦 Commandes fournisseurs
- Créer des commandes d'approvisionnement
- Suivi du statut (En cours / Livrée / Annulée)
- Mise à jour du stock à la réception

### 📊 Stock
- Visualisation du stock actuel
- Alertes pour les médicaments en stock faible (en dessous du seuil d'alerte)
- Entrées et sorties de stock manuelles

### 📈 Dashboard analytique
- Chiffre d'affaires par période
- Ventes par produit
- Ventes par client
- Produits les plus demandés

---

## ✅ Prérequis

Avant de lancer l'application, assurez-vous d'avoir installé :

1. **Java JDK 17** ou supérieur
   - Vérification : `java -version`
2. **MySQL Server 8.x**
   - Vérification : `mysql --version`
3. **Git** (optionnel, pour cloner le projet)

---

## ⚙️ Installation et configuration

### Étape 1 — Cloner le projet

```bash
git clone https://github.com/zouinealifst-create/projet-java-pharmacie.git
cd projet-java-pharmacie
```

### Étape 2 — Créer la base de données MySQL

Connectez-vous à MySQL et exécutez le script SQL suivant :

```sql
CREATE DATABASE IF NOT EXISTS gestion_pharmacie CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gestion_pharmacie;
```

### Étape 3 — Configurer la connexion

Ouvrez le fichier [`src/dao/Utilitaire.java`](src/dao/Utilitaire.java) et modifiez les paramètres si nécessaire :

```java
private static final String URL  = "jdbc:mysql://localhost:3306/gestion_pharmacie";
private static final String USER = "root";      // ← votre utilisateur MySQL
private static final String PASSWORD = "";      // ← votre mot de passe MySQL
```

---

## 🗄️ Base de données

### Script de création des tables

```sql
USE gestion_pharmacie;

CREATE TABLE Pharmacie (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100),
    adresse VARCHAR(255),
    telephone VARCHAR(20)
);

CREATE TABLE Client (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    telephone VARCHAR(20) NOT NULL,
    adresse VARCHAR(255)
);

CREATE TABLE Fournisseur (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nomSociete VARCHAR(100) NOT NULL,
    contact VARCHAR(100),
    adresse VARCHAR(255)
);

CREATE TABLE Medicament (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nomCommercial VARCHAR(100) NOT NULL,
    composition TEXT,
    forme VARCHAR(50),
    dosage VARCHAR(50),
    prix DOUBLE NOT NULL,
    quantiteStock INT DEFAULT 0,
    datePeremption DATE,
    seuilAlerte INT DEFAULT 10
);

CREATE TABLE Ordonnance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idClient INT NOT NULL,
    medecin VARCHAR(100),
    dateOrdonnance DATE,
    notes TEXT,
    FOREIGN KEY (idClient) REFERENCES Client(id)
);

CREATE TABLE Vente (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idClient INT,
    idOrdonnance INT,
    dateVente DATE NOT NULL,
    FOREIGN KEY (idClient) REFERENCES Client(id),
    FOREIGN KEY (idOrdonnance) REFERENCES Ordonnance(id)
);

CREATE TABLE LigneVente (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idVente INT NOT NULL,
    idMedicament INT NOT NULL,
    quantite INT NOT NULL,
    prixUnitaire DOUBLE NOT NULL,
    FOREIGN KEY (idVente) REFERENCES Vente(id),
    FOREIGN KEY (idMedicament) REFERENCES Medicament(id)
);

CREATE TABLE Commande (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idFournisseur INT NOT NULL,
    dateCommande DATE NOT NULL,
    statut VARCHAR(50) DEFAULT 'En cours',
    FOREIGN KEY (idFournisseur) REFERENCES Fournisseur(id)
);

CREATE TABLE LigneCommande (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idCommande INT NOT NULL,
    idMedicament INT NOT NULL,
    quantiteCommandee INT NOT NULL,
    prixAchat DOUBLE NOT NULL,
    FOREIGN KEY (idCommande) REFERENCES Commande(id),
    FOREIGN KEY (idMedicament) REFERENCES Medicament(id)
);
```

---

## 🚀 Compilation et exécution

### Compiler le projet

```bash
# Depuis la racine du projet
javac -encoding UTF-8 -cp lib/mysql-connector-j-9.7.0.jar -d bin $(find src -name "*.java")
```

**Sur Windows (PowerShell) :**
```powershell
$files = Get-ChildItem -Path src -Recurse -Filter "*.java" | Select-Object -ExpandProperty FullName
javac -encoding UTF-8 -cp "lib\mysql-connector-j-9.7.0.jar" -d bin $files
```

### Lancer l'application

```bash
# Linux / macOS
java -cp "bin:lib/mysql-connector-j-9.7.0.jar" Main.Main

# Windows
java -cp "bin;lib\mysql-connector-j-9.7.0.jar" Main.Main
```

> 💡 **Via un IDE** (IntelliJ IDEA / Eclipse) : Importer le projet, ajouter `lib/mysql-connector-j-9.7.0.jar` au classpath, puis exécuter `Main/Main.java`.

---

## 📐 Modèle de données

```
Client ──────────────────────┐
                             │
Ordonnance ──── idClient ────┘
     │
     └──── idOrdonnance ──── Vente ──── LigneVente ──── Medicament
                                              │
                                         quantite / prixUnitaire

Fournisseur ──── Commande ──── LigneCommande ──── Medicament
                      │
                   statut (En cours / Livrée / Annulée)
```

---

## 📁 Fichiers importants

| Fichier | Description |
|---|---|
| `src/Main/Main.java` | Point d'entrée — lance la fenêtre principale |
| `src/dao/Utilitaire.java` | Connexion MySQL (singleton) |
| `src/view/GestionPharmacieView.java` | Fenêtre principale avec tous les onglets |
| `lib/mysql-connector-j-9.7.0.jar` | Driver JDBC MySQL |
| `factures/` | Dossier des factures générées automatiquement |

---

## 👨‍💻 Auteur

Projet Java — Gestion de Pharmacie  
Développé avec Java 17, Swing et MySQL.

---

## 📄 Licence

Projet académique — usage éducatif.
