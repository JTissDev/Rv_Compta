# Logs - Non-English occurrences (initial scan)

I ran an automated lexical scan for logger.* occurrences and inspected the results. This initial pass found several log messages written in French. The search may be incomplete — see the GitHub search link to explore more results: https://github.com/JTissDev/Rv_Compta/search?q=logger.

Findings (file : excerpt -> suggested replacement)

- compta/src/main/java/com/jtissdev/features/pcg/mapper/PcgDataMapper.java
  - "pcg loaded \n JSON Array size: " -> "PCG loaded. JSON array size: {}"
  - "Failed to load pcg resource : " -> "Failed to load PCG resource: {}"

- Rv-Compta-View/src/main/java/com/jtissdev/controller/TestMainController.java
  - "--- TOUS LES SCÉNARIOS TERMINÉS ---" -> "--- ALL SCENARIOS COMPLETED ---"
  - "SCÉNARIO : Entrée dans le journal et retour" -> "SCENARIO: Entering journal and returning"
  - "SCÉNARIO : Insertion d'une ligne en position 3" -> "SCENARIO: Insert line at position 3"
  - "> Test Affichage basiques..." -> "> Basic display tests..."
  - "> Test Affichage du Bloc pcg..." -> "> Test display of PCG block..."
  - "> Chargement du Bloc pcp..." -> "> Loading PCP block..."

- compta/src/main/java/com/jtissdev/features/referential/mapper/ReferentialDataMapper.java
  - Uses French key names when mapping (e.g., obj.getString("nom", null)) — consider mapping to English property names or document transformation.

- compta/src/main/java/com/jtissdev/features/compta/repository/JsonFileJournalRepository.java
  - "Créated missing storage directory" log already English in code but other comments are French. Ensure comments do not appear in logs.

- core/src/main/java/com/jtissdev/features/core/mapper/AbstractJsonMapper.java
  - "Expected a JSON array but found an object. Attempting to extract array from 'data' key." (already English) — keep as is.

- test-utils/src/main/java/com/jtissdev/utils/TestResultLogger.java
  - Several test run header lines are in French (e.g., "DÉMARRAGE GLOBAL DE LA SÉQUENCE DE TESTS", "ÉCHEC", "SUCCÈS"). Proposed replacements provided in separate column in the CSV report when generated.

Next steps for logs
1. Run a repository-wide grep to capture all logger.* occurrences and filter by non-ASCII/french words. Example command I used as heuristic: `grep -RIn --include="*.java" -E 'logger\.(trace|debug|info|warn|error)\(|System\.out\.println\('`
2. For each occurrence produce a patch replacing French messages with English US versions and keep variables/placeholders intact (use {} placeholders for SLF4J).

Note: This is an initial scan; more occurrences will be found when searching for accented characters or French keywords. I will run a deeper scan next and prepare the patch PRs.