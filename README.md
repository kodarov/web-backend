# Платформа размещения объявлений по продаже вещей.
<div style="display: flex; align-items: center; justify-content: space-between;">
  <div style="width: 60%;">
    <h3>Дипломный проект
    <p>Автор: Кодаров Салават.</p></h3>
<h4>Цель проекта - создать Backend для веб-сайта на котором пользователи могут размещать объявления о продаже своих вещей, просматривать объявления других пользователей, общаться с продавцами и покупателями через чат.
Присутствует редактирование пользователя через личный кабинет.</h4>
  </div>
  <img src="banner.gif" alt="Лого проекта" width="400" hspace="20"/>
</div>



## Технологии и инструменты

Проект написан на языке Java 11 с использованием фреймворка Spring Boot/Web. Для хранения данных используется база данных
PostgreSQL 16, для управления миграциями - Liquibase. 
Для контейнеризации приложения используется Docker-compose. Проект написан по готовой [cпецификации](https://example.com/path/to/your/specification.yaml)
фронтенда 
[avito на react](https://github.com/BizinMitya/front-react-avito).

![Java](https://img.shields.io/badge/java-11-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring--boot-2.7.15-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-16-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Liquebase](https://img.shields.io/badge/liquibase-%230db7ed.svg?style=for-the-badge&logo=liquibase&logoColor=white)
![Hibernate](https://img.shields.io/badge/hibernate-%236DB33F.svg?style=for-the-badge&logo=hibernate&logoColor=white)

## Функциональность

Проект реализует следующие функции:

- Авторизация и аутентификация пользователей с помощью Basic authentication.
- Распределение ролей между пользователями: пользователь и администратор.
- CRUD-операции для объявлений и комментариев: администратор может удалять или редактировать все объявления и комментарии, а пользователи — только свои.
- Возможность для пользователей оставлять комментарии под каждым объявлением.
- Показ и сохранение картинок объявлений, а также аватарок пользователей.
- Просмотр детальной информации об объявлении, включая фотографии, описание, контакты продавца и комментарии пользователей
- Просмотр и редактирование своего профиля, включая имя, фамилию, телефон, аватар и пароль.

## Запуск проекта

Для запуска проекта вам понадобятся следующие программы:

- Java 11 или выше
- Maven 3.6.3 или выше
- Docker 20.10.8 или выше
- PostgreSQL 16 или выше

Следуйте этим шагам, чтобы запустить проект:

1. Клонируйте репозиторий проекта с GitHub:

```bash
git clone https://github.com/kodarov/web-backend.git
```

2. Перейдите в папку проекта:

```bash
cd web-backend
```

3. Соберите проект с помощью Maven:

```bash
mvn clean package
```

4. Запустите Docker:

```bash
docker-compose up
```

5. После того, как Docker запустит все необходимые контейнеры, вы можете обратиться к приложению по адресу:

```bash
http://localhost:8080
```

6. Для остановки и удаления контейнеров используйте команду:

```bash
docker-compose down
```

## Документация API
Спецификации можно посмотреть на 
```bash
https://editor.swagger.io/ 
```
скопировав код из
[openapi.yaml](https://github.com/BizinMitya/front-react-avito/blob/main/openapi.yaml)

Вы также можете просмотреть и протестировать API по адресу:

```bash
http://localhost:8080/swagger-ui.html
```


## Применение Testсontainers <img src="https://avatars.githubusercontent.com/u/13393021?s=200&v=4" alt="Testcontainers" width="50" height="50">

В этом проекте <b>Testcontainers</b> используется для создания контейнеров PostgreSQL в процессе интеграционного тестирования. Это обеспечивает консистентность и надежность тестов, поскольку каждый тест контроллера работает со своим собственным экземпляром базы данных.



## Контакты

Если у вас есть вопросы или предложения по проекту, вы можете связаться со мной по электронной почте:

```bash
kodarov@gmail.com
```

Надеюсь, вам понравится мой проект! 😊