# Container Deck

[![Maven Verify](https://github.com/universityofwestgeorgia/COMP4420-FALL2026/actions/workflows/maven.yml/badge.svg?branch=dev)](https://github.com/universityofwestgeorgia/COMP4420-FALL2026/actions/workflows/maven.yml)
![Java 17+](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-required-2496ED?logo=docker&logoColor=white)

A desktop app that provides a graphical interface for managing local Docker containers and Docker Compose projects.
This project reflects my interest in DevOps and containerization while giving me an opportunity to learn Java, Maven, and JavaFX through a practical application.

## Run the Application

From the `code/ContainerDeck` directory, run:

```bash
mvn javafx:run
```

## Features

Container Deck displays all local Docker containers in a JavaFX table. Users can refresh the list, inspect a selected container in a details window, and start or stop containers through Docker.

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
