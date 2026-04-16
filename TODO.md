# 📝 TODO List - Rv_Compta

Liste des tâches à accomplir pour le développement de l'API.

## 🏗️ Infrastructure & Core
- [ ] Finaliser la configuration de sécurité (Spring Security).
- [ ] Implémenter le `GlobalExceptionHandler` dans `core.exception`.
- [ ] Configurer les profils Spring (`application-dev.yml`, etc.).

## 📊 Module PCG (Plan Comptable Général)
- [x] Définir la structure des DTO (Core & Hierarchy).
- [ ] Créer le `PcgService` avec gestion du cache (`ConcurrentHashMap`).
- [ ] Implémenter le `PcgController` pour l'exposition REST.
- [x] Développer le `PcgDataLoader` (Lecture JSON via Jakarta JSON).

## 📊 Module PCP (Plan Comptable Particulier)
- [x] Définir la structure des DTO (Tiers & Details_Comptable).
- [x] Développer les Loaders JSON (`TiersDataLoader`, `DetailsDataLoader`).
- [ ] Créer le `PcpService` pour la gestion des données en mémoire.
- [ ] Implémenter le `PcpController` pour l'exposition REST.
- [ ] Implémenter la logique de liaison entre le Niveau 4 (PCP) et le Niveau 3 (PCG).

## ⚙️ Engine (Moteurs techniques)
- [ ] Finaliser l'intégration d'Apache POI pour le `XlsDataLoader`.
- [ ] Créer le `PcgDbUpdater` pour la synchronisation Fichier -> BDD.
- [ ] Implémenter la logique `AUTO` (Fallback intelligent).

## 🧪 Tests & Qualité
- [ ] Écrire les tests unitaires pour la désérialisation de l'arbre PCG.
- [x] Écrire les tests unitaires pour le bloc PCP.
- [ ] Tester les bascules de profils (DEV/PROD/SHOW).