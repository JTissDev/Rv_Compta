# 📝 Carnet de Route - Module Core

Ce fichier liste de manière granulaire les actions de refactoring, de stabilisation et les évolutions techniques cantonnées exclusivement au périmètre du module `core`.

---

## 🎯 Sprint Actuel (v0.6.0-SNAPSHOT) : Dette Technique & Localisation

### 🌐 Internationalisation (Priorité Haute)
- [ ] **Refactor Logs to English (US)** : Auditer et réécrire toutes les sorties standards et les messages d'erreur dans les repositories JSON en Anglais (US).
- [ ] **Refactor Javadocs to English (US)** : Convertir les documentations de classes et de méthodes existantes en Anglais (US).

### 📝 Complétion de la Documentation (Javadocs manquantes)
- [ ] Ajouter les en-têtes standardisés `@author J.Tiss` et `@since` sur :
  * [ ] `AbstractJsonRepository`
  * [ ] `JsonMappingException`
  * [ ] `NotInTestCondition`
  * [ ] `CoreTestSuiteTest`

### 🧹 Nettoyage du Code & Alignement Fluent API
- [ ] 🗑️ **Suppression définitive des éléments dépréciés `[OLD]`** :
  - Supprimer les anciens constructeurs multi-arguments de l'ensemble des DTOs (`OperationStatus`, `PaymentMethod`, `Tiers`, `OperationDTO`, etc.).
  - Retirer les méthodes d'auto-parsing internes de type `fromJson` obsolètes pour forcer le passage exclusif par la couche de mapping dédiée.

### ⚙️ Optimisation & Refactoring
- [ ] **Ordonnancement Chronologique** : Finaliser l'intégration des règles de séquençage `position` dans `OperationDTO` pour permettre un tri personnalisé indépendant de la date de création.
- [ ] **Refactorisation de la logique File I/O** : Extraire la gestion brute des flux (`FileInputStream`, `JsonWriter`) de `AbstractJsonRepository` vers une classe d'aide indépendante `JsonFileUtils` pour alléger le repository.

### 📊 Utilitaire d'Importation Excel
- [ ] ⚙️ **Rendre opérationnel `ExcelReader.java`** :
  - Finaliser l'intégration d'Apache POI pour la lecture `.xlsx`.
  - Implémenter le parsing sécurisé des lignes et la conversion dynamique.
  - Ajouter la gestion des exceptions pour les fichiers corrompus.
- [ ] 🧪 Écrire les cas de tests unitaires associés dans `ExcelReaderTest.java`.

---

## 🚀 Évolutions Futures & Industrialisation (v1.0+)

### 🏗️ Découplage du Mapping JSON
- [ ] 📦 **Extraction vers `jtiss-dev-utils`** : Extraire la logique fondamentale de `AbstractJsonMapper` et de `JsonMappingException` vers un module utilitaire transverse externe.
- [ ] 🗃️ **Création de `JsonStorageService`** : Isoler totalement la gestion brute des flux de fichiers afin d'éviter la redondance de code I/O.

### 🧬 Rigueur Métier & Robustesse
- [ ] 📈 **Calculateur de Balance Embarqué** : Mettre à disposition un service d'auto-vérification (`BalanceService`) calculant à la volée les totaux d'un `JournalDTO` pour identifier les opérations déséquilibrées.