# Repository Guidelines

## Project Structure & Module Organization

This is a single Maven module. Production Java code is under
`src/main/java/com/position/publicador_alarmas/`, using the package
`com.position.publicador_alarmas`. The classes are organized around alarm
records, data transfer objects, validation, HTTP/JSON integration, and the
polling worker (`publicador_regalm`). There is currently no `src/test` tree or
checked-in assets. Maven output is written to `target/`; treat it as generated
and do not edit or commit it.

## Build, Test, and Development Commands

- `mvn clean package` — removes prior output, compiles with Java 21, and builds
  both the regular JAR and the assembled `jar-with-dependencies` artifact.
- `mvn test` — runs the Maven test lifecycle; no automated tests are currently
  present, so add tests under `src/test/java` as functionality is changed.
- NetBeans users can use the `run` action defined in `nbactions.xml`.

The POM and NetBeans action reference
`com.position.publicador_alarmas.Publicador_alarmas` as the executable main
class, but the current source tree does not contain that class. Verify or
restore the intended application entrypoint before relying on a runnable JAR.
The worker also calls external HTTP services, so integration execution
requires the expected service configuration and network access.

## Coding Style & Naming Conventions

Match the existing Java style: four-space indentation, braces on their own
line, and UTF-8 source files. Preserve the existing package and class naming
patterns when modifying legacy code; new types should use conventional
`PascalCase`, methods and variables `camelCase`, and constants `UPPER_SNAKE_CASE`.
Keep HTTP and data-mapping changes localized to their existing classes.

## Testing Guidelines

Place tests in `src/test/java` with package paths matching production code and
name classes `*Test`. At minimum, cover validation, mapping, and HTTP error
handling for changed behavior. Run `mvn test` before submitting changes; for
external-service behavior, prefer mocked HTTP responses over live endpoints.

## Documentation Guidelines

- Keep `README.md` as the entry summary of the worker and link out to the
  operational docs.
- Use `docs/test-cases/` for reproducible use-case verification, one case per
  file when the behavior needs separate preconditions or expected outcomes.
- Use `docs/improvement-roadmap.md` for gaps, risks, and pending refactors;
  do not mix roadmap items into the test-case docs.
- Write the docs in Spanish, but preserve literal identifiers such as class
  names, env vars, URLs, SQL objects, and Maven/Docker commands.

## Commit & Pull Request Guidelines

Existing commits are short, imperative Spanish descriptions (for example,
`Ultima actualizacion`). Keep commits focused and use a concise imperative
summary. Pull requests should explain the behavior change, identify affected
alarm/API flows, include test commands and results, and call out any required
endpoint or configuration changes. Do not include credentials, generated
`target/` files, or unrelated working-tree changes.

## Security & Configuration Tips

Do not hard-code API keys, account credentials, or production URLs. Review
configuration parsing and outbound request changes carefully, and redact
sensitive values from logs, commits, and pull-request screenshots.
