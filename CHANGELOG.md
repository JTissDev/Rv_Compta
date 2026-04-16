# 📜 Changelog - Rv_Compta

All notable changes to this project will be documented in this file.

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