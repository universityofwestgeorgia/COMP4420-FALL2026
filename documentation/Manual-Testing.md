# Manual Testing

Manual testing was performed on October 11, 2026, using Docker Desktop on macOS.

## Test Results

- The application opens successfully with `mvn javafx:run`.
- Local Docker containers appear in the dashboard table.
- The Refresh button updates the displayed container information.
- Opening details without selecting a container displays a warning.
- Opening details after selecting a container displays its name, image, status, state, and identifier.
- The Stop button stops the selected running container and refreshes the table.
- The Start button starts the selected stopped container and refreshes the table.
- An error dialog is displayed when Docker is unavailable.
- `mvn verify` completes successfully with 25 passing tests, no Checkstyle violations, and the required code coverage.
