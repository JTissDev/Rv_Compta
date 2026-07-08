# 🎯 Carnet de Route - Module Compta

Ce fichier liste les évolutions techniques et fonctionnelles prévues pour le moteur métier.

---

## 🚀 Chantiers Prioritaires

### 🏗️ Consolidation de l'Architecture MVC
- [ ] **Création des Services Métiers manquants** :
    - Implémenter `JournalService.java` et `OperationService.java` pour encapsuler la logique de traitement (actuellement gérée côté console).
    - Assurer la validation des équilibres Débit/Crédit lors de la création d'une opération avant sa persistance.

### 🌍 Gestion des Environnements
- [ ] **Déclinaison des profils Spring** :
    - Créer des fichiers de configuration spécifiques (ex: `application-dev.properties`, `application-test.properties`).
    - S'assurer que le profil `dev` pointe vers un dossier de données factices pour sécuriser les fichiers de production.

---

## 📦 Évolutions Futures

### 🗄️ Transition Base de Données
- [ ] Préparer les interfaces de `Repository` pour supporter une future implémentation relationnelle (MariaDB).

### 🛠️ Amélioration de l'Expérience de Code
- [ ] Intégrer Lombok dans le `pom.xml` pour alléger les entités et utiliser `@Accessors(chain = true)`.