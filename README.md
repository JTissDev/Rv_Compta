# Rv_Compta

API Java de gestion comptable modulaire, résiliente et haute performance.

> 📑 **Liens rapides :**
> [**Consulter la TODO List**](./TODO.md) |
> [**Voir le Changelog**](./CHANGELOG.md)

## Sommaire
- [🎯 Objectifs & Vision Métier](#-objectifs--vision-métier)
- [🛠 Technologies](#technologies)
- [🏗 Architecture & multi-modules](#-architecture--multi-modules)
- [⚙️ Stratégie de Chargement (PCG)](#️-stratégie-de-chargement-pcg)
- [🌍 Environnements & Configuration](#-environnements--configuration)
- [🚀 Installation & Lancement](#-installation--lancement)
- [🧪 Tests unitaires](#-tests-unitaires)
- [🤝 Contribution](#-contribution)
- [👨‍💻 Auteur](#-auteur)

---

## 🎯 Objectifs & Vision Métier
**Rv_Compta** a pour ambition de démocratiser la rigueur de la comptabilité professionnelle en proposant un moteur de comptabilité en partie double universel, adapté aux **Particuliers**, **Associations** et **Entreprises**.

### 1. Orientations Métiers (Le Cœur du Projet)
* **Comptabilité en Partie Double Universelle** : Application systématique du principe de la double écriture (débit/crédit) pour tous les profils.
* **Articulation Pivot PCG / PCP** :
    * **PCG (Plan Comptable Général)** : Socle standard immuable déterminant la *nature* de l'opération (Qu'est-ce qui est payé/encaissé ?).
    * **PCP (Plan Comptable Particulier)** : Extension analytique/auxiliaire universelle permettant de *détailler* le PCG en liant le mouvement à une entité concrète (tiers, bien immobilier, projet).
* **Ventilation Avancée** : Prise en charge d'écritures complexes multi-lignes.

### 2. Engagements Techniques
* **Norme Javadoc & Logs** : Strictement rédigés en **Anglais (US)** (Auteur : `J.Tiss` | Email : `jtissdev@gmail.com`).
* **Résilience & Fail-Safe** : Stratégie de chargement auto-adaptative (Fichier/BDD).

---

## Technologies
- **Langage** : Java 21 (LTS)
- **Build** : Maven 3.9+ (Architecture Multi-Modules)
- **Framework** : Spring Boot 3.x
- **JSON** : Jakarta JSON (`jakarta.json`)
- **Office Engine** : Apache POI (Lecture Excel)
- **Tests** : JUnit 5 & AssertJ

---

## 🏗 Architecture & Multi-Modules 📦

Le projet est structuré sous la forme d'un monorepo multi-modules Maven pour garantir une étanchéité stricte des composants :

* **`core`** : Colonne vertébrale infrastructurelle. Contient les DTOs globaux (`PcgCoreDTO`, `PcpCoreDTO`, `JournalDTO`), les exceptions techniques (`JsonMappingException`), le moteur de mapping abstrait (`AbstractJsonMapper`) et le socle de persistance (`CrudRepository`, `AbstractJsonRepository`).
* **`compta`** : Moteur métier lourd (Services de validation, logique d'écriture, loaders locaux et traitement `DbUpdater`).
* **`Rv-Compta-View`** : Interface utilisateur en mode console textuelle (Vues et contrôleur principal CLI).
* **`api-server`** : Point d'entrée Web REST Spring Boot (Futur pivot de l'écosystème).
* **`test-utils`** : Outillage de test et injecteurs de jeux de données.

---

## ⚙️ Stratégie de Chargement (PCG)
L'application gère trois modes pilotés par la propriété `app.pcg.load-mode` :

| Mode | Description | Cas d'usage |
| :--- | :--- | :--- |
| `FILE` | Force la lecture depuis les fichiers JSON locaux (`storage/`). | V1 (MVP Console) & Dev |
| `BDD` | Force la lecture depuis la base de données MariaDB. | V2 (Production) |
| `AUTO` | Fallback intelligent : Priorité BDD, bascule sur FILE si inaccessible. | Résilience / Fail-Safe |

---

## 🌍 Environnements & Configuration
La configuration s'appuie sur 4 profils Spring isolés dans le dossier `.env/` :
* **DEV** (`application-dev.properties`) : Mode `FILE`, stockage JSON local.
* **TEST** (`application-test.properties`) : Environnement JUnit léger avec isolation des données de test.
* **SHOW** (`application-show.properties`) : Mode démo forcé sur base de données.
* **PROD** (`application-prod.properties`) : Mode `AUTO` auto-hébergé sur Raspberry Pi.

---

## 🧪 Tests unitaires
Exécution globale depuis la racine :
```bash
mvn clean test
```

## 👨‍💻 Auteur
**JtissDev** (`J.Tiss` | `jtissdev@gmail.com`) - *Architecture & Développement*

#### 2. `TODO.md` (Mis à jour : Intégration du chantier d'anglicisation et correction de la Roadmap)

