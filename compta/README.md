# ⚙️ Rv_Compta - Module Compta

Le module `compta` est le **moteur principal** de l'application. Il contient toute la logique métier, le traitement des règles comptables (Plan Comptable Général), et la couche de persistance des données.

Ce module est conçu pour fonctionner en arrière-plan : il désactive intentionnellement les fonctionnalités de serveur web pour se concentrer sur le traitement pur.

---

## 🏗️ Architecture et Dépendances

Ce module s'appuie directement sur le module `core` pour récupérer les Objets de Transfert de Données (DTOs) et l'infrastructure de base.

Il gère la persistance de manière dynamique. Actuellement configuré pour utiliser des fichiers JSON, il définit des chemins relatifs adaptables à différents environnements.

## 📂 Fichiers de Configuration

La configuration (`application.properties`) met en place une structure de dossiers claire :
* **Chemin de base** : Défini dynamiquement sur le répertoire de l'utilisateur.
* **Dossier de données** : Pointant par défaut vers `./data/`.
* **Sécurité** : Intégration d'un dossier `.env` optionnel pour isoler les variables sensibles et faciliter la bascule entre les environnements de développement, de test, de démonstration et de production.

## 🗃️ Fichiers de Données Gérés
* `pcg.json` : Plan Comptable Général.
* `OperationStatuts.json` & `PaymentMethod.json` : Référentiels d'opérations.
* `Tiers.json` & `details.json` : Plan Comptable Personnel (PCP).
* `compta.json` : Journal principal.