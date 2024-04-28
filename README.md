# ProvaOnline

ProvaOnline is a backend application developed in Quarkus and frontend in Svelte. 
It allows the creation and application of exams based on a JSON file containing questions and single-choice answer options. At the end of the exam, the application displays which questions were answered correctly.

## Directory Contents

- **ProvaOnline:** Contains test scripts using the Bruno tool.
- **provaonline-backend:** Backend of the REST API written in Java with Quarkus.
- **provaonline-frontend:** Frontend of the application written in JavaScript with Svelte.

## Prerequisites

To run the project, the following prerequisites need to be installed:

- Maven 3.8.6
- Node.js v20.11.0
- npm 10.2.4

## Usage

1. In the `provaonline-backend` subdirectory, execute the following command to start app in development mode:

		mvn quarkus:dev

2. Open a web browser and access the following address:

		http://localhost:8080/


Internally, Maven with the *frontend-maven-plugin* compile svelte pages, ensuring its availability in the Quarkus static folder.  Then, you don't need to start frontend project.

Additionally, within the resources folder, you'll find an examtests_questions.json file. I've included simulated questions for the Cloud Digital Leader exam as a sample. Feel free to substitute them with alternative questions as needed.


## License

This project is licensed under the terms of the MIT license. See the licence file for details.

## Notice

This project was developed as part of system architecture playground and is not recommended for production environments without reviewing.

If this project helped you in any way, please star it :star:



