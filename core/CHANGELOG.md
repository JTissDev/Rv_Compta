# 📜 Journal des Modifications - Module Core

Toutes les modifications notables apportées au module **core** de l'application Rv_Compta sont répertoriées dans ce fichier.

---

## [0.6.0-SNAPSHOT] - En cours

### ✨ Ajouté
- **Infrastructure de Persistance** : Ajout du contrat générique `CrudRepository` et de la base abstraite `AbstractJsonRepository` pour supporter le stockage JSON fluide.
- **Gestion fine du positionnement** : Ajout de la propriété `position` dans `OperationDTO` pour gérer l'ordonnancement interne des opérations partageant une même date comptable (intervalles de 1000).
- **Vues Domaines intégrées** : Préparation du contexte de sélection (`SelectionContext`) et des structures de données pour alimenter les composants d'affichage console.
- **Suite de Validation** : Implémentation de `CoreTestSuiteTest` pour regrouper tous les flux de tests de sérialisation des DTOs localisés.
- **Gestion des Logs** : Séparation des sorties de tests via `logback-test.xml` pour nettoyer la console de compilation.

### 🛠 Modifié
- **Architecture** : Séparation des modèles core dans un sous-module Maven dédié (`core`), isolé des services métiers, actant la fin du monolithe.

### ⚠️ Dette Technique & Dépréciations
- **Mise à jour des Politiques** : Instauration d'une règle stricte imposant l'Anglais (US) pour toutes les Javadocs et déclarations de logs à venir.
- **Constructeurs obsolètes `[OLD]`** : Marquage en `@Deprecated` de tous les constructeurs multi-arguments des DTOs pour forcer le design **Fluent API**.
- **Méthodes d'auto-parsing** : Dépréciation des méthodes de désérialisation directe `fromJson` incluses historiquement dans les DTOs.

---

## [0.5.0] - 2026-05-06

### 🛠 Modifié
- **Standardisation Fluent API** : Uniformisation de l'ensemble des structures de données (DTOs) pour se conformer au pattern Fluent (chaînage des setters).

### 🧪 Tests
- **Couverture exhaustive** : Validation complète via des tests unitaires dédiés sur l'ensemble des blocs DTO : `Referential`, `PCP`, `PCG` et `Compta`.

---

## [0.4.0] - 2026-04-29

### ✨ Ajouté
- **Mapping Data** : Création de `DataMapper` et `AbstractJsonMapper` centralisant la conversion des flux JSON.

### 🧹 Nettoyage
- Remaniement global et nettoyage de la structure interne de l'intégralité des classes DTOs.

---

## [0.3.0] - 2026-04-16

### ✨ Ajouté
- **Moteur Comptable** : Refonte de la structure de données avec l'apparition de `JournalDTO`, `OperationDTO` et `MovementDTO`.
- **Alignement BDD** : Descente des entités `Tiers` et `PaymentMethod` au niveau des mouvements comptables (lignes) plutôt qu'à l'entête de l'opération.

---

## [0.2.0] - 2026-04-16

### ✨ Ajouté
- **Initialisation du bloc PCP** : Création des DTOs `Tiers` et `AnalyticDetail`.
- **Référentiel Transverse** : Implémentation de `OperationStatus`, `PaymentMethod` (incluant le code `VIRINT`), et du conteneur unifié `ReferentialCoreDTO`.

---

## [0.1.0] - 2026-04-14

### ✨ Ajouté
- **Architecture d'Origine** : Définition de la structure logicielle Hybride Orientée Domaine au sein du projet.
- **Initialisation du Projet** : Création du squelette du module `core` accueillant les premières définitions structurelles (Exceptions, Repositories).