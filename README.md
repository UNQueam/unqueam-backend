# UNQueam Backend Project

> This project is the backend of the gaming platform UNQueam. It is developed with Kotlin and Spring as a framework.

## Prerequisites

Before you begin, make sure you have the following installed on your system:

- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Kotlin](https://kotlinlang.org/docs/tutorials/command-line.html)
- [MySQL](https://www.mysql.com/downloads/)

## Project Setup

Follow these steps to configure and run the project:

1. **Clone the repository:** If you're using Git, clone this repository to your local machine. If not, you can also download the project as a ZIP file and unzip it.

    ```sh
    git clone git@github.com:UNQueam/unqueam-backend.git
    cd unqueam-backend
    ```

2. **Configure the database:** Open the `src/main/resources/application.properties` file. Database configuration:

   ```properties
   spring.datasource.url=${MYSQL_URL}
   spring.datasource.username= ${MYSQL_USERNAME}
   spring.datasource.password=${MYSQL_PASSWORD}
   ```
   
   You **must** configure the Environment Variables.
   Example:
   ```properties
   MYSQL_USERNAME=root
   MYSQL_PASSWORD=password
   MYSQL_URL=jdbc:mysql://localhost:3306/unqueam-db
   ```

3. **Run the application:** From your IDE, find the main class (usually annotated with `@SpringBootApplication`) and run it as a Kotlin application.

    You can also run the application from the command line using Gradle:

    ```sh
    ./gradlew bootRun
    ```

4. **Access the application:** Once the application is running, open a web browser and navigate to `http://localhost:8080` to see the Spring Boot home page.

## Customization and Development

This project is configured with Gradle and provides a basic structure to get started. You can add your own controllers, services, and domain classes in the respective packages.

To add new dependencies, modify the `build.gradle.kts` file.

## Contribution

If you'd like to contribute to this project, you're welcome to do so! Open an issue to discuss your ideas or submit a pull request with your proposed changes.

## License

This project is under the [MIT license](LICENSE).

---

## Project structure

The project is organized based on responsibilities distributed in the following packages:

#### application
> This layer provides an exposure of the API to the clients. It is composed of the following sub-packages:

- dtos: Contains objects that act as inputs and outputs of our system.
- http: This package houses the Controllers that expose the different endpoints.

#### core
> This layer contains the core business logic of the platform. It contains the following sub-packages:

- domain: Here reside the objects that represent the fundamental concepts of our business.
- services: This subpackage contains the interactors, which group one or more use cases. These interactors are responsible for orchestrating the domain objects to carry out the desired operations.

#### infrastructure
> This layer focuses on the infrastructure aspects of the application and external dependencies. It includes the following subpackages:

- configuration: instructions for instantiating Spring Boot Bean objects are defined here, configuring the application environment.
- persistence: Contains the definition and implementation of the repositories, which allow interacting with the persistent data storage.
