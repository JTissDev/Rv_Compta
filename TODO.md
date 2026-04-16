# 📝 TODO List - Rv_Compta

## 1. Current Version 0.4-SNAPSHOT (Import & JSON)

### 📒 Module COMPTA (Accounting Engine)
- [x] Implement `JournalLoader`: JSON to DTO rehydration using Jakarta JSON.
- [ ] **Excel Import Service**: Implement `.xlsx` / `.csv` parsing using Apache POI.
- [ ] **Interactive Mapping**: Create a mechanism for users to define Excel row-to-DTO mappings.
- [ ] **JSON Serialization**: Implement `JsonExportService` to transform a `JournalDTO` into a JSON file.
- [ ] **Validation & Integrity Engine**:
  * **Accounting Logic**: Enforce (Sum(Debit) == Sum(Credit)) balance.
  * **VIRINT Automation**: Symmetrical entry generation for internal transfers.

---

## 2. ✅ COMPLETED

### Version 0.3 (Data Structure Overhaul)
- [x] **Data Model Design**:
  * Create `OperationDTO`: Transaction header (Date, Label, Reference) with Fluent API.
  * Create `MovementDTO`: Atomic accounting lines (Debit/Credit, Tiers, Payment Method).
- [x] **Complex Data Model**: Define `JournalDTO` as a global container.
- [x] **Unit Testing**: Validate DTOs and their Fluent API.

### Version 0.2
#### 📊 Module PCG (General Accounting Plan)
- [x] Define DTO structures (Core & Hierarchy).
- [x] Develop `PcgDataLoader` (JSON/Jakarta JSON).

#### 📊 Module PCP (Personal Accounting Plan)
- [x] Define DTO structures (Tiers & Details_Comptable).
- [x] Develop JSON Loaders.

#### 📊 Module Referential (Core)
- [x] Define `OperationStatus` and `PaymentMethod` DTOs.
- [x] Implement `ReferentialDataLoader`.

---

## 3. Global Todo List (Backlog)
### 🏗️ Infrastructure & Core
- [ ] **Advanced Security Layer**: Spring Security with JWT/Roles.
- [ ] **Robust Error Handling**: `GlobalExceptionHandler` and Business Exceptions.
- [ ] **Environment Management**: Spring Profiles (`dev`, `prod`, etc.).
- [ ] **API Documentation**: Swagger/OpenAPI integration.

### 👤 User Management & Profiles
- [ ] **Multi-Profile System**: `INDIVIDUAL`, `ASSOCIATION`, `PROFESSIONAL` logic toggles.

### 📊 Module Referential (Core)
- [ ] **Metadata & Synchronization**: Hash-based sync between JSON files and DB.

### 📊 Module PCG (General Accounting Plan)
- [ ] **Service & Cache**: High-performance access with `ConcurrentHashMap`.
- [ ] **Persistence**: JPA Entities and Repositories.

### 📊 Module PCP (Personal Accounting Plan)
- [ ] **PCP-PCG Bridge**: Level 4 to Level 3 mapping validation.

### 📒 Module COMPTA (Accounting Engine)
- [ ] **Balance Calculator**: Real-time balance engine by account with period filtering.

### ⚙️ Engine (Technical & Migration)
- [ ] **Progressive DB Migration**: Step-by-step worker and progress tracker.
- [ ] **Smart Loading (AUTO Mode)**: DB priority with File fallback.