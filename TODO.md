# 📝 Project Tracking - Rv_Compta

## 4. Global RoadMap & Progress

- [ ] **Version 1.0: The Stable Console MVP**
  * **Referential & PCG/PCP Modules**:
    - [x] Define DTO structures for Core & Hierarchy. *(Validated in v0.2)*
    - [x] Develop `PcgDataLoader` (JSON/Jakarta JSON). *(Validated in v0.2)*
    - [x] Define Tiers and metadata referential structures. *(Validated in v0.1)*
  * **Accounting Engine (Core Logic)**:
    - [x] Create `OperationDTO` & `MovementDTO` with Fluent API. *(Validated in v0.3)*
    - [x] Implement global `JournalDTO` container. *(Validated in v0.3)*
    - [x] Unit Testing for DTOs and business rules. *(Validated in v0.3)*
    - [x] Implement `JournalLoader` (JSON to DTO rehydration). *(Validated in v0.4)*
    - [ ] **Excel Import Service**: Implement `.xlsx` / `.csv` parsing (Apache POI).
    - [ ] **Interactive Mapping**: User-defined Excel row-to-DTO mapping.
    - [ ] **Validation Engine**: Enforce (Sum(Debit) == Sum(Credit)) and VIRINT automation.
  * **Console UI & Persistence**:
    - [ ] **Final CLI**: Robust navigation, journal auditing, and reporting.
    - [ ] **JSON Serialization**: `JsonExportService` for full Journal persistence.

- [ ] **Version 1.1: Environment & Profile Control**
  * [ ] **Multi-Profile Management**: Implementation of Dev, Test, Prod, Show configurations.
  * [ ] **Deployment Logic**: Dynamic switching between Local and Raspberry Pi environments (.env/.properties).

- [ ] **Version 2.0: Relational Persistence**
  * [ ] **Database Migration**: Move from JSON files to MariaDB.
  * [ ] **Data Access Layer**: Spring Data JPA implementation.
  * [ ] **Schema Design**: Relational mapping for complex accounting hierarchies.

- [ ] **Version 3.0: Architectural Modularization**
  * [ ] **Multi-Module Refactoring**: Splitting into `core`, `cli`, and `service` Maven modules.
  * [ ] **Clean Architecture**: Decoupling business logic from the delivery layer.

- [ ] **Version 3.1: The Backend Pivot (REST API)**
  * [ ] **RESTful Services**: Transformation into a Spring Boot Web API.
  * [ ] **API Documentation**: Swagger/OpenAPI integration.
  * [ ] **GUI Ecosystem**: ReactJS web and mobile apps consuming the API.

- [ ] **Version 4.0: Enterprise Security & Multi-Tenancy**
  * [ ] **User Management**: Authentication and Authorization (JWT).
  * [ ] **RBAC**: Role-Based Access Control.
  * [ ] **Audit Logging**: Tracking user actions for financial compliance.