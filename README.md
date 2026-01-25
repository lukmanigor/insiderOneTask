# insiderOneTask — Test Automation Project  

This repository contains automation work covering three areas of testing:
- UI Functional Automation using Java + Selenium + TestNG  
- API Automation using Java + RestAssured  
- Load/Performance Smoke Testing using Apache JMeter  

---

## Project Overview

### UI Functional Tests (Selenium + TestNG)
Test automation for the careers page of https://insiderone.com 

Flows:
- Open homepage  
- Navigate to QA careers  
- Filter by location and department  
- Validate filtered job listings  
- Verify job details open in a new tab

This is created as two separate test classes:
- VerifyWelcomePageLoaded
- FilterQAJobs

Features:
- Page Object Model (POM)  
- Explicit waits  
- Screenshot on failure  
- Browser parameterization inside each test  

Location: `src/test/java/tests/`

Note:
For running UI tests, chromdriver.exe and geckodriver.exe need to be put inside the project's root folder

---

### API Tests (RestAssured)
Tests against the Swagger Petstore API: https://petstore.swagger.io/

Scenarios:
- Create a pet (POST /pet)  
- Read a pet (GET /pet/{id})  
- Update a pet (PUT /pet)  
- Delete a pet (DELETE /pet/{id})  

Location:
- Tests: `src/test/java/api_tests/`  
- Models: `src/main/java/models/`  

---

### Load/Performance Smoke Tests (JMeter)
Basic performance-smoke test for the n11.com search module.

Flow:
- Open homepage  
- Perform search query  
- Verify results page loads  

Location: `load-tests/`

---
### Room for improvement
- Selecting browser for UI test is set on test level. This can be improved by parametrized TestNG .xml suite.
- Timeouts had to be hardcoded in certain situation, this might've been handled better with fluent wait or explicit wait.
- Some of the test data used for validation could've been abstracted further.
- Screenshot names could've been parametrized with random id so every time they are taken they have a unique name.
