# Hospital Management System — Architecture

[README](../README.md) · [Setup](SETUP.md) · [Network Ecosystem](../README.md#network-ecosystem)

## Scope and evidence

Static review of `main` on 13 November 2026 covered the POM, all six Java handlers, the deployment descriptor, menu, and six user-input forms. Current behavior below is derived from source only; no runtime or healthcare compliance assessment has been performed.

## Current architecture

The browser submits a static HTML form to an annotation-mapped `javax.servlet.http.HttpServlet`. A `doPost` handler reads strings, performs local numeric/date conversion, opens a JDBC connection through `DriverManager`, prepares SQL, and generally executes an INSERT. It then forwards to a static result page. No independent domain/service/DAO layer or central validation/authorization filter was found.

`SelectOptionsHere.html` links all six forms. The source uses direct JDBC to `jdbc:mysql://localhost:3306/hospital`; there is no migration/seed fixture, pooled DataSource, API schema, sibling-service client, or deployment stack in the audited repository.

## Exact route and field inventory

Routes are relative to the WAR context. All implement POST and accept HTML form parameters, not a documented JSON API.

| POST route | Handler source | Request parameter names |
| --- | --- | --- |
| `/AppoinmentSchedulingl` | [AppoinmentScheduling.java](../Hospital_Management_System/src/main/java/com/Hospital_Management_System/AppoinmentScheduling.java) | `patient_id`, `patient_name`, `doctor_name`, `appointment_date`, `appointment_time` |
| `/Billing` | [Billing.java](../Hospital_Management_System/src/main/java/com/Hospital_Management_System/Billing.java) | `patient_id`, `bill_amount`, `payment_status`, `date` |
| `/ElectronicHealthRecords` | [ElectronicHealthRecords.java](../Hospital_Management_System/src/main/java/com/Hospital_Management_System/ElectronicHealthRecords.java) | `patient_id`, `diagnosis`, `treatment`, `prescription`, `date` |
| `/InventoryManagementForMedicalSupplies` | [InventoryManagementForMedicalSupplies.java](../Hospital_Management_System/src/main/java/com/Hospital_Management_System/InventoryManagementForMedicalSupplies.java) | `item_name`, `quantity`, `price` |
| `/PatientRegistration` | [PateintRegistration.java](../Hospital_Management_System/src/main/java/com/Hospital_Management_System/PateintRegistration.java) | `Id`, `Name`, `Dob`, `Address`, `Email`, `Mobilenumber` |
| `/StaffManagement` | [StaffManagement.java](../Hospital_Management_System/src/main/java/com/Hospital_Management_System/StaffManagement.java) | `Staff_Id`, `Staff_name`, `role`, `salary`, `contact_info`, `email` |

`AppoinmentScheduling.java` and `PateintRegistration.java` are the actual class filenames. The route `/AppoinmentSchedulingl` includes a final lower-case `l`, and the appointment form uses that exact action. Patient registration parameters are case-sensitive (`Id`, `Name`, `Dob`, `Address`, `Email`, `Mobilenumber`). Staff parameter casing is also preserved above.

## Database contract inferred from SQL

| Table spelling in current SQL | Referenced columns | Behavior |
| --- | --- | --- |
| `PatientRegistration` | `id`, `name`, `date_of_birth`, `address`, `email`, `mobilenumber` | Inserts a registration |
| `appointment` | `patient_id`, `patient_name`, `doctor_name`, `appointment_date`, `appointment_time` | Inserts a scheduling record |
| `EHR` | `patient_id`, `diagnosis`, `treatment`, `prescription`, `date` | Inserts health-record text; stores server date |
| `Billing` | `patient_id`, `bill_amount`, `payment_status`, `bill_date` | Inserts billing record; stores server date |
| `Inventory` | `item_name`, `quantity`, `price` | INSERT is prepared/bound but not executed |
| `staff` | `Staff_Id`, `Staff_name`, `role`, `salary`, `contact_info`, `email` | Inserts a staff record |

No original DDL or migration exists. Do not infer actual keys, constraints, allowed statuses, foreign keys, string lengths, or database contents from this column list. MySQL table-name case sensitivity varies by platform/configuration; preserve the SQL spelling when reconstructing a local disposable schema. Numeric phone/ID conversion may lose formatting/leading zeros.

## Known behavior and defects

- **Inventory:** `InventoryManagementForMedicalSupplies` binds parameters and forwards to `File5.html` without calling an execute method. A success/status page is not evidence that an inventory row was inserted.
- **Date semantics:** Billing and EHR read a `date` parameter but bind `new Date(System.currentTimeMillis())`. The chosen form date is not used. Patient date-of-birth and appointment date are converted with `Date.valueOf`; invalid values can raise unchecked conversion errors.
- **Appointment time:** the handler appends `:00` to a five-character time before calling `Time.valueOf`. Explicit validation/error messages are still needed.
- **Authorization/privacy:** no login, authenticated session, common authorization filter, or patient/staff access controls were found. The application is not appropriate for real health or personnel records.
- **Resources and errors:** SQL/ClassNotFound exceptions are generally printed to logs; client-facing errors, try-with-resources, result handling, and cleanup are inconsistent. Staff handling does not close its connection on its success path.
- **Integrity/completeness:** no enforced patient-reference integrity, complete update/search/delete workflows, business status validation, or automated tests were found. Do not advertise those features.

## Configuration and servlet discovery

The servlet source embeds database connection credentials in `DriverManager.getConnection(...)`. Their values are intentionally not reproduced here. Do not reuse them; rotate any credential that was ever valid outside a disposable local environment. Before running, replace embedded credentials with a reviewed environment/JNDI/DataSource configuration and a new least-privilege local database account. **The current source does not read `DB_URL`, `DB_USER`, `DB_PASSWORD`, or a `.env` file: setting variables alone will not configure it.** Configuration refactoring and credential rotation are separate, unperformed changes; this documentation pass modifies no Java or deployment configuration.

The committed `WEB-INF/web.xml` declares Web Application 2.3 while servlet mappings are declared with `@WebServlet`. Verify annotation discovery in the chosen container. If form POSTs return 404, inspect the application startup log and mappings; modernizing the descriptor to Servlet 4.0 or explicitly registering mappings may be required. This guide does not claim that this configuration change has been made.

## Proposed next architecture — not implemented

Introduce configuration-managed least-privilege database access, a pooled DataSource, domain/service/DAO separation, explicit request validation, consistent safe HTTP/error responses, and reliable JDBC cleanup. Establish authentication/role-based access, audit trails, privacy/redaction/retention controls, and reviewed synthetic fixtures before processing any sensitive data. Add schema migrations and relationships, define date/time/timezone semantics, execute inventory writes with verified affected-row checks, and test actual persistence.

Any future integration with a sibling gateway, identity proxy, workflow service, telemetry worker, or dispatcher needs a reviewed data-minimization contract and a tested permission boundary. The [Network Ecosystem](../README.md#network-ecosystem) provides navigation only, not an existing runtime topology.

## Suggested regression coverage

Test all form/route spellings and parameter casing; malformed dates/numbers; absent and duplicate IDs; unauthorized direct access; data minimization; submitted versus stored dates; inventory affected rows; table-name portability; transient SQL errors; connection cleanup; and safe retry behavior. No such tests have yet been verified.

## Verification status

No build, database creation, Tomcat start, browser smoke test, vulnerability scan, or end-to-end test was executed during this documentation pass. The tree contains generated `target/` classes, but they are not evidence of a reproducible clean build. A JUnit 3.8.1 test dependency is declared; no `src/test` tree or CI workflow was found. A successful Maven test phase without test sources does not demonstrate application correctness.
