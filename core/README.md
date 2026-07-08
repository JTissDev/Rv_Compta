# 📦 Rv_Compta - Module Core

Ce module agit comme la colonne vertébrale de l'application **Rv_Compta**. Il centralise tous les objets de transfert de données (DTOs), les contrats de persistance de base, la gestion des exceptions techniques et les utilitaires communs.

> 📑 **Liens rapides :**
> [**Consulter la TODO List**](./TODO.md) | [**Consulter le Changelog**](./CHANGELOG.md)

---

## 📑 Sommaire
1. [Rôle Architectural](#1-rôle-architectural)
2. [Composants Principaux & Responsabilités](#2-composants-principaux--responsabilités)
3. [Règles Techniques Strictes](#3-règles-techniques-strictes)

---

## 1. Rôle Architectural
Le module `core` est conçu pour n'avoir **aucune dépendance externe** vers les autres modules du projet (défini dans le `pom.xml`). Son but strict est de fournir l'infrastructure de base et les structures de données aux modules supérieurs comme `compta`, `Rv-Compta-View`, et `api-server`.

## 2. Composants Principaux & Responsabilités

### A. Modèles & Objets de Transfert (DTOs)
Contient les modèles structurels absolus du système, construits avec un design **Fluent API** strict pour garantir l'immutabilité et la lisibilité :
* **`PcgCoreDTO`** : Structure centrale cartographiant le *Plan Comptable Général* (Nature des transactions).
* **`PcpCoreDTO`** : Extension gérant le *Plan Comptable Particulier* (Détails analytiques/auxiliaires comme les Tiers, Biens immobiliers, Projets).
* **`JournalDTO`** : Conteneur central gérant les opérations comptables et les mouvements multi-lignes.
* **`OperationDTO` & `MovementDTO`** : Représentations atomiques de bas niveau des transactions financières.

### B. Couche de Persistance de Base
* **`CrudRepository<T, ID>`** : Contrat générique pour les opérations CRUD de base (Save, Find, Delete), abstrayant les implémentations de stockage.
* **`AbstractJsonRepository<T, ID>`** : Base technique gérant la sérialisation/désérialisation JSON brute, automatisant la création de l'arborescence des dossiers si manquante.

### C. Utilitaires & Transversalité
* **`AbstractJsonMapper` & `JsonMappingException`** : Pipeline technique centralisé utilisant Jakarta JSON pour des transitions Java-JSON résilientes.
* **`MainController`** : Contrat d'entrée générique pour l'amorçage de l'application (méthode `run()`).
* **`ExcelReader`** : Utilitaire basé sur Apache POI pour l'importation de données depuis des fichiers `.xlsx`.

### D. Assurance Qualité & Logs
* **`CoreTestSuiteTest`** : Orchestre l'exécution unifiée de tous les tests unitaires des DTOs et de la sérialisation du module `core`.
* **`logback-test.xml`** : Configuration spécialisée des logs de test routant les traces verbeuses vers `test-execution.log` pour garder la console de build propre.
* **`NotInTestCondition`** : Bascules d'exécution conditionnelle des composants selon la détection de l'environnement JUnit.

## 3. Règles Techniques Strictes
* 🌐 **Exigence Linguistique** : Toutes les Javadocs et les messages de logs générés à l'intérieur de ce module **DOIVENT** être rédigés en **Anglais (US)**.
* 👨‍💻 **Métadonnées Javadoc** : Chaque nouvelle classe doit inclure l'en-tête standard de l'auteur du projet :
  ```java
  /**
   * @author J.Tiss
   * @since Parent-Version (e.g., 0.6.0-SNAPSHOT)
   * @version Component-Version
   * @email jtissdev@gmail.com
   */
  ```