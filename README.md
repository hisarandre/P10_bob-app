# P10 – Bob app

## 📌 Description

BobApp is a fullstack application with a Java Spring Boot backend and an Angular frontend. This project implements a complete CI/CD pipeline:

- Automatic execution of tests and code quality analysis on each push and pull request
- Publication of Docker images to Docker Hub
- Check the code coverage and the quality Gates with SonarCloud

## 🛠️ CI/CD Workflows

The project contains CI/CD pipelines triggered on push or pull request to the main branch.

### Pipeline steps

1. Backend - Build & Test

- Checkout code
- Set up JDK 17
- Build the backend with Maven
- Run automated tests
- Generate JaCoCo code coverage report
- Upload backend coverage artifacts

2. Frontend - Build & Test

- Checkout code
- Set up Node.js 14
- Install dependencies
- Run automated tests with coverage
- Upload frontend coverage artifacts

3. SonarCloud Quality Checks

- Backend: download backend coverage, run SonarCloud scan with Maven
- Frontend: download frontend coverage, run SonarCloud scan

4. Docker Build & Push

- Checkout code
- Set up Docker Buildx
- Log in to Docker Hub
- Build and push backend Docker image
- Build and push frontend Docker image

5. Resume Success

The [Actions](https://github.com/hisarandre/P10_bob-app/actions) are visible on the tab ACTIONS of this repository

### Execution rules

Each step depends on the previous step:

- sonarcloud-backend depends on build-backend
- sonarcloud-frontend depends on build-frontend
- push-docker depends on both SonarCloud checks
- resume-success depends on push-docker
- Backend and frontend pipelines run in parallel but are independent
- Docker images are only pushed if all previous steps succeed
- The merge is automatically blocked if the quality gates or any pipeline step failed (ruleset)

## 📈 KPIs and Quality Gates

This project uses the default "SonarWay" Quality Gate from SonarCloud, which verifies:

- No new blocker issues, bugs, or vulnerabilities on new code
- Coverage on new code ≥ 80%
- Duplication on new code < 3%
- No unreviewed Security Hotspots

You can check the code quality and coverage here :

- [SonarCloud - backEnd](https://sonarcloud.io/project/overview?id=hisarandre_P10_bob-app_back)
- [SonarCloud - frontEnd](https://sonarcloud.io/project/overview?id=hisarandre_P10_bob-app_end)

## 🛠️ Technologies

- Java 11
- Maven 3.6+
- Node.js 16+
- npm 8+
- Docker

## 🚀 Installation

Clone project:

```bash
   git clone https://github.com/hisarandre/P10_bob-app.git
   cd P10_bob-app
```

## Front-end

Go inside folder the front folder:

> cd front

Install dependencies:

> npm install

Launch Front-end:

> npm run start;

### Docker

Build the container:

> docker build -t bobapp-front .

Start the container:

> docker run -p 8080:8080 --name bobapp-front -d bobapp-front

OR you can run the latest Docker image from Docker Hub:

> docker run -p 8080:8080 --name bobapp-front -d sarandre/bobapp-front:latest

## Back-end

Go inside folder the back folder:

> cd back

Install dependencies:

> mvn clean install

Launch Back-end:

> mvn spring-boot:run

Launch the tests:

> mvn clean install

### Docker

Build the container:

> docker build -t bobapp-back .

Start the container:

> docker run -p 8080:8080 --name bobapp-back -d bobapp-back

OR you can run the latest Docker image from Docker Hub:

> docker run -p 8080:8080 --name bobapp-back -d sarandre/bobapp-back:latest
