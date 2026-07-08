# 📝 Project Tracking - Rv_Compta

# Modules ToDo link
> [**Module Core**](./core/TODO.md) |

## Global RoadMap & Progress

- [ ] **Version 1.0: The Stable Console MVP**
  * **Referential & PCG/PCP Modules**:
    - [x] Define DTO structures for Core & Hierarchy. *(Validated in v0.2)*
    - [x] Develop `PcgDataLoader` (JSON/Jakarta JSON). *(Validated in v0.2)*
    - [x] Define Tiers and metadata referential structures. *(Validated in v0.1)*
    - [x] **Gap-based Sequencing**: Implement `position` logic in `OperationDTO` (intervals of 1000) for internal sorting.
    - [ ] **Real-time Balancing**: Add a `BalanceService` to calculate account totals on-the-fly from the `JournalDTO`.
  * **Accounting Engine (Core Logic)**:
    - [x] Create `OperationDTO` & `MovementDTO` with Fluent API. *(Validated in v0.3)*
    - [x] Implement global `JournalDTO` container. *(Validated in v0.3)*
    - [x] Unit Testing for DTOs and business rules. *(Validated in v0.3)*
    - [x] Implement `JournalLoader` (JSON to DTO rehydration). *(Validated in v0.4)*
    - [x] **Architecture MVC stricte** : Mise en place des couches Controllers, Services, et Mappers.
    - [x] **Infrastructure JSON** : Création et validation des `AbstractJsonMapper` et gestion des exceptions I/O.
    - [ ] **Excel Import Service**: Implement `.xlsx` / `.csv` parsing (Apache POI).
      - [ ] **Correspondence Map**
      - [ ] **Interactive Mapping**: User-defined Excel row-to-DTO mapping.
    - [ ] **Validation Engine**: Enforce (Sum(Debit) == Sum(Credit)) and VIRINT automation.
  * **Console UI & Persistence**:
    - [ ] **Final CLI**: Robust navigation, journal auditing, and reporting.
    - [ ] **JSON Serialization**: `JsonExportService` for full Journal persistence.
    - [ ] **Scripting**: Integrate `track.sh` for automated Changelog/Todo updates.
    - [ ] **Automated Scenarios**: Complete the `TestMainController` with full flow simulations (Navigation + Input).

- [ ] **Version 1.1: Environment & Profile Control**
  * [ ] **Multi-Profile Management**: Implementation of Dev, Test, Prod, Show configurations.
  * [ ] **Deployment Logic**: Dynamic switching between Local and Raspberry Pi environments (.env/.properties).
  * [ ] Ensure `.env` / `.properties` files correctly point to the JSON storage paths on both Local and Raspberry Pi.

- [ ] **Version 2.0: Relational Persistence**
  * [ ] **Database Migration**: Move from JSON files to MariaDB.
  * [ ] **Data Access Layer**: Spring Data JPA implementation.
  * [ ] **Schema Design**: Relational mapping for complex accounting hierarchies.
  * [ ] **Relational Indexing**: Map the `(date, position)` composite index in MariaDB for ultra-fast sorting.
  * [ ] **Snapshot Table**: Implement `account_balances` table to store monthly opening/closing balances (Materialized views logic).

- [ ] **Version 3.0: Architectural Modularization**
  * [ ] **Multi-Module Refactoring**: Splitting into `core`, `cli`, and `service` Maven modules.
  * [ ] **Clean Architecture**: Decoupling business logic from the delivery layer.

- [ ] **Version 3.1: The Backend Pivot (REST API)**
  * [ ] **RESTful Services**: Transformation into a Spring Boot Web API.
  * [ ] **API Documentation**: Swagger/OpenAPI integration.
  * [ ] **GUI Ecosystem**: ReactJS web and mobile apps consuming the API.

- [ ] **Version 4.0: Enterprise Security & Multi-Tenancy**
  * [ ] **User Management**: Authentication and Authorization (JWT).
  * [ ] **RBAC**: Role-Based Access Control.
  * [ ] **Audit Logging**: Tracking user actions for financial compliance.

- [ ] **Version 5.0: Association update**
  * [ ] **Multi-Type-Profil** :  `Particular`, `association`, `entreprise`
  * [ ] **Update PCG**
  * [ ] **Integrating Association constraint**
    * [ ] **Documentation**
    * [ ] **Rules**

