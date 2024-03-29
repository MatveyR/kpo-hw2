# Инициализация
Для запуска проекта рекомендуется использовать Docker и запускать файл docker-compose.yaml из корня проекта (для начала надо сбилдить .jar файл)
Для запуска без Docker (менее предпочтительный вариант) необходимо в файле src/main/recources/application.properties заменить url к базе данных на jdbc:postgresql://localhost:5432/postgres

# Запуск
После запуска проекта api доступно по адресу localhost:8080/ (если не перекидывает сразу на swagger-ui, то localhost:8080/swagger-ui/index.html)

# Используемые технологии
REST API, симулирующее работу ресторана, реализовано на языке Kotlin при помощи Spring Boot
Демонстрация работы эндпоинтов представлена при помощи Swagger-UI
Хранение данных осуществляется при помощи СУБД PostgreSQL

# Инициализация БД
Инициализировать изначальные значения базы данных можно при помощи скриптов из папки src/main/resources/sql

# Симуляция обработки заказов
Заказы обрабатываются по следующим приоритетам: чем выше общая сумма заказа, тем он приоритетнее
Многопоточность приготовления заказов реализована при помощи корутин в сервисе KitchenService
Чтобы потоки одновременно не обращались к одним и тем же данным используется семафор

# Шаблоны проектирования
В проекте использованы следующие шаблоны:
- Синглтон. Используется для хранения информации об авторизованным пользователе
- MVC. Общий шаблон проектирования REST Api
- JPA-Repository. Используется для хранения сущностей. JPA-репозиторий из spring так же позволяет автоматически генерировать запросы к БД
- Объекты DTO
