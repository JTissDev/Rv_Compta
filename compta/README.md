# Compta Module README (English draft)

Module: compta
Role: Business engine implementing accounting rules, validation and persistence strategies (file / DB).

How to build & test
```bash
cd compta
mvn clean test
```

Notes
- Persistence supports FILE and DB modes; verify seed deployment and fallback logic in JsonFilePcgRepository and PcgRepository.
- Rename suggestions in reports for domain terms (pcg -> chartOfAccounts, pcp -> personalChart, tiers -> thirdParty).
