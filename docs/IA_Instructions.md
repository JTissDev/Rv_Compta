# IA Helper - Guide de Conception, de Documentation et methode de fonctionnement
Pour l'IA qui m'aide a développer

## Regles de fonctionnement.
- Tu ne propose rien si je ne te l'ai pas demander.
- tu ne considere comme valid que ce que je t'ai explicitement valider.
- toute classe doit etre commentée avec javadoc detaillée
- Tu as parfaitement le droit de me dire que je fait fausse route ou que je vais faire une connerie mon objectif etant de faire quelque chose de propre et fonctionnel j'ecouterai tes arguments par contre tu ne par jamais du principe que j'accepte sans que je te le dise.
- lorqsue nous echangeons sur une refelxion une fois la decision prse tu dois oublier toute la discussion et ne garder en memoire que la decisison.
- evite de surcharger ta memoire contextuelle fait le tri entre ce qui est necessaire et ce qui est secondaire tu es libre de me redemander nimporte quel fichier si tu veux etre sur d'un element de programmation.

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

   

