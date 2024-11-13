# Event manager

Приложение управления и бронирования мероприятий.
Для запуска приложения необходимо разврнуть Docker контейнер для БД postgres

## Разворачивание БД в Docker

log/pass: postgres/postgres
-  Для развертывания postgres БД нужно запустить команду:
docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres
- После разворачивания контейнера можно подключиться к БД по JDBC URL:
  jdbc:postgresql://localhost:5432/postgres


### Open API спецификация

В статических ресурсах приложения (resources/static/openapi.yaml) 
размещена openapi спецификация для взаимодействия с приложением