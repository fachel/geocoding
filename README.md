## Задача
Необходимо разработать приложение с возможностью прямого и обратного геокодирования (из координат в адрес и наоборот). 
Для геокодирования использовался  API  Яндекса. 
Таким образом приложение является кэширующим прокси-сервисом для стороннего сервиса геокодирования. 
Приложение должно предоставлять API с JSON форматом данных.

## Кеширование
В качестве кеша  используется БД Postgres.

## Логирование
Логирование ошибок производилось в методе "handleException()", в котором логи ошибок записывались в БД. Логирование приложения в целом производилось с помощью Slf4j.

## Настройка
- Создать таблицы из файла sql.txt
- В файле application.properties вписать свой url, username, password для БД. Также в нем вписать свой API ключ Яндекса.

## Запуск
В начале запроса всегда "/api/". 

Если надо получить координаты по адресу: "/api/address/*пишем адрес*".

Если надо получить адрес по координатам: "/api/coordinates/*пишем координаты*".

## Примеры
Получение адреса по координатам:
```
http://localhost:8080/api/coordinates/37.611347,55.76024
```
Получение координат по адресу:
```
http://localhost:8080/api/address/москва+тверская
```
Запрос «Москва Тверская», первые 5 результатов:
```
http://localhost:8080/api/address/москва+тверская?results=5
```
Запрос «Москва Тверская», 5 результатов, начиная с 3-го:
```
http://localhost:8080/api/address/москва+тверская?results=5&skip=2
```

## Стек
- Java
- Spring Boot
- Postgre SQL
- Slf4j
