#!/bin/bash

# =========================================================
# == RV_COMPTA - Developer Toolbox                       ==
# == Author: Gemini (for J.Tiss)                         ==
# =========================================================

# --- FONCTION 1 : Affichage de l'arborescence ---
show_tree() {
    echo ""
    echo "--- PROJECT STRUCTURE: RV_COMPTA ---"
    find . -not -path '*/.*' \
           -not -path './target*' \
           -not -path './.git*' \
           -not -path './.idea*' \
           | sed -e 's/[^-][^\/]*\// |/g' -e 's/| \([^ ]\)/|-- \1/'
    echo "------------------------------------"
}

# --- FONCTION 2 : Mapping des méthodes Java ---
show_methods() {
    echo ""
    echo "--- MAPPING DES METHODES : RV_COMPTA ---"
    find src/main/java -name "*.java" | while read -r file; do
        echo ""
        echo "📍 Classe : $(basename "$file")"
        echo "------------------------------------------------"
        # Extrait les signatures (public/private/protected/static)
        grep -E '^\s*(public|private|protected|static).*\(' "$file" | \
        grep -v ";" | \
        sed 's/ {.*//' | \
        sed 's/^ *//'
    done
    echo "----------------------------------------"
}

# --- LOGIQUE D'APPEL ---
case "$1" in
    tree)
        show_tree
        ;;
    methods)
        show_methods
        ;;
    all)
        show_tree
        show_methods
        ;;
    *)
        echo "Usage: ./rv_tools.sh {tree|methods|all}"
        exit 1
        ;;
esac