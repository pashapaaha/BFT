# Тестовое задание компании БФТ

#### Автор: Павел Андреев

## Задание
Разработать веб приложение по генерации сообщений из текстового шаблона.

# Инструкция
*   Перед началом работы с API необходимо создать базу данных. 

Параметры базы:

    host=localhost  
    user=postgres  
    password=12345  
    dbname=bftDatabase  

*   Часть методов, предоставляемых API требует авторизацию пользователя.

Для авторизации используются следующие данные:

    user=user
    password=12345
    
У авторизированного пользователя есть возможность редактировать и просматривать данные в базе при помощи следующих методов: 

#### 1. Вывести все
Для вывода всех существующих шаблонов из базы используется метод GET. Пример запроса:

    localhost:8080/templates/
    
При вызове данного метода будет возвращена инфомация обо всех шаблонах в формате JSON.
    
#### 2. Вывести конкретный шаблон
Для вывода информации о конкретном шаблоне используется метод GET. Пример запроса:

    localhost:8080/templates/1

В данном методе используется поиск шаблона по id, указанного в качестве параметра запроса.  
Если шаблон с указанным id существует, результатом запроса будет информация о шаблоне и статус OK(200).  
Если указанный шаблон отсутствует, будет возвращен статус NotFound(404).

#### 3. Добавить шаблон
Метод - POST. Пример запроса:

    localhost:8080/templates/
    
Пример тела запроса:

        {
            "messageText": "it's {weather} today",
            "parameters": [
                { "name": "weather" }
            ],
            "active": true
        }
        
В случае успешного добавления будет выведена информация о шаблоне, включая его id (id также можно указать и в теле запроса, но он будет проигнорирован).  
Если наборы параметров в тексте сообщения и в поле 'parameters' не совпадают, будет возвращена ошибка BadRequest(400) с соответствующем сообщением, а шаблон не будет добавлен.

   
#### 4. Обновление шаблона
Метод - PUT. Пример:

    localhost:8080/templates/1 
    
Пример тела запроса:
  
    {
      "messageText": "it was {weather} yesterday",
      "parameters": [
          { "name": "weather" }
      ],
      "active": false
    }
    
Id изменяемого шаблона указывается в параметре запроса, а изменяемые данные в его теле. При указании данных о шаблоне необходимо указать все поля, даже те, которые не изменяются.  
Если наборы параметров в тексте сообщения и в поле 'parameters' не совпадают, будет возвращена ошибка BadRequest(400) с соответствующем сообщением, а шаблон не будет изменен.  
Если указанный шаблон отсутствует в базе, будет возвращен статус NotFound(404).

#### 5.  Удаление шаблона
Метод - DELETE. Пример:

    localhost:8080/templates/1 
    
Если указанный шаблон отсутствует в базе, будет возвращен статус NotFound(404).
В случае успеха шаблон будет удален.

*   Без авторизации пользователю доступны методы генерации сообщения из шаблона (синхронный и асинхронный). 

Пример запроса(POST):

    localhost:8080/generate/sync/1 
    localhost:8080/generate/async/1 

Пример тела запроса:

    {
        "weather": "sunny"
    }
    
В случае успешного выполнения запроса пользователю будет выведено сгенерированное сообщение с подстановкой всех указанных параметров.  
Если указанный шаблон отсутствует в базе, будет возвращен статус NotFound(404).  
Если указанный шаблон существует, но не активен, запрос будет иметь статус BadRequest(400) и будет выведено соответствующее сообщение.  
Если введенный пользователем набор параметров не соответствует требуемому, будет выведена подсказка с необходимыми параметрами с статус BadRequest(400).
