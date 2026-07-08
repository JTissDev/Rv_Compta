# 📜 Changelog - Rv_Compta

All notable changes to this project will be documented in is file.

# Modules Changelog link
> [**Module Compta**](./compta/CHANGELOG.md) |
> [**Module Core**](./core/CHANGELOG.md)
# Changelog

## [0.6] in progress

### Added
- View `Compta`, `Journal`, `Operation`, `Movement`
- View `PCP`
- View `PCG`
- View `Referential`
- OperationDTO : Add position management for sorting operation by countable date (e.g., due date, payment date) and not just by creation date.
- **Rv-Compta-View** : Integration of domain-specific CLI views (`Compta`, `Journal`, `Operation`, `Movement`, `PCP`, `PCG`, `Referential`).
- **Core Engine** : Added position-based sorting logic in `OperationDTO` to allow financial sorting independently of creation order.
- **Core Infrastructure** : Solidified `AbstractJsonRepository`, generic `CrudRepository`, and custom `JsonMappingException`.

### Changed
- **Architecture** : Fully migrated from a monolithic layout to a multi-module Maven structure (`core`, `compta`, `Rv-Compta-View`, `api-server`, `test-utils`).

### Technical Debt / Incoming
- **Internationalization** : Enforced strict rule requiring all Javadocs and log messages to be written in English (US).

## [0.5] 2026-05-06

### Added

- Logs `Banner`, `Blocs` , `suite`
- Success Test for Referential DTO: `PaymentMethod`,`OperationStatus`,`ReferentialCore`
- Success Test for PCP DTO `Contacts`,`Tiers`,`AnalyticDetais`,`PcpCoreDTO`
- Success Test for PCG DTO `AccountingType`,`SubAccountingType`,`AccountingTypeDetails`,`PcgCoreDTO`
- Success Test for Compta DTO `MovmentDTO`, `OperationDTO`

### Deprecated
- all construtors of DTOs except the default one, to enforce the use of Fluent API for better readability and maintainability.
and the one with jsonObject as parameter.
- all methods fromJson for the same reason, to centralize JSON parsing logic within the DTOs and ensure consistency across the codebase.

## [0.4.0] 2026-04-29

### Clean Up

- All DTO files.

## [0.3.0] - 2026-04-16

### Added

- Complete data structure overhaul (v0.3).
- Creation of DTOs: `JournalDTO`, `OperationDTO`, `MovementDTO`.
- Support for multi-tiers and multi-payment methods at the line level (`MovementDTO`).
- Implementation of Fluent API and exhaustive Javadoc documentation.
- Comprehensive unit tests for data structure validation.

### Changed

- Migration of Tiers and Payment Methods from Operation level to Movement level (BDD Schema sync).
- Systematic use of `BigDecimal` for financial precision.

## [0.2.0] - 2026-04-16

### ✨ Added

- Initialized the **PCP** (Personal Accounting Plan) module.
- Created `Tiers` and `Details_Comptable` DTOs with full Javadoc.
- Implemented technical loaders `TiersDataLoader` and `DetailsDataLoader` using `FileSystemResource`.
- Updated the central container `PcpCoreDTO`.
- **DTOs**: Implemented `OperationStatus` and `PaymentMethod` (including `VIRINT` code) with Fluent API.
- **DTOs**: Created `ReferentialCoreDTO` as a central container for cross-functional referentials.
- **Engine**: Developed `ReferentialDataLoader` for dynamic JSON loading.

### 🧪 Tests

- Unit test coverage for DTOs and Loaders within the PCP block.
- Unit test coverage for `OperationStatus`, `PaymentMethod`, and `ReferentialCoreDTO`.
- Unit test coverage for `ReferentialDataLoader`.
- Validated "Truth Test" (Test de Vérité) via the application's `CommandLineRunner`.

## [0.1.0] - 2026-04-14

### ✨ Added

- Defined the **Hybrid Domain-Oriented** architecture.
- Created the automatic package generation script `gen_features.sh`.
- Full `README.md` structure with 4-environment management.
- Initialized tracking files (`TODO.md`, `CHANGELOG.md`).

### 🏗️ Changed

- Centralized technical engines within the `engine/` package.
- Modularized business logic into the `features/` package.

---

> Format based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).
