# job4j_chat Чат с комнатами.

+ [Описание](#Описание)
+ [Используемые технологии](#Технологии)
+ [Доступ](#Доступ)

## Описание
REST API приложение  "Чат c комнатами".
Пользователь должен зарегистроваться, пройти процесс авторизации по токену.
При регистрации пользователю назначается роль.  
Можно оставлять оставлять сообщения во всех чатах.
Также пользователь может удалить свое сообщение по айди.

## Технологии

+ **Maven**, **Checkstyle**
+ **Java 17**, **Spring Boot**, **Spring Data JPA**, **Spring Security**
+ **PostgreSQL**
+ 
## Доступ
**/login**

### Роли 
**/chat/admin**

**GET /role** найти все роли

**GET /role/{id}** найти роль по айди.

**POST /role** создать роль.

**PUT /role** обновить роль.

**DELETE /role/{id}** удалить роль.

### Комнаты
**/chat**

**GET /room** найти все комнаты.

**GET /room/get/{name}/** найти комнату по имени.

**POST /admin/room** создать комнату.

**DELETE /admin/room/{id}** удалить роль.

### Пользователи

**/chat**

**POST /persons/sign-up** создать пользователя.

**GET /persons** найти всех пользователей.

**GET /persons/{id}** найти пользователя по айди.

**GET /room/{room}/people** найти всех пользователей в комнате.

**PUT /persons** обновить пользователя.

**DELETE /persons/{id}** удалить пользователя.

### Сообщения

**/chat**

**GET /room/{room}** найти все сообщения в заданной комнате.

**POST /room/{nameroom}/{username}/reply** добавить сообщение в комнату.

**DELETE /room/{name}/delete/{id}** удалить сообщение в комнате.