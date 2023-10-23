# Jaiz Express 

An automated fare collection platform which has been designed with both transport operators 
and travellers in mind, digitalising the payment process end-to-end. 

### System Stack
- Java
- Spring - boot
- Postgres

### Notable Libraries
- Mapstruct
- Lombok
- Hibernate
- Flyway

### API Documentation
- Springdoc Open-Api
- Swagger UI

## Development

To contribute or continue development on this system, Please follow the following steps:

- Install Java 17 and PostgreSQL on your development environment
- Clone this repository
- Create postgres db based on config in application-dev,yml
````
  sudo -u postgres psql
  create database jaiz_express_db;
  create user jaizexpress with encrypted password 'KfiwSDrL6VPP2s5';
  grant all privileges on database jaiz_express_db to jaizexpress;
````
- Build the application
````shell
mvn clean package
````
- Run the application
```shell
mvn spring-boot:run
```
- Create a new branch following this format: YourName-TicketNo
- Make your changes
- Commit your code with a message that must contain the following:
  - Ticket Number and Url
  - Commit Message
- Push and Create a Pull Request to development branch (dev)

`:smiley:` Happy Coding `:smiley:``:smiley:`