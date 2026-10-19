# StayHaven

An integrated Spring Boot hotel booking site with guest and administrator roles, a shared database, and a responsive Thymeleaf interface.

## Requirements

- JDK 25 (the available JDK used for this project; Spring Boot 4.1.1 supports Java 17 through 26)
- Maven 3.10.0 (the Windows wrapper launcher uses the machine's cached distribution, or a `mvn` command on PATH)

The project targets Java 25. Java 21 was not installed in the build environment.

## Run

PowerShell:

```powershell
$env:STAYHAVEN_ADMIN_EMAIL = "admin@stayhaven.local"
$env:STAYHAVEN_ADMIN_PASSWORD = "Choose-A-Strong-Local-Password"
.\mvnw.cmd spring-boot:run
```

Then open <http://localhost:8080>. The bootstrap administrator is created only when `STAYHAVEN_ADMIN_PASSWORD` is set at startup. The initial email is `admin@stayhaven.local`; choose the password locally in the environment variable. Passwords are BCrypt encoded before storage and are not printed by the application. To rotate the admin password, change `STAYHAVEN_ADMIN_PASSWORD` and restart the app. Set a strong password before using a shared or deployed instance.

Use `/admin/login` for the admin sign-in form. It uses the same authentication system; successful admin sign-in opens `/admin`, while a regular account opens `/dashboard`. Regular users can create an account at `/register` and sign in at `/login`.

To stop the development server, press `Ctrl+C` in its terminal. To run the packaged app after `.\mvnw.cmd package`, use `java -jar target\stayhaven-1.0.0.jar`; stop it with `Ctrl+C` in that terminal.

Without the admin environment variables, the site still runs and visitors can register as users. Set both variables before the first run to initialize the admin. The local database file is created under `./data/stayhaven`.

## Database

Default: file-backed H2, so data survives restarts without an external service. The H2 console is disabled by default. For local debugging, set `H2_CONSOLE_ENABLED=true`, sign into the app as admin, then open `/h2-console` (JDBC URL `jdbc:h2:file:./data/stayhaven;AUTO_SERVER=TRUE`, user `sa`, blank password). The console remains local-only and admin-protected.

For MySQL, create a database and set:

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/stayhaven?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC"
$env:DB_USER = "stayhaven"
$env:DB_PASSWORD = "your-local-database-password"
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=mysql
```

The schema is managed by Hibernate `ddl-auto=update`. For production, configure managed migrations, secure H2 console access, HTTPS, and a secret manager.

## Build and tests

```powershell
.\mvnw.cmd test
.\mvnw.cmd package
```

Tests cover booking date validation, price calculation and capacity limits, user/admin route access, CRUD/security behavior, and rendering of the admin operations pages.

## Included flows

- Public hotel search and detail pages; registration, sign in, sign out, address-aware guest profiles, booking history and filters.
- Individual room units are created additively from existing room-type inventory. Room status, scheduled times, actual check-in/out timestamps and staff accounts are tracked, with room status history.
- Booking creation assigns an available unit, validates dates/capacity/overlaps and blocks maintenance/cleaning units. Admins can confirm/cancel; front-desk actions record check-in/out and move checked-out rooms to Cleaning.
- Guest history, recorded demo payments, guest reviews, room-service orders, date and room-type aware offers, and loyalty point earning/redemption are database-backed.
- Responsive admin navigation links dashboard, rooms, bookings, arrivals/departures, guests/history, payments, room service, reviews, offers, loyalty, reports, settings, hotels, and account management.
- Starter stays are inserted into the shared database on first startup. Hibernate `ddl-auto=update` adds new tables and nullable fields without dropping existing data.

## REST API

- `GET /api/rooms/{roomTypeId}` returns room-type and unit details.
- `GET /api/availability?roomId={id}&checkIn=YYYY-MM-DD&checkOut=YYYY-MM-DD` reports bookable units for future dates.
- `POST /api/bookings` creates a booking for the signed-in user. JSON fields: `roomId`, `checkIn`, `checkOut`, and `guests`.
- `POST /api/admin/bookings/{id}/status?status=CONFIRMED|CANCELLED|PENDING` updates a booking; ADMIN role required.

API booking writes use the app's session authentication and CSRF protection. Admin APIs are role-protected.

## Notes

- A room type's `quantity` is the number of sellable units. Booking creation locks that room type during the availability check and insert, reducing concurrent overbooking.
- Public hotel images use Unsplash image URLs; browser access to Unsplash is required for those photos. The booking and database features do not depend on image loading.
- Payment uses an explicit `DEMO_TEST` adapter and does not collect card details or process real payments. A provider SDK and credentials are needed for settlement.
- Booking confirmation appears in the app, but SMTP delivery is not implemented; no email is claimed to have been sent. Configure a mail provider after adding an email adapter to enable outbound messages.
- Role separation currently includes `USER` and `ADMIN`; there is no separate staff role. Amenities and room-amenity assignment are not implemented.
- The MySQL profile is provided but has not been integration-tested against a live MySQL server. Production deployment should use versioned migrations instead of Hibernate schema update.
