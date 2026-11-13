# Contributing to Hospital Management System

[Root README](README.md) · [Architecture](docs/ARCHITECTURE.md) · [Setup](docs/SETUP.md) · [API guide](docs/API.md) · [Network Ecosystem](README.md#network-ecosystem)

## Scope and privacy boundary

This Java Servlet/JDBC learning application lives under `Hospital_Management_System/`. It is incomplete and is not a clinical system, production deployment, or healthcare-compliance implementation. Use an isolated local environment with synthetic patients, health records, staff, and billing data only.

Read setup first. The suggested JDK/Maven/Tomcat baseline and schema sketch are unverified proposals. Embedded JDBC credentials must not be copied or reused; setting `DB_*` variables alone does not configure the existing source.

```sh
git clone https://github.com/OBAIAHMOPURI/BrainWave_Matrix_Intern1.git
cd BrainWave_Matrix_Intern1
git switch -c fix/describe-change
```

Prefer focused branches and pull requests for normal work. This batch's direct-to-main publication was specifically owner-authorized, not a standing merge rule. Run Maven from `Hospital_Management_System/`, not the repository root.

## Repository-specific priorities

1. Replace embedded credentials with reviewed least-privilege configuration/DataSource access and rotate reused credentials.
2. Add authentication, role-based authorization, privacy-safe responses/logging, and reviewed audit/retention behavior before sensitive-data use.
3. Execute and verify inventory writes: the current handler prepares/binds an INSERT but never executes it.
4. Resolve Billing/EHR date semantics: the current handlers ignore the submitted date and store the server date. Validate all dates, numbers, references, and statuses.
5. Add migrations/constraints and synthetic fixtures; close JDBC resources reliably; pin compatible builds and test servlet discovery with the legacy descriptor.

Preserve exact names unless changing annotations, forms, documentation, and tests together. `/AppoinmentSchedulingl` includes a final lower-case `l`; patient and staff parameters have case-sensitive names. The actual classes include `AppoinmentScheduling.java` and `PateintRegistration.java`. Do not silently correct spelling only in documentation or invent unimplemented CRUD features.

## Review and evidence requirements

Each pull request should state scope, rationale, affected routes/fields, schema or compatibility changes, commands actually run, observed results, remaining limits, and rollback/migration needs. Add tests for the changed behavior: input conversion, missing/duplicate IDs, authorization, exact route/field casing, chosen versus stored dates, inventory affected rows, table-name case portability, SQL failures, and resource cleanup.

A JUnit 3.8.1 dependency is declared, but no test-source tree or verified CI suite was found. A test phase with no tests is not evidence of correctness. Mark unrun tests/builds explicitly. Rebuild from source; do not add generated `target/` output or remove existing artifacts as unrelated cleanup.

For documentation, run `git diff --check`, inspect Markdown rendering, verify root README and architecture/setup/API links, and keep all seven sibling links valid. Separate current source behavior from proposed remediation.

## Sensitive reports and operational changes

Do not place real patient/personnel records, credentials, private operational notes, raw logs, or identifying screenshots in public issues or commits. Report sensitive vulnerabilities through an owner-approved private channel. Production access, deployment, live integrations, or schema changes outside an isolated disposable environment need separate authorization. This guide selects no license.

## Sibling repository network

Navigation only; no live integrations are claimed.

- [alert-notification-dispatcher](https://github.com/OBAIAHMOPURI/alert-notification-dispatcher/blob/main/README.md)
- [Brainwave_Matrix_Intern — Banky Application](https://github.com/OBAIAHMOPURI/Brainwave_Matrix_Intern/blob/main/README.md)
- [edge-gateway-service](https://github.com/OBAIAHMOPURI/edge-gateway-service/blob/main/README.md)
- [edge-gateway-service1](https://github.com/OBAIAHMOPURI/edge-gateway-service1/blob/main/README.md)
- [identity-auth-proxy](https://github.com/OBAIAHMOPURI/identity-auth-proxy/blob/main/README.md)
- [telemetry-stream-worker](https://github.com/OBAIAHMOPURI/telemetry-stream-worker/blob/main/README.md)
- [workflow-state-sync](https://github.com/OBAIAHMOPURI/workflow-state-sync/blob/main/README.md)
