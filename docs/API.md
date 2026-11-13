# Hospital Management System — API and Form Route Guide

[Root README](../README.md) · [Architecture](ARCHITECTURE.md) · [Setup](SETUP.md) · [Contributing](../CONTRIBUTING.md) · [Network Ecosystem](../README.md#network-ecosystem)

## Contract status and transport

This reference describes the existing Java HTML-form POST handlers from static source inspection on 13 November 2026. It is not a tested clinical API, JSON REST contract, or compliance assertion. There is no verified deployed base URL, OpenAPI specification, SDK, health route, or complete patient-management CRUD interface.

Routes are relative to the WAR context, conventionally `/Hospital_Management_System` when the configured WAR name is retained. Actual host, port, context, and annotation discovery depend on local setup. Start at `SelectOptionsHere.html` to access the six forms.

All six handlers implement `doPost`; existing forms submit URL-encoded fields (`application/x-www-form-urlencoded`), not JSON. Route and parameter spelling/case are significant. Responses forward to static `File1.html` through `File6.html`; no consistent JSON response, structured validation/error envelope, idempotency policy, or supported status-code contract is established. A status page alone does not prove a database write.

## Exact POST route inventory

| Route | Parameters consumed by handler | Current source behavior |
| --- | --- | --- |
| `/PatientRegistration` | `Id`, `Name`, `Dob`, `Address`, `Email`, `Mobilenumber` | Inserts `PatientRegistration` columns `id`, `name`, `date_of_birth`, `address`, `email`, `mobilenumber`; forwards to `File1.html` |
| `/AppoinmentSchedulingl` | `patient_id`, `patient_name`, `doctor_name`, `appointment_date`, `appointment_time` | Inserts `appointment`; appends `:00` to a five-character time before conversion; forwards to `File2.html` |
| `/ElectronicHealthRecords` | `patient_id`, `diagnosis`, `treatment`, `prescription`, `date` | Inserts `EHR`; ignores submitted `date` and stores the current server date; forwards to `File3.html` |
| `/Billing` | `patient_id`, `bill_amount`, `payment_status`, `date` | Inserts `Billing`; ignores submitted `date` and stores current server date as `bill_date`; forwards to `File4.html` |
| `/InventoryManagementForMedicalSupplies` | `item_name`, `quantity`, `price` | Prepares/binds an `Inventory` INSERT but does not execute it; forwards to `File5.html` without demonstrated persistence |
| `/StaffManagement` | `Staff_Id`, `Staff_name`, `role`, `salary`, `contact_info`, `email` | Inserts `staff`; forwards to `File6.html`; success-path connection cleanup is incomplete |

The appointment action is exactly `/AppoinmentSchedulingl`, including its final lower-case `l`; the HTML form matches. Patient-registration parameters begin with capitals as listed. Staff parameter casing is also exact. Source classes include `AppoinmentScheduling.java` and `PateintRegistration.java`; see [Architecture](ARCHITECTURE.md) for handler links.

## Input conversions and database assumptions

- Patient/staff IDs and inventory quantity are parsed as Java integers.
- Phone/contact values are parsed as longs, so formatting and leading zeros are not preserved.
- Date of birth and appointment date use `Date.valueOf` and expect ISO `YYYY-MM-DD`; time uses `Time.valueOf` after the described seconds adjustment. Invalid formats can raise unchecked conversion errors.
- Billing amount and inventory price use `BigDecimal`; staff salary uses `double`. No comprehensive positivity/range/status validation is established.
- The EHR/Billing `date` field is read but is not the value persisted. There is no documented timezone policy beyond server-current-date behavior.

Source SQL uses local schema `hospital` and mixed table spelling: `PatientRegistration`, `appointment`, `EHR`, `Billing`, `Inventory`, `staff`. Original DDL, foreign keys, migrations, and seeds are absent. Preserve case when reconstructing an isolated disposable schema and review platform-specific MySQL behavior. The setup sketch is not recovered production DDL.

## Safe request exploration

Only after following setup and remediating local configuration, use the HTML forms with synthetic values and inspect actual database effects. These routes mutate data; no live request is executed by this guide. Do not submit real patient, staff, medical, or billing records, and do not assume a success/status page means persistence. There is no read-only API or production-ready curl quick start to advertise.

Direct browser navigation uses GET and may return 405. A 404 may indicate context/route spelling or annotation discovery with the legacy `web.xml`. SQL/driver failures are largely printed to logs, numerical/date parsing lacks robust client errors, and sensitive logs must not be shared publicly.

## Authentication and future contract work

No implemented login/session/authorization/privacy boundary was found. Embedded database credentials are intentionally not reproduced. Do not expose the application publicly or claim healthcare compliance. Before offering a supported API, add authenticated role/record access, validated schemas, correct date semantics, actual inventory persistence, reliable resource cleanup, migration-backed integrity, safe errors/redaction, and tests. Coordinate any renamed route/field with its form and clients. No build, database setup, runtime test, security scan, or sibling integration was performed by this documentation change.

## Sibling repository network

Navigation only; no runtime connections are implied.

- [alert-notification-dispatcher](https://github.com/OBAIAHMOPURI/alert-notification-dispatcher/blob/main/README.md)
- [Brainwave_Matrix_Intern — Banky Application](https://github.com/OBAIAHMOPURI/Brainwave_Matrix_Intern/blob/main/README.md)
- [edge-gateway-service](https://github.com/OBAIAHMOPURI/edge-gateway-service/blob/main/README.md)
- [edge-gateway-service1](https://github.com/OBAIAHMOPURI/edge-gateway-service1/blob/main/README.md)
- [identity-auth-proxy](https://github.com/OBAIAHMOPURI/identity-auth-proxy/blob/main/README.md)
- [telemetry-stream-worker](https://github.com/OBAIAHMOPURI/telemetry-stream-worker/blob/main/README.md)
- [workflow-state-sync](https://github.com/OBAIAHMOPURI/workflow-state-sync/blob/main/README.md)
