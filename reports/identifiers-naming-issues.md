# Identifiers and naming issues (initial scan)

This initial pass lists identifiers and terms that appear to use French words or may not follow Java naming conventions. The search was heuristic-based and may be incomplete. Full enforcement should be done with Checkstyle/PMD.

Examples found and suggested replacements

- pcg / Pcg / PCG -> chartOfAccounts / ChartOfAccounts / CHART_OF_ACCOUNTS (variable/class naming)
- pcp -> personalChart or auxiliaryChart
- Tiers -> ThirdParty or Counterparty
- compta -> accounting
- mouvement / mouvements -> movement / movements or transactionLine(s)
- ecriture / ecritures -> entry / entries

Files where these terms appear (examples)
- data/PCG.json
- compta/src/main/java/com/jtissdev/features/pcg/*
- core/src/main/java/com/jtissdev/features/pcp/dto/Tiers.java

Recommendations
- Use automated refactoring where safe. For public APIs, add deprecated aliases and new names to minimize breaking changes.
- Add Checkstyle + PMD rules to CI and run across the codebase.

I will perform a fuller scan and prepare a PR that applies safe, non-breaking renames plus a suggested plan for larger refactors.