# 📝 TODO List - Rv_Compta

## 🏗️ Infrastructure & Core
- [ ] **Advanced Security Layer**:
    * Implement Spring Security with JWT or Session management.
    * Define Roles (ADMIN, USER, VIEWER) for accounting operations.
- [ ] **Robust Error Handling**:
    * Complete `GlobalExceptionHandler` to catch JSON parsing and DB integrity errors.
    * Create custom Business Exceptions (e.g., `BalancedAccountException`).
- [ ] **Environment & Profile Management**:
    * Create `application-dev.properties`, `application-test.properties`, `application-show.properties`, `application-prod.properties`.
    * Implement **Spring Bean Switching**: Use `@Profile` to alternate between `FileService` (Mock/Local) and `DatabaseService` (JPA/Production).
- [ ] **API Documentation & Developer Guide**:
    * Integrate **Swagger/OpenAPI** to auto-generate the API documentation.
    * Create a "Developer Guide" module showing standard request examples.

## 👤 User Management & Profiles
- [ ] **Multi-Profile System**:
    * Implement `UserProfile` entity with specific types: `INDIVIDUAL`, `ASSOCIATION`, and `PROFESSIONAL`.
    * Add logic to toggle business rules based on profile type (VAT, specific reports).

## 📊 Module Referential (Core)
- [x] Define `OperationStatus` and `PaymentMethod` DTOs.
- [x] Implement `ReferentialDataLoader`.
- [ ] **Metadata & Synchronization System**:
    * **Database side**: Create a `REF_METADATA_UPDATE` table (`table_name`, `last_sync_date`, `file_hash`).
    * **API side**: Implement pre-load check (Local JSON Hash vs. DB Metadata).
    * **Automated Sync**: Logic to push JSON updates to DB if metadata is obsolete.

## 📊 Module PCG (General Accounting Plan)
- [x] Define DTO structures (Core & Hierarchy).
- [x] Develop `PcgDataLoader` (JSON/Jakarta JSON).
- [ ] **Service & Cache**: Implement `PcgService` with `ConcurrentHashMap` for high-performance access.
- [ ] **API Exposure**: Create `PcgController` for REST endpoints.
- [ ] **Persistence**: Create JPA Entities and Repositories for PCG storage.

## 📊 Module PCP (Personal Accounting Plan)
- [x] Define DTO structures (Tiers & Details_Comptable).
- [x] Develop JSON Loaders.
- [ ] **PCP-PCG Bridge**: Implement Level 4 (PCP) to Level 3 (PCG) mapping validation.

## 📒 Module COMPTA (Accounting Engine)
- [ ] **Complex Data Model**: Define `Journal`, `Ecriture`, and `Mouvement`.
- [ ] **Validation & Integrity Engine**:
    * **Action Selector (Apps.java)**: Command router via `String[] args` (`--SERVER`, `--IMPORT`, `--SYNC`, `--CHECK`).
    * **Accounting Logic**: Enforce (Sum(Debit) == Sum(Credit)) balance.
    * **VIRINT Automation**: Symmetrical entry generation for internal transfers.

## ⚙️ Engine (Technical & Migration)
- [ ] **Semi-Automated Excel-to-JSON Tool**: Build generic mapper using Apache POI.
- [ ] **Progressive DB Migration**: Build step-by-step migration worker and progress tracker.
- [ ] **Smart Loading (AUTO Mode)**: Prioritize DB but fallback to Files with a "Warning" state.