# Hospital Management System

## Project Overview

Hospital Management System is a learning project for discussing administrative journeys such as patient registration, appointment records, billing, supplies, and staff information. It offers a starting point for understanding how information might move between people and forms.

**Current stage:** an incomplete educational prototype with application source, not a clinical or hospital-ready product. Use fictional examples only; never enter real patient, medical, billing, or staff information.

## Business Use Cases

Use this project for learning and process discussions, not healthcare delivery:

- **Workflow mapping:** describe a fictional registration-to-appointment journey and identify unclear handoffs.
- **Team orientation:** discuss what reception, administrative, and support roles would need to see or do, without claiming those permissions are implemented.
- **Requirements planning:** prioritize missing privacy safeguards, reliable record saving, and correct date handling before considering any real-world use.

## Team Onboarding Checklist

- [ ] Read the current-stage note and understand that this is not approved for clinical use.
- [ ] Choose a fictional administrative scenario and agree the purpose of the discussion.
- [ ] Ask a technical teammate which forms and record-saving steps are incomplete or unverified.
- [ ] Keep all real patient, medical, billing, and staff details out of examples and screenshots.
- [ ] Read the [contribution guide](CONTRIBUTING.md), record unanswered questions, and browse [Related Projects](#related-projects).

## Existing Project Details

A Java Servlet/JDBC **learning application** under [`Hospital_Management_System/`](Hospital_Management_System/) with HTML forms for patient registration, appointment scheduling, health records, billing, supplies, and staff records. Handlers write directly to a local MySQL `hospital` schema.

> **Safety/status:** This is an incomplete educational prototype, not a clinical system. No authentication/authorization layer or original database DDL was found, credentials are embedded in source, and some operations do not persist or honor submitted values. Do not use real patient, medical, billing, or staff data.

## Features present in source

- Patient registration, appointment insertion, electronic health-record insertion, billing insertion, and staff insertion handlers.
- A menu at `SelectOptionsHere.html` links the six forms.
- An inventory form and SQL preparation exist, but the handler does not execute the prepared INSERT; **inventory saving is incomplete**.
- Billing/EHR handlers accept a submitted date field but persist the current server date instead.

These are source-observed code paths, not tested capabilities or compliance claims. No complete patient-management CRUD/API workflow is claimed.

## Stack and repository layout

| Item | Verified value |
| --- | --- |
| Packaging | Maven WAR, version `0.0.1-SNAPSHOT` |
| Servlet API | `javax.servlet-api:4.0.1`, container-provided |
| JDBC driver | `com.mysql:mysql-connector-j:8.0.33` |
| Test dependency | JUnit `3.8.1`; no test sources found |
| WAR name | `Hospital_Management_System.war` |
| Database URL in current source | `jdbc:mysql://localhost:3306/hospital` |

```text
Hospital_Management_System/
  pom.xml
  src/main/java/com/Hospital_Management_System/  # Six servlets; direct JDBC
  src/main/webapp/                              # Menu, forms, static status pages
  src/main/webapp/WEB-INF/web.xml                # Legacy deployment descriptor
  target/                                       # Committed generated files; rebuild locally
```

## Architecture notes

```text
Browser form -> HttpServlet.doPost -> direct JDBC INSERT -> MySQL hospital
                     |
                     +-> static File1.html ... File6.html forward
```

No separate service/DAO layer, authentication filter, migration system, connection pool, or implemented sibling-service connection was found. See [Architecture](docs/ARCHITECTURE.md) for exact case-sensitive route/field names, inferred tables, and known defects.

## Setup instructions

```sh
git clone https://github.com/OBAIAHMOPURI/BrainWave_Matrix_Intern1.git
cd BrainWave_Matrix_Intern1/Hospital_Management_System
```

Follow [Setup](docs/SETUP.md) before building/running. It covers a suggested but unverified JDK/Maven/Tomcat/MySQL baseline, schema reconstruction limits, credential/configuration remediation, servlet discovery, an explicit-plugin WAR build, and isolated smoke checks. No verified one-command startup is available.

After local prerequisites and remediation, the intended menu path is `/Hospital_Management_System/SelectOptionsHere.html`; actual host/port/context are container configuration choices.

## Important limitations and security work

- No access control or real-user session protection is implemented in the audited handlers.
- The inventory handler forwards to a status page without executing its INSERT.
- Billing and EHR ignore the submitted date and use server current time for the stored date.
- The scheduling route is actually `/AppoinmentSchedulingl` (including its spelling); the form currently matches it.
- Input validation, relational integrity, error reporting, and resource management need review.
- There is no evidence of healthcare privacy/regulatory compliance, audit controls, tested backups, or production readiness.

Documentation does not remediate these defects. Use isolated synthetic-data demonstrations only.

## Related Projects

Explore the seven other projects in this account. Each link opens that project's main page so you can compare its purpose and stage. These are browsing links, not live connections between applications.

### Network Ecosystem

This is a navigation network across all eight repositories owned by this account, audited on 13 November 2026. Links indicate sibling projects, **not implemented integrations**. The Java apps are independent learning projects; the six service repositories have no application code.

| Sibling repository | Role and verified maturity |
| --- | --- |
| [alert-notification-dispatcher](https://github.com/OBAIAHMOPURI/alert-notification-dispatcher) | Proposed alert routing and delivery; no implementation |
| [Brainwave_Matrix_Intern](https://github.com/OBAIAHMOPURI/Brainwave_Matrix_Intern) | Java Servlet/JDBC banking learning app; incomplete and unverified |
| [edge-gateway-service](https://github.com/OBAIAHMOPURI/edge-gateway-service) | Proposed ingress/routing service; previously empty |
| [edge-gateway-service1](https://github.com/OBAIAHMOPURI/edge-gateway-service1) | Separate gateway scaffold; relationship to other gateway undecided |
| [identity-auth-proxy](https://github.com/OBAIAHMOPURI/identity-auth-proxy) | Proposed identity/policy boundary; no implementation |
| [telemetry-stream-worker](https://github.com/OBAIAHMOPURI/telemetry-stream-worker) | Proposed telemetry ingestion worker; no implementation |
| [workflow-state-sync](https://github.com/OBAIAHMOPURI/workflow-state-sync) | Proposed state reconciliation service; no implementation |

Both gateway repositories remain distinct. No canonical gateway, shared protocol, deployment, or runtime connection has been established. Do not infer a production microservices system from these cross-links.

## Development and verification

No build, database creation, Tomcat start, browser smoke test, vulnerability scan, or end-to-end test was executed during this documentation pass. The tree contains generated `target/` classes, but they are not evidence of a reproducible clean build. A JUnit 3.8.1 test dependency is declared; no `src/test` tree or CI workflow was found. A successful Maven test phase without test sources does not demonstrate application correctness.

Prioritize credential removal/rotation, authentication/authorization, privacy-safe data handling, schema migrations/constraints, validated field/date semantics, actual inventory persistence, consistent JDBC cleanup, and automated tests. No license file was found in the audited tree; no license is added here.
