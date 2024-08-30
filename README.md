# Code Explanation

Пробрасывание исключений, как в нашем коде, с помощью `RuntimeException`, имеет свои плюсы и минусы. Давайте разберёмся, почему такой подход может считаться оптимизацией и в каких ситуациях он применяется.

## Код

```java
public static Connection open() {
    try {
        return DriverManager.getConnection(DATABASE_NAME, USER_NAME, PASSWORD);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
```

### Объяснение

1. **Упрощение обработки исключений:**
   В оригинальной версии метода `DriverManager.getConnection`, если происходит ошибка, генерируется `SQLException`, который является проверяемым исключением (checked exception). Это значит, что в каждом месте, где вызывается метод `open()`, вам пришлось бы либо обрабатывать это исключение с помощью `try-catch`, либо пробрасывать его дальше по цепочке вызовов через `throws SQLException`.

   Преобразование `SQLException` в `RuntimeException` (непроверяемое исключение) позволяет избежать необходимости явно обрабатывать или пробрасывать исключение. Таким образом, метод `open()` можно использовать без лишних `try-catch` блоков в вызывающем коде. Это делает код более чистым и проще в написании, особенно если вы уверены, что ошибка должна прерывать выполнение программы.

2. **Передача ошибки дальше:**
   Если что-то пошло не так при подключении к базе данных, в большинстве случаев это ошибка, которая не может быть легко исправлена в коде, вызвавшем этот метод. Пробрасывание `RuntimeException` позволяет прекратить выполнение программы и сигнализировать об ошибке на более высоком уровне, что может быть полезно в случае критических ошибок, таких как невозможность подключения к базе данных.

3. **Повышение читаемости:**
   Код становится проще и легче для понимания. Вам не нужно беспокоиться о каждом `SQLException` в вашем коде — все ошибки автоматически "пробрасываются" в виде непроверяемых исключений.

### Минусы такого подхода

1. **Потеря гибкости в обработке:**
   Поскольку исключение превращается в `RuntimeException`, вызывающий код не обязан его обрабатывать. Это может привести к ситуации, когда ошибка останется необработанной, что может усложнить отладку и слежение за ошибками.

2. **Меньше контроля:**
   Когда вы пробрасываете исключение как `RuntimeException`, вы теряете контроль над тем, как оно будет обрабатываться. В некоторых случаях это может привести к неожиданным последствиям.

### Когда использовать

Этот подход полезен в случаях, когда ошибка, как правило, является критической и должна завершить выполнение программы, или когда проверка и обработка исключений в каждом вызове не является необходимой и лишь утяжеляет код.

Итак, такой способ обработки исключений оптимизирует ваш код, делая его чище и проще, но требует осмотрительности и понимания последствий, связанных с тем, что ошибки могут не быть должным образом обработаны.

---

## Скрипт для создания базы данных

`create_flight_repository` - создает базу данных полетов

Слудующие скрипты вставлют данные в соответствующие таблицы

- `insert_into_aircraft_table`
- `insert_into_airport_table`
- `insert_into_flight_table`
- `insert_into_seat_table`
- `insert_into_ticket_table`


---

В ходе изучения темы я буду создавать различные тематические ветки и каждая ветка будет отходить от точки initial commit


---

Colored output to console
- `\033[0m` — Сброс цвета и форматирования.
- `\033[0;31m` — Красный цвет.
- `\033[0;32m` — Зеленый цвет.
- `\033[0;33m` — Желтый цвет.
- `\033[0;34m` — Синий цвет.

---

Давайте разберем, как работает этот код:

```java
PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")
```

### 1. `PropertiesUtil.class`
Этот элемент кода возвращает объект `Class`, представляющий класс `PropertiesUtil`. Используя этот объект, можно получить доступ к метаданным класса, а также к методам для работы с загрузкой ресурсов и загрузчиком классов.

### 2. `getClassLoader()`
Метод `getClassLoader()` возвращает загрузчик классов (`ClassLoader`), который загрузил данный класс. В этом случае, `PropertiesUtil.class.getClassLoader()` возвращает загрузчик классов, который загрузил класс `PropertiesUtil`.

Загрузчики классов в Java ответственны за загрузку классов и ресурсов (например, файлов) в память JVM. В зависимости от среды исполнения (например, веб-сервер, десктопное приложение), могут использоваться различные загрузчики классов.

### 3. `getResourceAsStream("application.properties")`
Этот метод используется для загрузки ресурса (в данном случае файла `application.properties`) как потока ввода (`InputStream`).

#### Как это работает:
- Метод `getResourceAsStream()` ищет ресурс (например, файл) с указанным именем на пути к классам, доступном этому загрузчику классов.
- Если ресурс найден, метод возвращает поток ввода (`InputStream`), через который можно прочитать содержимое файла.
- Если ресурс не найден, метод возвращает `null`.

### Общий механизм работы:

1. **Загрузка класса:**
   JVM загрузила класс `PropertiesUtil` с помощью определенного загрузчика классов (`ClassLoader`). Этот загрузчик классов отвечает за поиск и загрузку всех ресурсов, связанных с классом `PropertiesUtil`.