- [ ] **Version 6.0: Entreprise update**
  * [ ] **Update PCG**
  * [ ] **Integrating Entreprise constraint**
    * [ ] **Documentation**
    * [ ] **Rules**


## Évolutions / Refactoring
- [ ] ⚙️ **Stabilisation du Mapping JSON** : Valider le fonctionnement de `AbstractJsonMapper` et de `JsonMappingException` sur l'application `Rv-Compta` actuelle.
- [ ] 📦 **Modularisation (jtiss-dev-utils)** : Extraire l'infrastructure de mapping (`AbstractJsonMapper` et son exception dédiée) pour les injecter dans un module indépendant de `jtiss-dev-utils` afin de les rendre réutilisables.
- [ ] **[Refactoring Core]** Extraire la logique d'accès aux fichiers JSON (I/O) dans une classe utilitaire dédiée (`JsonFileUtils` ou `JsonStorageService`).
  - **Objectif :** Alléger les Repositories (PCG, Referential) de la gestion bas niveau des flux (`FileInputStream`, `JsonWriter`).
  - **Méthodes prévues :** - `readObject(File)` / `readArray(File)` pour le parsing direct.
    - `write(File, JsonObject/JsonArray)` pour l'écrasement/création (remplace les `save()` actuels).
    - `update(File, JsonObject/JsonArray)` pour la modification partielle en mémoire avant réécriture (préparation V2).



# 📝 Project Tracking - Rv_Compta

## 📈 Global RoadMap & Progress

### 🔄 En cours : Version 0.6.x (Stabilisation de la Console & Dette Technique Core)
- [ ] 🌐 **Anglicisation Technique Globale** : Passer l'intégralité des messages de logs et de la Javadoc du module `core` en **Anglais (US)**.
- [ ] 📝 **Audit & Complétion Javadoc Core** : Injecter les tags obligatoires (`@author J.Tiss`, `@since`, etc.) sur les composants clés identifiés :
  * [ ] `AbstractJsonRepository`
  * [ ] `JsonMappingException`
  * [ ] `NotInTestCondition`
  * [ ] `CoreTestSuiteTest`
- [ ] 🎮 **Console UI** : Stabiliser les vues spécialisées (`Compta`, `PCP`, `PCG`, `Referential`) orchestrées par le `ConsoleMainController`.
- [ ] 🕒 **Position Management** : Finaliser la gestion de `position` dans `OperationDTO` pour le tri chronologique hors création.

### 🎯 Prochainement : Version 1.0 (The Stable Console MVP)
- [ ] **Real-time Balancing** : Finaliser le `BalanceService` pour le calcul des soldes à la volée.
- [ ] **Excel Import Service** : Finaliser le parsing via Apache POI (Correspondence Map & Interactive Mapping).
- [ ] **Validation Engine** : Implémenter la validation stricte `Sum(Debit) == Sum(Credit)` et l'automatisation du code `VIRINT`.
- [ ] **JSON Persistence** : Finaliser `JsonExportService` pour la sauvegarde complète du Journal.

### 🚀 Versions Futures
- [ ] **Version 2.0: Relational Persistence** (Migration MariaDB, Spring Data JPA, index composite `(date, position)`, tables de snapshots de balances).
- [ ] **Version 3.0: The Backend Pivot (REST API)** (Contrôleurs Spring Boot Web dans `api-server`, Swagger/OpenAPI, préparation de l'écosystème ReactJS).
- [ ] **Version 4.0: Enterprise Security** (Authentification JWT, RBAC, Audit Logging complet).
- [ ] **Version 5.0: Association Update** (Profils multi-types, contraintes métiers et règles spécifiques).
- [ ] **Version 6.0: Entreprise Update** (Extensions PCG/PCP spécifiques aux structures commerciales).

## ⚙️ Évolutions / Refactoring Techniques
- [ ] 📦 **Extraction Utils** : Extraire l'infrastructure de mapping (`AbstractJsonMapper` et son exception) du module `core` vers une dépendance indépendante `jtiss-dev-utils`.
- [ ] 💾 **Refactoring Core I/O** : Extraire la logique d'accès bas niveau des fichiers dans un utilitaire dédié `JsonFileUtils` pour soulager l' `AbstractJsonRepository`.