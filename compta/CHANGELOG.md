# 📜 Journal des Modifications - Module Compta

Toutes les modifications notables apportées au moteur métier de Rv_Compta sont documentées ici.

---

## [0.6.0-SNAPSHOT] - En cours

### ✨ Ajouté
- **Configuration de la Persistance** : Mise en place des propriétés dynamiques pour le stockage JSON (`rvcompta.storage.type=json`).
- **Gestion des Chemins** : Implémentation de la logique d'assemblage des chemins pour le dossier `./data/` et les fichiers de référentiels associés (`pcg.json`, `compta.json`, etc.).
- **Sécurisation des variables** : Ajout du support optionnel pour le répertoire `.env/` dans l'import de configuration.

### ⚙️ Technique
- **Dépendance Core** : Liaison établie avec le socle technique `com.jtissdev-API:core`.
- **Désactivation Web** : Le module est officiellement configuré comme `spring.main.web-application-type=none` pour agir exclusivement comme un moteur de services.