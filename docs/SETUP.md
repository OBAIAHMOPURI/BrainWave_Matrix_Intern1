# Hospital Management System — Setup

[README](../README.md) · [Architecture](ARCHITECTURE.md)

## 1. Scope and prerequisites

Use an isolated, disposable learning environment and **synthetic patients, staff, appointments, and billing data only**. This is not a clinical or production deployment procedure. Source defects and missing privacy/access controls remain uncorrected by documentation.

A suggested local baseline is a JDK 11 installation, Maven 3.9.x, a patched Tomcat 9.x container compatible with `javax.servlet`/Servlet 4.0, and a disposable local MySQL 8.x database. This combination is a **proposal, not a tested compatibility matrix**. Select supported patched versions for your environment. The POM does not pin Java compiler settings or build-plugin versions. Tomcat 10+ uses `jakarta.servlet`; it is not a drop-in runtime for these unchanged `javax.servlet` imports.

Check tools after installing your selected versions:

```sh
git --version
java -version
mvn -version
```

These and the following commands are proposed local instructions, not execution evidence.

## 2. Obtain the source

```sh
git clone https://github.com/OBAIAHMOPURI/BrainWave_Matrix_Intern1.git
cd BrainWave_Matrix_Intern1/Hospital_Management_System
```

The Maven project is inside `Hospital_Management_System/`. Do not run Maven from the repository root or rely on committed generated `target/` classes.

## 3. Resolve configuration and schema blockers

The servlet source embeds database connection credentials in `DriverManager.getConnection(...)`. Their values are intentionally not reproduced here. Do not reuse them; rotate any credential that was ever valid outside a disposable local environment. Before running, replace embedded credentials with a reviewed environment/JNDI/DataSource configuration and a new least-privilege local database account. **The current source does not read `DB_URL`, `DB_USER`, `DB_PASSWORD`, or a `.env` file: setting variables alone will not configure it.** Configuration refactoring and credential rotation are separate, unperformed changes; this documentation pass modifies no Java or deployment configuration.

The current URL is `jdbc:mysql://localhost:3306/hospital`. Review host, port, schema, new local credentials, privileges, and JDBC driver availability. The app must not connect as a database administrator.

No original DDL, schema migration, or synthetic seed script is committed. Prefer an owner-reviewed schema. If reconstructing a disposable local demo, the following **untested illustrative sketch** reflects source column names only; data types, lengths, primary keys, and other choices are proposed, not recovered from an existing database. It omits foreign keys, comprehensive constraints, indexes, and a full clinical data model. Do not run it against an existing or production database.

```sql
-- ILLUSTRATIVE DISPOSABLE-DEMO SCHEMA, NOT THE ORIGINAL DATABASE DDL.
-- Table casing follows the current SQL; keys/lengths/types are proposed.
CREATE DATABASE IF NOT EXISTS hospital;
USE hospital;
CREATE TABLE PatientRegistration (
  id INT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  date_of_birth DATE NOT NULL,
  address VARCHAR(500),
  email VARCHAR(255),
  mobilenumber BIGINT
);
CREATE TABLE appointment (
  patient_id INT NOT NULL,
  patient_name VARCHAR(255),
  doctor_name VARCHAR(255),
  appointment_date DATE,
  appointment_time TIME
);
CREATE TABLE EHR (
  patient_id INT NOT NULL,
  diagnosis TEXT,
  treatment TEXT,
  prescription TEXT,
  `date` DATE
);
CREATE TABLE Billing (
  patient_id INT NOT NULL,
  bill_amount DECIMAL(19,2),
  payment_status VARCHAR(100),
  bill_date DATE
);
CREATE TABLE Inventory (
  item_name VARCHAR(255),
  quantity INT,
  price DECIMAL(19,2)
);
CREATE TABLE staff (
  Staff_Id INT PRIMARY KEY,
  Staff_name VARCHAR(255),
  role VARCHAR(100),
  salary DECIMAL(19,2),
  contact_info BIGINT,
  email VARCHAR(255)
);
```

