Оргагнизовать взаимодействие микросервисов посредством REST

Сервис по shell команде getAll запрашивает список всех книг у сервиса /spring-16

Сервис по shell команде add добавляет книгу в сервисе /spring-16 через kafka. kafka должен быть поднят отдельно на localhost:9092.

Сервис по shell команде get -id запрашивает книгу у сервиса spring-25 с использованием circuitBreaker Resilience4J.
Статистика вызовов http://localhost:8080/actuator/metrics      /resilience4j.circuitbreaker.*
