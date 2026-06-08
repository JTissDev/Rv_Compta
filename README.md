# Rv_Compta

API Java de gestion comptable modulaire, résiliente et haute performance.
> 📑 **Liens rapides :** > 
> [**Consulter la TODO List**](./TODO.md) | 
> [**Voir le Changelog**](./CHANGELOG.md)

## Sommaire

- [🎯 Objectifs & Vision Métier](#-objectifs--vision-métier)
- [🛠 Technologies](#technologies)
- [🏗 Architecture & Design Patterns](#-architecture--design-patterns)
- [⚙️ Stratégie de Chargement (PCG)](#️-stratégie-de-chargement-pcg)
- [🌍 Environnements & Configuration](#-environnements--configuration)
- [🚀 Installation & Lancement](#-installation--lancement)
- [📂 Structure du Projet](#-structure-du-projet)
- [🧪 Tests unitaires](#-tests-unitaires)
- [🤝 Contribution](#-contribution)
- [👥 Contributeurs](#-contributeurs)
- [📄 Licence](#-licence)
- [👨‍💻 Auteur](#-auteur)

---

## 🎯 Objectifs & Vision Métier

**Rv_Compta** a pour ambition de démocratiser la rigueur de la comptabilité professionnelle en proposant un moteur de comptabilité en partie double universel, capable de s'adapter aux besoins des **Particuliers**, les **Associations** et les **Entreprises**.

### 1. Orientations Métiers (Le Cœur du Projet)
* **Comptabilité en Partie Double Universelle** : Application systématique du principe de la double écriture (débit/crédit) pour tous les profils, y compris pour la gestion patrimoniale des particuliers, garantissant une traçabilité financière absolue.
* **Articulation Pivot PCG / PCP** :
  * **PCG (Plan Comptable Général)** : Le socle standard immuable qui détermine la *nature* de l'opération (Qu'est-ce qui est payé ou encaissé ?).
  * **PCP (Plan Comptable Particulier)** : Un bloc d'extension universel (disponible pour tous les profils) agissant comme une comptabilité auxiliaire/analytique. Il permet de *détailler* le PCG en liant un mouvement à une entité concrète (ex: pour un particulier, affecter une dépense à un bien immobilier ou un véhicule précis ; pour une entreprise, à un compte de tiers ou un centre de coût).
* **Ventilation Avancée des Écritures** : Prise en charge d'écritures complexes (multi-lignes) pour permettre d'éclater un flux financier global sur plusieurs natures (PCG) et plusieurs destinations/biens (PCP).
* **États Financiers Automatisés** : Capacité à générer l'ensemble des documents comptables de synthèse (Journaux, Grand Livre, Balance, Bilan et Compte de Résultat) adaptés et filtrables par entités du PCP.

### 2. Engagements Techniques (Au service du métier)
* **Modularité Étanche** : Découpage strict par domaines fonctionnels (`pcg`, `pcp`, `compta`) pour isoler la logique de structure, la logique d'affectation et le moteur d'écritures.
* **Résilience & Fail-Safe** : Protection des données financières par un mécanisme de persistance hybride (Fichier/BDD) et une stratégie de chargement auto-adaptative en cas de défaillance.
* **Haute Performance Engine** : Optimisation des traitements de calcul pour compiler instantanément les balances croisées PCG/PCP, même sur des volumes importants d'écritures ventilées.

---

## Technologies

- **Langage** : Java 21 (LTS)
- **Build & gestion de dépendances** : Maven 3.9+
- **Framework** : Spring Boot 3.x (parent `spring-boot-starter-parent`)
- **JSON** : Jakarta JSON (`jakarta.json`)
- **Office Engine** : Apache POI (Lecture Excel)
- **Tests** : JUnit 5 & AssertJ (via `spring-boot-starter-test`)

---

## 🏗 Architecture & Design Patterns

Le projet adopte une structure **Hybride Orientée Domaine**, fusionnant la clarté du découpage par fonctionnalités et la rigueur d'un moteur technique centralisé.

### 1. Organisation des Packages 📦
* **`core/`** : Infrastructure transversale (Exceptions, Utils, Configuration, Repositories de base).
* **`features/`** : Le cœur métier divisé par domaines :
  * **core/** : Conteneurs transversaux (ex: DTO génériques `PcgCoreDTO`).
  * **pcg/, pcp/, compta/, referential/** : Sous-modules spécialisés contenant leurs propres Controllers, Services, DTOs et Mappers.
* **`engine/`** : Moteurs techniques spécialisés (Stateless) pour l'import/export de fichiers.

### 2. Principes Directeurs 🎯
* **API Fluent** : Chaînage systématique des setters pour faciliter la manipulation des DTOs.
* **Template Method** : Abstraction de l'accès aux données via `CrudRepository` et `AbstractBaseRepository`.
* **Multi-Tenancy Prêt** : Isolation stricte pour le futur déploiement multi-profils (Particuliers / Entreprises).

---

## ⚙️ Stratégie de Chargement (PCG)

L'application gère trois modes pilotés par la propriété `app.pcg.load-mode` :

| Mode | Description | Cas d'usage |
| :--- | :--- | :--- |
| `FILE` | Force la lecture depuis les fichiers JSON locaux. | V1 (MVP) & Développement |
| `BDD` | Force la lecture depuis la base de données relationnelle. | V2 et futures (Production) |
| `AUTO` | Fallback intelligent : Priorité BDD, chargement Fichier si BDD inaccessible. | Résilience |

---

## 🌍 Environnements & Configuration

La configuration s'articule autour d'une architecture modulaire des fichiers properties.

* `src/main/resources/application.properties` : Propriétés globales et définition du profil actif (`spring.profiles.active`).
* `src/main/resources/.env/` : Ce dossier contient les fichiers spécifiques à chaque profil :
  * **DEV** (`application-dev.properties`) : Mode `FILE`, ciblant les données locales.
  * **TEST** (`application-test.properties`) : Configuration légère et isolée pour JUnit.
  * **PROD** (`application-prod.properties`) : Mode `AUTO` ou `BDD` pour la sécurité.
  * **SHOW** / **STAGING** : Modes pour démonstration ou validation client.

> **Note - Portabilité (Local vs Raspberry Pi) :**
> L'architecture est pensée pour permettre un développement en local tout en garantissant un déploiement fluide sur des cibles matérielles distantes (ex: hébergement sur Raspberry Pi). Le changement de chemin d'accès au dossier `storage` de persistance JSON s'effectue dynamiquement en basculant le profil configuré dans ces fichiers `.env`.

---

## 🚀 Installation & Lancement

### 🛠 Prérequis

- Java **21** installé (configuré dans le `PATH`)
- Maven **3.9+** installé
- **Git Bash** (recommandé sous Windows pour l'exécution des commandes)
- **IntelliJ IDEA Community / Ultimate 2024+** (L'IDE recommandé pour l'écosystème de ce projet)

---

### 📥 Installation & Compilation

1. **Cloner le dépôt :**
   ```bash
   git clone [https://github.com/votre-user/Rv_Compta.git](https://github.com/jtissdev/Rv_Compta.git)
   cd Rv_Compta
   ```
2. **Compiler le projet (via Git Bash ou terminal) :**
   ```bash
   mvn clean install
   ```

---

### 🚀 Exécution

**Option A : Via Spring Boot Plugin**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
> Remplacez `dev` par le profil souhaité (`test`, `prod`, `show`).

**Option B : Executer le JAR compilé**
```bash
java -jar target/Rv_Compta-1.0-SNAPSHOT.jar --spring.profiles.active=dev
```
**Option C : Depuis IntelliJ IDEA (Recommandé)**
Ouvrez le dossier du projet dans IntelliJ, attendez la synchronisation Maven, localisez la classe `Apps.java` et lancez-la avec l'icône Run (▶).

---

## 📂 Structure du Projet

```text
src/main/java/com/jtissdev_API/
├── core/                # Infrastructure (Exceptions, Utils, Repositories de base)
├── features/            # Modules métiers
│   ├── compta/          # Journaux et mouvements en partie double
│   ├── pcg/             # Plan Comptable Général
│   ├── pcp/             # Plan Comptable Particulier (Tiers, analytique)
│   └── referential/     # Référentiels techniques (Statuts, Paiements)
├── engine/              # Automates I/O (JSON, Excel POI)
└── Apps.java            # Point d'entrée Spring Boot

### Lancer l’application 🚀

Depuis la racine du projet :

```bash
mvn springboot:run
```
## 🧪 Tests unitaires

Pour garantir la stabilité du socle comptable et du respect du Fail-Safe, les tests unitaires couvrent le modèle de données et le mapping JSON.

- Tous les tests présents dans `src/test/java` seront exécutés.
- Le projet utilise JUnit 5.
- Les résultats sont visibles dans la console Maven ou dans l’IDE.

```bash
mvn test
```

Dans **IntelliJ IDEA** :Clic droit sur `src/test/java` → **Run 'Tests in ...'**.

---

## 📄 Licence

Ce projet est distribué sous licence **MIT**. Vous êtes libre de l'utiliser, de le modifier et de le distribuer.

---

## 👨‍💻 Auteur

**JtissDev** - *Architecture & Développement* > "Concevoir pour la résilience, coder pour la clarté."

---

## 🤝 Contribution

Les contributions sont les bienvenues ! Pour maintenir la qualité du code :

1. **Forkez** le projet.
2. Créez une **branche** dédiée (`git checkout -b feature/name-de-la-feature`).
3. Effectuez vos modifications en respectant la structure **Hybride Domaine**.
4. Assurez-vous que les **tests unitaires** passent (`mvn test`).
5. Ouvrez une **Pull Request** détaillée.

---

## 👥 Contributeurs

Nous attachons une grande importance à la clarté de l'historique. Chaque contributeur est invité à ajouter sa ligne en respectant strictement le format suivant :
`| Date | Pseudo | Module impacté | Description courte |`

| Date | Pseudo | Module | Description |
| :--- | :--- | :--- | :--- |
| 14/04/2026 | **JtissDev** | `Structure` | Initialisation de l'architecture Hybride Domaine |

> **Note aux contributeurs :** Merci d'ajouter votre ligne en bas du tableau ci-dessus lors de votre Pull Request.

---