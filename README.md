# Advertisement Platform
<div style="display: flex; align-items: center; justify-content: space-between;">
  <div style="width: 60%;">
    <h3>
    <p>Author: Salavat Kodarov</p></h3>
<h4>Adv is a back-end for a website where users can post
advertisements for selling their items, view ads from 
other users, communicate with sellers and buyers through a chat. 
User profile editing is also available.</h4>
  </div>
  <img src="banner.gif" alt="Лого проекта" width="400" hspace="20"/>
</div>



## Technologies and Tools

The project is written in Java 11 using the Spring Boot/Web framework. PostgreSQL 16 is used for data storage, and Liquibase is used for migration management.
Docker-compose is used for containerizing the application.
The project is based on a ready-made [front-end specification](https://example.com/path/to/your/specification.yaml) for the frontend
[ADV на react](https://github.com/BizinMitya/front-react-avito).

![Java](https://img.shields.io/badge/java-11-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring--boot-2.7.15-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-16-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Liquebase](https://img.shields.io/badge/liquibase-%230db7ed.svg?style=for-the-badge&logo=liquibase&logoColor=white)
![Hibernate](https://img.shields.io/badge/hibernate-%236DB33F.svg?style=for-the-badge&logo=hibernate&logoColor=white)

## Functionality

Проект реализует следующие функции:

- Authorization and user authentication using Basic authentication.
- Role distribution among users: user and administrator.
- CRUD operations for ads and comments: the administrator can delete or edit all ads and comments, while users can only modify their own.
- Ability for users to leave comments under each ad.
- Display and storage of ad images, as well as user avatars.
- Viewing detailed information about an ad, including photos, description, seller's contacts, and user comments.
- Viewing and editing one's profile, including name, surname, phone, avatar, and password.

## Project Launch
To launch the project, you will need the following programs:

- Java 11 or higher
- Maven 3.6.3 or higher
- Docker 20.10.8 or higher
- PostgreSQL 16 or higher
-
Follow these steps to run the project:

1. Clone the project repository from GitHub:

```bash
git clone https://github.com/kodarov/web-backend.git
```

2. Navigate to the project folder:

```bash
cd web-backend
```

3. Build the project using Maven:

```bash
mvn clean package
```

4. Start Docker:

```bash
docker-compose up
```

5. Once Docker starts all necessary containers, you can access the application at:

```bash
http://localhost:8080
```

6. To stop and remove the containers, use the command:

```bash
docker-compose down
```

## API Documentation
Specifications can be viewed at
```bash
https://editor.swagger.io/ 
```
by copying the code from openapi.yaml
[openapi.yaml](https://github.com/BizinMitya/front-react-avito/blob/main/openapi.yaml)

You can also view and test the API at:

```bash
http://localhost:8080/swagger-ui.html
```


## Using Testcontainers <img src="https://avatars.githubusercontent.com/u/13393021?s=200&v=4" alt="Testcontainers" width="50" height="50">

n this project, <b>Testcontainers</b> is used to create PostgreSQL containers during integration testing. This ensures consistency and reliability of tests, as each controller test operates with its own instance of the database.



## Contacts

If you have any questions or suggestions about the project, you can contact me by email:

```bash
kodarov@gmail.com
```

Hope you enjoy my project! 😊