Preserve table spelling/case because the SQL mixes `PatientRegistration`, `appointment`, `EHR`, `Billing`, `Inventory`, and `staff`. Review platform-specific MySQL table-name behavior. The code inserts patient/staff IDs from form input, so choose unique synthetic IDs. ISO `YYYY-MM-DD` date values and a valid HTML time value are expected by the conversions, but errors are not handled robustly. The provisional schema does not implement privacy/security controls or repair the missing inventory execute call.

## 4. Verify servlet discovery and exact paths

The committed `WEB-INF/web.xml` declares Web Application 2.3 while servlet mappings are declared with `@WebServlet`. Verify annotation discovery in the chosen container. If form POSTs return 404, inspect the application startup log and mappings; modernizing the descriptor to Servlet 4.0 or explicitly registering mappings may be required. This guide does not claim that this configuration change has been made.

Keep `/AppoinmentSchedulingl` exactly as currently mapped unless both the Java annotation and HTML action are changed in a separate reviewed change. Patient/staff input field casing must match [Architecture](ARCHITECTURE.md).

## 5. Suggested explicit-plugin build — unverified

A candidate command that supplies Java source/target compatibility and an explicit WAR plugin is:

```sh
mvn -Dmaven.compiler.source=8 -Dmaven.compiler.target=8 clean test org.apache.maven.plugins:maven-war-plugin:3.4.0:war
```

Expected artifact if successful: `target/Hospital_Management_System.war`. This is not a verified build or a fix for source/database defects. No test sources are committed, despite the POM's test dependency. Inspect compiler/plugin errors and pin compatible build configuration through a separate reviewed change rather than relying on old generated files.

## 6. Isolated container deployment after remediation

Configure a local Tomcat 9 instance and `CATALINA_HOME`/`CATALINA_BASE` for your operating system. A Unix-like deployment/start example after a successful build is:

```sh
cp target/Hospital_Management_System.war "$CATALINA_BASE/webapps/"
"$CATALINA_HOME/bin/catalina.sh" run
```

For a container configured at local port 8080, the illustrative entry URL is:

```text
http://localhost:8080/Hospital_Management_System/SelectOptionsHere.html
```

Adjust the host/port/context to the actual local container and keep it off public networks.

## 7. Synthetic smoke checks

- Verify the menu and all six forms load; submit forms rather than navigating directly to POST-only routes.
- Insert a synthetic patient, then inspect actual database rows. Do not treat a static success page as persistence evidence.
- Test appointment values with a synthetic patient ID and inspect stored date/time.
- Compare submitted and stored Billing/EHR dates: current code stores the server date, not the selected form date.
- Submit inventory only to demonstrate the known defect: current handler does not execute its prepared INSERT and must not be counted as working persistence.
- Inspect staff insertion and resource/error behavior with fake records.
- Attempt direct unauthenticated access only in this isolated setup to confirm the absence of access-control guarantees; do not use real records.

## 8. Troubleshooting

| Symptom | Check |
| --- | --- |
| Menu not found | WAR name/context and `SelectOptionsHere.html` spelling |
| Appointment POST returns 404 | Actual `/AppoinmentSchedulingl` action/annotation, context, annotation discovery |
| Route returns 405 on direct browsing | Handler is POST-only; load and submit the HTML form |
| Unknown database/table | Missing schema and exact mixed-case table names |
| Access denied or driver not found | Reviewed new local credentials/privileges and WAR dependency packaging |
| Invalid number/date errors | Exact form parameter casing and valid synthetic field formats |
| Inventory status page but no new row | Known missing execute call, not a setup success |
| Selected billing/EHR date is not stored | Known current-server-date behavior |

## 9. Verification record

No build, database creation, Tomcat start, browser smoke test, vulnerability scan, or end-to-end test was executed during this documentation pass. The tree contains generated `target/` classes, but they are not evidence of a reproducible clean build. A JUnit 3.8.1 test dependency is declared; no `src/test` tree or CI workflow was found. A successful Maven test phase without test sources does not demonstrate application correctness.

Record actual tool versions, schema/migration revision, servlet-mapping discovery, successful build command, and observed per-form database effects before marking setup verified. Never store health/staff data, credentials, or raw sensitive logs in GitHub documentation or memory checkpoints.