2. **Поиск ресурса:**
   Когда вызывается `getResourceAsStream("application.properties")`, загрузчик классов ищет файл `application.properties` в директориях или JAR-файлах, которые он обрабатывает. Например, если этот файл находится в корне директории `src/main/resources`, то загрузчик классов найдет его там.

3. **Открытие потока:**
   Если файл `application.properties` найден, `getResourceAsStream` возвращает поток ввода (`InputStream`), который можно использовать для чтения содержимого файла.

### Пример использования:

```java
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream input = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return null;
            }

            // load a properties file from class path, inside static method
            properties.load(input);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return properties;
    }
}
```

### Краткое резюме:
Этот код использует загрузчик классов для поиска файла `application.properties` в пути классов. Если файл найден, он возвращается в виде потока ввода, который затем можно использовать для загрузки конфигурации в объект `Properties`. Это обычная практика для работы с конфигурационными файлами, размещенными в ресурсах приложения.


---

Этот код небезопасен, потому что он уязвим для SQL-инъекций. Давайте рассмотрим подробнее:

### Проблема: Уязвимость к SQL-инъекции
В коде используется метод `String.formatted()` для вставки значения переменной `flightId` непосредственно в SQL-запрос:

```java
String select_ticket_by_flight_id = """
    select
        id
    from
        ticket
    where
        flight_id = %s
""".formatted(flightId);
```

Если переменная `flightId` содержит вредоносный SQL-код, этот код будет вставлен напрямую в запрос, что может привести к SQL-инъекции. Например, если злоумышленник передаст в `flightId` следующую строку:

```java
"1 OR 1=1"
```

То сформированный запрос будет выглядеть так:

```sql
SELECT id FROM ticket WHERE flight_id = 1 OR 1=1
```

Этот запрос вернет все строки из таблицы `ticket`, что не является ожидаемым поведением.

### Безопасное решение: Использование PreparedStatement
Чтобы избежать SQL-инъекций, следует использовать подготовленные выражения (`PreparedStatement`). В этом случае параметры запроса передаются отдельно от самого SQL-кода, что предотвращает возможность их интерпретации как SQL-команды.

Вот как можно переписать ваш код, используя `PreparedStatement`:

```java
private static List<Long> getTicketsByFlightId(String flightId) throws SQLException {
    List<Long> selectedId = new ArrayList<Long>();
    String select_ticket_by_flight_id = """
        select
            id
        from
            ticket
        where
            flight_id = ?
    """;
    try (Connection connection = ConnectionManager.open();
         PreparedStatement preparedStatement = connection.prepareStatement(select_ticket_by_flight_id)) {
        preparedStatement.setString(1, flightId); // Устанавливаем значение flightId как параметр
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            selectedId.add(resultSet.getObject(1, Long.class)); // На случай, если resultSet будет содержать null
        }
    }
    return selectedId;
}
```

### Почему это решение безопасно:
1. **Параметризованный запрос:** Значение `flightId` передается в запрос как параметр, и база данных гарантирует, что оно будет интерпретироваться как строковое значение, а не как часть SQL-кода.

2. **Защита от SQL-инъекций:** Даже если злоумышленник попытается передать вредоносный код в `flightId`, он будет интерпретирован как обычная строка, что предотвращает SQL-инъекцию.

Таким образом, использование `PreparedStatement` вместо конкатенации строк для создания SQL-запросов делает ваш код более безопасным и устойчивым к атакам SQL-инъекций.

---


Для избежания SQL-инъекций в JDBC не рекомендуется использовать методы, которые позволяют напрямую вставлять пользовательский ввод в SQL-запросы. Основные методы, которых следует избегать:

### 1. **Statement.executeQuery() и Statement.executeUpdate()**
   - Эти методы позволяют выполнять SQL-запросы, создавая строку запроса, которая включает в себя пользовательский ввод напрямую. Это делает их уязвимыми для SQL-инъекций, если пользовательский ввод не экранирован или не проверен должным образом.
   - **Пример опасного использования:**
     ```java
     Statement statement = connection.createStatement();
     String query = "SELECT * FROM users WHERE username = '" + userInput + "' AND password = '" + passwordInput + "'";
     ResultSet resultSet = statement.executeQuery(query);
     ```
   - В этом примере злоумышленник может вставить SQL-код в переменные `userInput` или `passwordInput`, что приведет к SQL-инъекции.

### 2. **Statement.execute()**
   - Метод `execute()` также позволяет выполнять любые SQL-запросы, включая `SELECT`, `INSERT`, `UPDATE`, `DELETE`, и т.д. Если запрос создается с использованием строковой конкатенации и пользовательского ввода, то это представляет аналогичный риск SQL-инъекции.
   - **Пример опасного использования:**
     ```java
     Statement statement = connection.createStatement();
     String query = "DELETE FROM users WHERE user_id = " + userInput;
     statement.execute(query);
     ```
   - Если `userInput` содержит вредоносный код, то это может привести к удалению всех пользователей или другим нежелательным последствиям.

