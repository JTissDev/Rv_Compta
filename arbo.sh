#!/bin/bash

# =========================================================
# == RV_COMPTA - Developer Toolbox                       ==
# == Author: Gemini (for J.Tiss)                         ==
# =========================================================

# --- FONCTION : Structure des fichiers (Arborescence) ---
get_tree() {
    echo ""
    echo "--- PROJECT STRUCTURE: RV_COMPTA ---"
    find . -not -path '*/.*' \
           -not -path './target*' \
           -not -path './.git*' \
           -not -path './.idea*' \
           | sed -e 's/[^-][^\/]*\// |/g' -e 's/| \([^ ]\)/|-- \1/'
    echo "------------------------------------"
}

# --- FONCTION : Mapping complet (Package > Classe > Champs > Méthodes) ---
get_details() {
    echo ""
    echo "--- FULL MAPPING (FIELDS & METHODS) : RV_COMPTA ---"

    find src/main/java -name "*.java" -exec sh -c 'echo "$(grep -m 1 "^package " "$1" | sed "s/package //;s/;//")|$1"' _ {} \; | sort | while IFS="|" read -r package file; do

        if [ "$package" != "$current_package" ]; then
            echo ""
            echo "📦 PACKAGE : $package"
            echo "================================================================"
            current_package=$package
        fi

        echo "  📍 Classe : $(basename "$file")"
        echo "    🔹 Champs :"
        grep -E '^\s*(private|public|protected)\s+[^()]+\s+\w+\s*;' "$file" | sed 's/^ */      - /'

        echo "    🔸 Fonctions :"
        grep -E '^\s*(public|private|protected|static).*\(' "$file" | grep -v ";" | sed 's/ {.*//' | sed 's/^ */      - /'

        echo "  --------------------------------------------------------------"
    done
}

# --- LOGIQUE DE TRAITEMENT ---
ACTION=$1  # show | print
TARGET=$2  # tree | details

case "$ACTION" in
    show)
        if [ "$TARGET" == "tree" ]; then get_tree;
        elif [ "$TARGET" == "details" ]; then get_details;
        else echo "Cible inconnue. Usage: ./arbo.sh show {tree|details}"; fi
        ;;
    print)
        REPORT_FILE="report_${TARGET}.txt"
        echo "📄 Génération du rapport dans $REPORT_FILE..."
        if [ "$TARGET" == "tree" ]; then get_tree > "$REPORT_FILE";
        elif [ "$TARGET" == "details" ]; then get_details > "$REPORT_FILE";
        else echo "Cible inconnue. Usage: ./arbo.sh print {tree|details}"; exit 1; fi
        echo "✅ Terminé."
        ;;
    *)
        echo "Usage: ./arbo.sh {show|print} {tree|details}"
        exit 1
        ;;
esac