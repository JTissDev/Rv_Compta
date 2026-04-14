# Rv_Compta

API Java de gestion comptable modulaire, résiliente et haute performance.
> 📑 **Liens rapides :** > 
> [**Consulter la TODO List**](./TODO.md) | 
> [**Voir le Changelog**](./CHANGELOG.md)

## Sommaire

- [🛠 Technologies](#technologies)
- [🏗 Architecture & Design Patterns](#-architecture--design-patterns)
- [⚙️ Stratégie de Chargement (PCG)](#️-stratégie-de-chargement-pcg)
- [🌍 Environnements](#-environnements-spring-profiles)
- [🚀 Installation & Lancement](#-installation--lancement)
- [📂 Structure du Projet](#-structure-du-projet)
- [🧪 Tests unitaires](#-tests-unitaires)
- [👥 Contributeurs](#-contributeurs)
- [📄 Licence](#-licence)
- [👨‍💻 Auteur](#-auteur)

---

## Technologies

- **Langage** : Java 21 (LTS)
- **Build & gestion de dépendances** : Maven 3.9+
- **Framework** : Spring Boot 3.x (parent `spring-boot-starter-parent`)
- **JSON** : Jakarta JSON (`jakarta.json`)
- **Office Engine** : Apache POI (Lecture Excel)
- **Tests** : JUnit 5 & AssertJ (via `spring-boot-starter-test`)

---

## 🛠 Prérequis

- Java **21** installé et configuré dans le `PATH`
- Maven **3.9+** installé
- (Optionnel) Un IDE Java, par exemple **IntelliJ IDEA Community 2025.1.x**

Vérification rapide :

```bash
java -version 
mvn -version
```

---

##  📥 Installation

Cloner le dépôt :

** Fonctionnalité pas encore disponible
```bash
git clone https://example.com/mon-repo/Rv_Compta.git
cd Rv_Compta
```

Télécharger les dépendances et compiler :

```bash
mvn clean compile
```

---

## ⚙️ Compilation & exécution

### Générer le JAR 📦
```bash
mvn clean install
mvn clean package

```

Le JAR sera généré dans `target/` (par exemple `target/Rv_Compta-1.0-SNAPSHOT.jar`).

### Lancer l’application 🚀

Depuis la racine du projet :

```bash
mvn springboot:run
```

Ou directement avec le JAR :

```bash
java -jar/target/Rv_Compta-${version}.jar
```

L’application démarre alors avec la configuration Spring Boot par défaut.

---

## 🧪 Tests unitaires

Lancer tous les tests unitaires :

```bash
mvn test
```

- Tous les tests présents dans `src/test/java` seront exécutés.
- Le projet utilise JUnit 5.
- Les résultats sont visibles dans la console Maven ou dans l’IDE.

Dans **IntelliJ IDEA** :

- Clic droit sur `src/test/java` → **Run 'Tests in ...'**.
- Ou clic sur l’icône verte à côté des classes/méthodes de test.

---

---

## 🏗 Architecture & Design Patterns

Le projet adopte une structure **Hybride Orientée Domaine**, fusionnant la clarté du découpage par fonctionnalités et la rigueur d'un moteur technique centralisé.

### 1. Organisation des Packages 📦
* **`core/`** : Infrastructure transversale (Sécurité, Exceptions, Config, Utils).
* **`features/`** : Le cœur métier divisé par domaines autonomes.
  * **Controller** : Point d'entrée REST (Délégation au service).
  * **Service** : Logique métier, gestion du cache (`ConcurrentHashMap`) et stratégie de données.
  * **DTO** : Objets de transfert (ex: `hierarchy` pour l'arbre PCG, `core` pour le conteneur).
  * **Entity & Repository** : Couche de persistance Spring Data JPA.
* **`engine/`** : Moteurs techniques spécialisés (Stateless).
  * **`loader/`** : Transformation de sources externes (JSON, Excel) en DTO.
  * **`persistence/`** : Synchronisation du référentiel (Fichier vers BDD).

### 2. Principes Directeurs 🎯
* **SRP (Single Responsibility Principle)** : Séparation stricte entre la gestion du référentiel (Service) et l'extraction technique (Engine).
* **Feature Toggling** : Basculement dynamique des modes de chargement via les propriétés Spring.
* **Fail-Safe Design** : Résilience garantie par le mode `AUTO` (fallback sur fichier si la BDD est vide).

---


## ⚙️ Stratégie de Chargement (PCG)

L'application gère trois modes pilotés par la propriété `app.pcg.load-mode` :

| Mode | Description | Cas d'usage |
| :--- | :--- | :--- |
| `FILE` | Force la lecture depuis les fichiers JSON/Excel locaux. | Développement & Tests |
| `BDD` | Force la lecture depuis la base de données. | Production / Showroom |
| `AUTO` | Fallback intelligent : Priorité BDD, chargement Fichier si BDD vide. | Déploiement initial & Résilience |

---

## 🌍 Environnements (Spring Profiles)

L'API est configurée pour 4 environnements spécifiques :
- **DEV** (`application-dev.properties`) : Mode `FILE` pour une itération rapide.
- **TEST** (`application-test.properties`) : Données immuables pour la validation JUnit.
- **SHOW** (`application-show.properties`) : Démo client basée sur la BDD.
- **PROD** (`application-prod.properties`) : Mode `AUTO` pour une sécurité maximale.

--- ---

## 📂 Structure du Projet

```text
src/main/java/com/jtissdev_API/
├── core/                # Infrastructure (Config, Exceptions, Utils)
├── features/            # Modules métier
│   ├── pcg/             # Plan Comptable Général
│   │   ├── dto/         # (core, hierarchy)
│   │   ├── service/     # Logique & Cache (PcgService)
│   │   └── ...          # Controller, Repository, Entity
│   ├── pcp/             # Plan Comptable Tiers (Client, Fournisseur)
│   └── compta/          # Journaux et Écritures
├── engine/              # Automates de traitement techniques
│   ├── loader/          # Loaders (JSON, Excel via POI)
│   └── persistence/     # Updaters de base de données
└── Apps.java            # Point d'entrée Spring Boot
```


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
2. Créez une **branche** dédiée (`git checkout -b feature/nom-de-la-feature`).
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