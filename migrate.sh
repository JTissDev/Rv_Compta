#!/bin/bash

# Fichier généré à l'étape 1
LISTE="liste_fichiers.txt"

if [ ! -f "$LISTE" ]; then
    echo "Erreur: Fichier liste_fichiers.txt introuvable. Exécute la commande find d'abord."
    exit 1
fi

while IFS= read -r fichier; do
    echo "-----------------------------------"
    echo "Fichier trouvé : $fichier"
    read -p "Vers quel module le déplacer ? (core/compta/view/api/skip) : " mod

    if [ "$mod" != "skip" ]; then
        # Construction du chemin destination
        dest_path="$mod/src/main/java/$(echo $fichier | sed 's/src\/main\/java\///')"
        mkdir -p "$(dirname "$dest_path")"
        mv "$fichier" "$dest_path"

        # Vérification et déplacement automatique du test associé
        test_file="src/test/java/$(echo $fichier | sed 's/src\/main\/java\///' | sed 's/\.java$/Test.java/')"
        if [ -f "$test_file" ]; then
            dest_test="$mod/src/test/java/$(echo $test_file | sed 's/src\/test\/java\///')"
            mkdir -p "$(dirname "$dest_test")"
            mv "$test_file" "$dest_test"
            echo "✅ Test associé déplacé vers $dest_test"
        fi
    fi
done < "$LISTE"

echo "Migration terminée. N'oublie pas de supprimer le dossier src/ racine."