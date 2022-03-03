# Приложение Bank

## О проекте
#### Описание
Приложение реализует упрощенный функционал сервиса по осуществлению банковских операций.
В приложении используется многослойная архитектура - Controller, Service, Model, Repository.
Слой Controller обрабатывает клиентские запросы, Service содержит основную бизнес-логику.
Основная модель данных - Account. 
DTO модели используются для передачи данных между слоями приложения:
DepositDTO - модель пополнения счета, TransferDTO - модель перевода средств со счёта на счет, 
WithdrawDTO - модель для снятия средств. Слой Repository содержит одно хранилище.
Настройки БД: src/main/resources/application.properties. Схема БД: resources/db/schema.sql

#### Технологии
> JDK11, Maven, Spring Boot, Spring Data JPA, PostgreSQL, Liquibase, REST API
>
## Сборка
1. Произвести сборку проекта: `mvn clean install`
2. Скопировать полученный файл "bank-0.0.1-SNAPSHONT.jar" из папки target в папку вашего сервера

## Как пользоваться
Для работы с приложением необходимо создать счет с помощью запроса:
`http://localhost:8080/create-account` 
с передачей в теле запроса JSON объекта Account: `{ "requisite": "00002222", "balance": 500.00 }`, 
при этом баланс не может быть отрицательным.

Для перевода денег с одного счёта на другой: `http://localhost:8080/transfer`,
с передачей в теле запроса JSON объекта TransferDTO: 
`{"senderRequisite":"00002222","recipientRequisite": "00001111","amount":100.00}`

Для пополнения счета: `http://localhost:8080/deposit`,
с передачей в теле запроса JSON объекта DepositDTO: 
`{"recipientRequisite": "00001111","amount":100.00}`

Для снятия денег со счёта: `http://localhost:8080/withdraw`,
с передачей в теле запроса JSON объекта WithdrawDTO: 
`{"ownerRequisite": "00002222","amount":200.00}`

## Контакты
Кутявин Владимир Анатольевич

skype: tribuna87

email: tribuna87@mail.ru

telegram: @kutiavinvladimir