# 📜 Changelog - Rv_Compta

thAll notable changes to this project will be documented in is file.

# Changelog

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