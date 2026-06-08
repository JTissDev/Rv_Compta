# Rv Compta

## Présentation

Ce projet a pour vocation de devenir un utilitaire de gestion de comptabilité universel, utilisable avec la même rigueur
par les particuliers, les associations et les entreprises.

**Principes fondamentaux et Fonctionnalités :**

- **Comptabilité en partie double universelle** : Application stricte de ce principe pour tous les profils afin de
  garantir une traçabilité financière absolue.

- **Architecture PCG / PCP** :
    - *Un Plan Comptable Général (PCG) adapté sera généré pour chaque type d'utilisateur.*

    - *Un Plan Comptable Particulier (PCP)*
      permettra aux utilisateurs de créer des comptes personnalisés et analytiques
      (gestion fine des tiers, suivi par projet ou par bien).

- **Ventilation avancée des écritures** :
  Une opération globale peut être éclatée en de multiples mouvements.
  Cela permet de répartir une transaction sur différents comptes,
  de gérer des paiements multi-moyens, de suivre des dettes (totales ou partielles),
  ou encore de gérer des transactions impliquant plusieurs tiers simultanément.

- **Assistant d'importation** : Un système d'importation (ex: relevés bancaires) couplé à une boucle de ventilation
  interactive pour aider l'utilisateur à qualifier et équilibrer ses opérations de manière semi-automatique.

- **Restitution et Suivi** :
    - *Génération des états financiers de synthèse (Balance, Grand Livre, etc.)*.

    - *Intégration future d'un module complet de gestion et de suivi de budget.*

## Étapes de production

* **V1 - Le Socle Autonome (MVP) :** Code fonctionnel permettant de créer des opérations, de les ventiler interactivement, de les enregistrer et de générer des états financiers de base (Balance, Grand Livre). Interface utilisateur en mode console. Sauvegarde *Fail-Safe* au format JSON.
* **V2 - La Persistance Robuste :** Bascule du stockage vers une base de données relationnelle (ex: MariaDB) grâce à l'abstraction des Repositories.
* **V3 - Le Découplage (Headless) :** Séparation stricte du moteur métier central et de l'interface console (qui devient un module d'affichage externe).
* **V4 - L'Ouverture API :** Transformation du moteur métier autonome en API REST prête à communiquer avec des clients externes.
* **V5 - Les Nouvelles Vues :** Création d'une interface utilisateur graphique (GUI Web) et d'une application mobile venant consommer l'API.
* **V6 - L'Expansion Multi-Profils :** Déploiement de la logique Particulier, Association et Entreprise (génération de PCG adaptés). Application stricte d'une **architecture mono-profil par base de données** pour garantir l'isolation des accès.
* **V7 - L'Architecture Multi-Modules :** Découpage physique du backend en sous-modules indépendants :
  * Module de base (Comptes, Opérations, États financiers).
  * Module d'importation (Assistant d'import et de ventilation).
  * Module de suivi (Gestion et prévision budgétaire).

## Socle Technique et Architectural

Afin de garantir la robustesse, l'évolutivité et la maintenabilité de **Rv Compta**, le projet s'appuie sur des choix techniques stricts et une architecture modulaire.

**1. Stack Technologique**
* **Langage & Framework :** Java 21 (LTS) propulsé par Spring Boot 3.x pour la gestion de l'injection de dépendances et de la configuration globale.
* **Gestion des dépendances :** Maven.
* **Manipulation des données :** Jakarta JSON pour la sérialisation/désérialisation et Apache POI pour l'extraction de données complexes (moteur d'importation Excel).

**2. Architecture Hybride Orientée Domaine**
* Le code est découpé de manière étanche par modules métiers verticaux (`compta`, `pcg`, `pcp`, `referential`).
* Chaque module possède sa propre pile complète : Objets de Transfert de Données (DTO), Mappers, Repositories (Accès aux données) et Services (Logique métier).
* Les automates techniques (chargement de fichiers, orchestration de l'importation) sont isolés dans un moteur indépendant (`engine`).

**3. Stratégie de Persistance & Sécurité**
* **Évolution fluide (Template Method Pattern) :** L'accès aux données est abstrait via des interfaces (`CrudRepository`). Cela permet au moteur de fonctionner sur des fichiers JSON locaux (V1) et de basculer sur une base de données sans modifier la logique métier centrale (V2).
* **Auto-Seeding :** Capacité du système à s'auto-initialiser avec des données de référence (statuts, moyens de paiement, PCG de base) dès le premier lancement.
* **Multi-Tenancy (Multi-Profils) :** Sécurité absolue garantie par une architecture "Une base de données par entité/profil". Les données d'une association et d'un particulier ne se croiseront jamais techniquement.

**4. Design Patterns et Standards de Code**
* **API Fluent :** Tous les objets de données permettent le chaînage de méthodes pour une instanciation et une lecture claires.
* **Logique Métier Encapsulée :** Les règles financières (ex: vérification de l'équilibre Débit/Crédit) sont portées directement par les objets de données (DDD - Domain Driven Design), garantissant le principe de *Fail-Safe*.