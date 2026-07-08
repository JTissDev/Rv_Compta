# Rv_Compta (English draft)

Rv_Compta is a modular, resilient, high-performance Java accounting engine implementing double-entry bookkeeping. It targets both individual and small business use-cases and provides a console UI, data loaders, and a REST API pivot for integration.

Stack
- Language: Java 21
- Build: Maven (multi-module)
- Framework: Spring Boot 3.x
- Notable libraries: Jakarta JSON, Apache POI, JUnit 5

Modules
- core: DTOs, JSON mapping, low-level utilities
- compta: business rules, journal processing, persistence
- Rv-Compta-View: console-based UI and controllers (user prompts intentionally French)
- api-server: REST entry point (Spring Boot)

How to build & run (short)
```bash
# build and tests
mvn -T 1C clean test

# run API server module
mvn -pl api-server spring-boot:run

# run console view (from IDE or mvn exec if configured)
```

Testing & Coverage
- Run: mvn clean test
- Generate JaCoCo report: mvn jacoco:report

Policy highlights
- All Javadocs and log messages MUST be in English (US).
- User-facing CLI prompts may remain in French per project decision.
- Naming conventions: follow Java standard (UpperCamelCase for classes, lowerCamelCase for methods/variables, UPPER_SNAKE_CASE for constants).

Contributing
- Small, focused PRs. Docs-first for language changes.
- CI will enforce Checkstyle/PMD and Javadoc checks (being added).

For details see /reports in this branch.