### 3. **Statement.addBatch()**
   - Метод `addBatch()` позволяет добавлять несколько SQL-команд в пакет для последующего выполнения. Если SQL-команды в пакете формируются с использованием строковой конкатенации и пользовательского ввода, то это также создает риск SQL-инъекций.
   - **Пример опасного использования:**
     ```java
     Statement statement = connection.createStatement();
     String query1 = "INSERT INTO users (username, password) VALUES ('" + userInput1 + "', '" + passwordInput1 + "')";
     String query2 = "INSERT INTO users (username, password) VALUES ('" + userInput2 + "', '" + passwordInput2 + "')";
     statement.addBatch(query1);
     statement.addBatch(query2);
     statement.executeBatch();
     ```
   - Если пользовательский ввод содержит вредоносный SQL-код, то это может привести к выполнению непредусмотренных запросов.

### **Что использовать вместо этого:**

Для предотвращения SQL-инъекций, рекомендуется использовать **подготовленные выражения (Prepared Statements)**, которые надежно обрабатывают пользовательский ввод и защищают от подобных атак. Подготовленные выражения автоматически экранируют ввод, гарантируя, что он будет интерпретирован как данные, а не как SQL-код.

**Пример безопасного использования PreparedStatement:**

```java
String query = "SELECT * FROM users WHERE username = ? AND password = ?";
PreparedStatement preparedStatement = connection.prepareStatement(query);
preparedStatement.setString(1, userInput);
preparedStatement.setString(2, passwordInput);
ResultSet resultSet = preparedStatement.executeQuery();
```

В этом примере пользовательский ввод `userInput` и `passwordInput` передается в запрос как параметры, что исключает возможность SQL-инъекции.

---

Верно, методы `execute`, `executeQuery`, и `executeUpdate` для `PreparedStatement` не принимают SQL-запрос в качестве параметра, потому что SQL-запрос уже был передан в `PreparedStatement` при его создании. Вот как работают эти методы:

### Методы `PreparedStatement`

1. **`executeQuery()`**
   - Выполняет SQL-запрос, который возвращает результат в виде `ResultSet` (например, SELECT-запросы).
   - Не принимает SQL-запроса как параметра.
   - **Пример:**
     ```java
     String sql = "SELECT * FROM users WHERE username = ?";
     PreparedStatement preparedStatement = connection.prepareStatement(sql);
     preparedStatement.setString(1, "john_doe");
     ResultSet resultSet = preparedStatement.executeQuery();
     ```

2. **`executeUpdate()`**
   - Выполняет SQL-запрос, который изменяет данные в базе данных (например, INSERT, UPDATE, DELETE). Возвращает количество затронутых строк.
   - Не принимает SQL-запроса как параметра.
   - **Пример:**
     ```java
     String sql = "UPDATE users SET password = ? WHERE username = ?";
     PreparedStatement preparedStatement = connection.prepareStatement(sql);
     preparedStatement.setString(1, "new_password");
     preparedStatement.setString(2, "john_doe");
     int affectedRows = preparedStatement.executeUpdate();
     ```

3. **`execute()`**
   - Выполняет SQL-запрос, который может возвращать результат (например, SELECT-запрос) или не возвращать результат (например, INSERT, UPDATE, DELETE). Возвращает `true` если первый результат — это `ResultSet`, и `false` если первый результат — это обновленное количество строк.
   - Не принимает SQL-запроса как параметра.
   - **Пример:**
     ```java
     String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
     PreparedStatement preparedStatement = connection.prepareStatement(sql);
     preparedStatement.setString(1, "john_doe");
     preparedStatement.setString(2, "password123");
     boolean hasResultSet = preparedStatement.execute();
     if (hasResultSet) {
         ResultSet resultSet = preparedStatement.getResultSet();
         // Обработка результата
     } else {
         int affectedRows = preparedStatement.getUpdateCount();
         // Обработка количества затронутых строк
     }
     ```

### Объяснение

1. **`executeQuery()`**:
   - Этот метод используется для выполнения запросов, которые возвращают данные, таких как SELECT-запросы. Результаты запроса возвращаются в виде объекта `ResultSet`.

2. **`executeUpdate()`**:
   - Этот метод используется для выполнения SQL-запросов, которые изменяют данные в базе данных, таких как INSERT, UPDATE, DELETE. Он возвращает количество затронутых строк.

3. **`execute()`**:
   - Этот метод более универсален и может использоваться для выполнения любого SQL-запроса. Он возвращает `true` если результатом выполнения является `ResultSet`, и `false` если результатом является количество затронутых строк.

### Преимущества использования `PreparedStatement`

1. **Безопасность от SQL-инъекций**:
   - `PreparedStatement` защищает от SQL-инъекций, так как SQL-запрос и его параметры передаются отдельно.

2. **Производительность**:
   - Использование `PreparedStatement` может улучшить производительность, так как база данных может оптимизировать выполнение подготовленных запросов и повторно использовать их планы выполнения.

3. **Читаемость и поддерживаемость**:
   - Код становится более читаемым и поддерживаемым, поскольку параметры устанавливаются через методы `setXXX` вместо динамического формирования строк SQL-запросов.

Использование `PreparedStatement` является хорошей практикой в JDBC для безопасного и эффективного выполнения SQL-запросов.
