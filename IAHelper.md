 Pour mon projet Compta

## Regles de fonctionnement.
- Tu ne propose rien si je ne te l'ai pas demander.
- tu ne considere comme valid que ce que je t'ai explicitement valider.
- toute classe doit etre commentée avec javadoc detaillée 

### 📝 Règles de Documentation Javadoc (RV_Compta Standard)
 1. Bloc d'Entête de Classe
Chaque classe commence par une description conceptuelle suivie de détails techniques sur sa structure (ex: hiérarchie, comportement des codes).

Tags obligatoires (dans cet ordre) :

``` 
@author jtiss (ou J.Tiss selon la version, à harmoniser).

@since 0.1 (indique la version de création).

@version 1.0.0 (indique la révision actuelle).
```

2. Séparation par Sections (Visual Markers)
   Utilisation de bannières de commentaires pour segmenter la classe de manière lisible :

```Java
// =========================================================
// == FIELDS                                              ==
// =========================================================
```
Sections types : FIELDS, CONSTRUCTORS, GETTERS / SETTERS, METHODS.

3. Documentation des Champs (Fields)
   Chaque champ privé doit avoir sa Javadoc :

Description concise du rôle de la donnée.

Tag @since correspondant à l'ajout du champ.

4. Documentation des Méthodes
   Description : Ce que fait la méthode.

Contrat : Utilisation de {@code ...} pour les valeurs techniques ou {@link ...} pour référencer d'autres classes.

Tags :

@param : Nom et description du paramètre.

@return : Description de la valeur de retour (mentionner "this instance for chaining" pour l'API Fluent).

@since : Version d'introduction.

🏗️ Structure de Code & Design
1. API Fluent (Chaînage)
   Tous les setters doivent retourner l'instance de l'objet pour permettre l'écriture fluide :

```Java
public Type_Comptable setName(String name) {
this.name = name;
return this;
}
```

Constructeurs font appel aux setters.
2. Logique de Mapping JSON (Jakarta)
   Utilisation systématique de JsonObjectBuilder et JsonArrayBuilder pour l'export. Les méthodes toJson() doivent être documentées en précisant qu'elles servent à la représentation JSON.

3. Override Standard
   toString() : Doit être surchargé avec une structure claire (StringBuilder), incluant les indentations pour faciliter le débuggage dans les logs.

## Algorithme en cours de travail.

📑 Algorithme : Importation & Ventilation Interactive (v0.4)
Étape 1 : Initialisation du contexte
Démarrage : Le CommandeWorker reçoit l'ordre d'importation.

Chargement des référentiels :

Chargement du Journal existant via JournalLoader (pour ne pas écraser l'existant).

Chargement du PCG/PCP (pour la navigation dans les types).

Chargement du Référentiel Tiers (pour l'assignation).

Lecture Source : Ouverture du fichier Excel via ExcelService.

Étape 2 : Boucle de lecture "Intelligente"
Pour chaque ligne du fichier Excel :

Vérification du statut : Si la colonne "Statut" contient déjà "VENTILE", passer à la suivante.

Extraction Entête :

dateOperation = Colonne Date.

dateComptable = Colonne Date Valeur.

descriptif = Colonne Libellé.

Création de l'objet : Instanciation d'un OperationDTO avec ces valeurs.

Étape 3 : Génération de la Ligne 1 (Automatique)
Cible : Compte Banque (Code 512, Détail .442).

Logique de montant :

Si Débit Excel > 0 alors Ligne1.creditAmount = Débit Excel.

Si Crédit Excel > 0 alors Ligne1.debitAmount = Crédit Excel.

Ajout : L'opération reçoit son premier MovementDTO.

Étape 4 : Boucle de Ventilation (Interface Utilisateur)
Tant que Somme(Débits) != Somme(Crédits) :

Navigation Hiérarchique :

Affichage des Type_Comptable. L'utilisateur choisit.

Affichage des Sub_Type_Comptable associés. L'utilisateur choisit.

Affichage des Detail (PCP). L'utilisateur choisit le code final.

Enrichissement :

Demande : "Complément de libellé ?" (Optionnel).

Demande : "Sélection du Tiers ?" (Recherche par ID ou Nom).

Moyen de Paiement :

Analyse du libellé (Ex: "VIR" -> Virement).

Si Virement : Question "Interne ou Externe ?".

Calcul du Montant :

Le système affiche le reste à équilibrer.

L'utilisateur valide le montant suggéré OU saisit un montant inférieur (ce qui relance la boucle pour une nouvelle ligne).

Étape 5 : Persistance et Sécurisation
Une fois l'opération équilibrée et validée :

Commit Journal : Ajout de l' OperationDTO finale au JournalDTO.

Sauvegarde JSON : Réécriture immédiate du fichier JSON (sécurité en cas de coupure).

Marquage Excel : ExcelService écrit "VENTILE" sur la ligne traitée dans le fichier .xlsx.

