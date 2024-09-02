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


В MySQL нет строгого разделения на схемы, как это сделано в других СУБД. Вместо схем используется концепция каталогов, которые в MySQL соответствуют базам данных. Поэтому при работе с MySQL вам следует использовать каталог (`catalog`) вместо схемы (`schema`).

Если вы пытаетесь получить список схем в MySQL, код `getString("TABLE_SCHEM")` не будет работать, потому что в MySQL схемы отсутствуют. Вместо этого вам следует использовать `getCatalogs()` для получения списка баз данных, а метод `getTables()` для получения таблиц из определенной базы данных.

### Пример получения списка таблиц в MySQL:

```java
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTableExample {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your_database";
        String username = "your_username";
        String password = "your_password";

        Connection connection = null;

        try {
            // Устанавливаем соединение с базой данных
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Получаем объект DatabaseMetaData
            DatabaseMetaData metaData = connection.getMetaData();

            // Получаем список таблиц
            ResultSet tables = metaData.getTables("your_database", null, "%", new String[] { "TABLE" });

            // Перебираем результат и выводим имена таблиц
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Таблица: " + tableName);
            }

            // Закрываем ResultSet
            tables.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Закрываем соединение
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

### Объяснение:
- **Первый параметр** в методе `getTables` теперь является именем базы данных (каталога), в которой вы хотите получить список таблиц (`"your_database"`).
- **Второй параметр** для схемы (`null`) можно оставить пустым, так как в MySQL нет схем.

Этот код корректно работает в MySQL и вернет список всех таблиц из указанной базы данных.

---

В MySQL нет строгого разделения на схемы, как это сделано в других СУБД. Вместо схем используется концепция каталогов, которые в MySQL соответствуют базам данных. Поэтому при работе с MySQL вам следует использовать каталог (`catalog`) вместо схемы (`schema`).

Если вы пытаетесь получить список схем в MySQL, код `getString("TABLE_SCHEM")` не будет работать, потому что в MySQL схемы отсутствуют. Вместо этого вам следует использовать `getCatalogs()` для получения списка баз данных, а метод `getTables()` для получения таблиц из определенной базы данных.

### Пример получения списка таблиц в MySQL:

```java
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTableExample {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your_database";
        String username = "your_username";
        String password = "your_password";

        Connection connection = null;

        try {
            // Устанавливаем соединение с базой данных
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Получаем объект DatabaseMetaData
            DatabaseMetaData metaData = connection.getMetaData();

            // Получаем список таблиц
            ResultSet tables = metaData.getTables("your_database", null, "%", new String[] { "TABLE" });

            // Перебираем результат и выводим имена таблиц
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Таблица: " + tableName);
            }

            // Закрываем ResultSet
            tables.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Закрываем соединение
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

### Объяснение:
- **Первый параметр** в методе `getTables` теперь является именем базы данных (каталога), в которой вы хотите получить список таблиц (`"your_database"`).
- **Второй параметр** для схемы (`null`) можно оставить пустым, так как в MySQL нет схем.

Этот код корректно работает в MySQL и вернет список всех таблиц из указанной базы данных.

---

В представленном коде ошибки, связанные с неиспользованием транзакций, могут возникнуть в следующих случаях:

1. **Удаление данных без защиты от частичного выполнения**: Код сначала удаляет запись из таблицы `flight` (удаляет рейс) с заданным `id`, затем удаляет связанные записи из таблицы `ticket` (удаляет билеты на этот рейс). Если первая операция выполнится успешно, но вторая вызовет ошибку, данные останутся в неконсистентном состоянии. Например, рейс будет удален, а связанные билеты — нет, что нарушает целостность данных.

2. **Проблемы с сохранением результатов изменений**: Если по какой-либо причине удаление билетов пройдет неуспешно (например, из-за блокировок или других ошибок), без транзакции изменения, сделанные в первом запросе (удаление рейса), уже не могут быть откатаны, что также может привести к потере данных или их несоответствию.

3. **Нарушение бизнес-логики**: В случае успешного выполнения первого запроса и неуспешного второго, может произойти нарушение бизнес-логики приложения, если билеты должны быть удалены вместе с рейсом.

Для решения этих проблем необходимо использовать транзакции, которые гарантируют, что все операции (удаление рейса и билетов) будут выполнены как единое целое. Если хотя бы одна из операций не удастся, транзакция будет откатана, и состояние базы данных останется неизменным.

Пример исправленного кода с использованием транзакций:

```java
try (Connection connection = ConnectionManager.open()) {
    connection.setAutoCommit(false); // Начало транзакции

    try (PreparedStatement preparedStatement_1 = connection.prepareStatement(delete_flight_by_id);
         PreparedStatement preparedStatement_2 = connection.prepareStatement(delete_referenced_ticket);
         PreparedStatement preparedStatement_3 = connection.prepareStatement(select_all_from_flight_table)) {

        preparedStatement_1.setLong(1, flight_id);
        preparedStatement_2.setLong(1, flight_id);

        preparedStatement_1.executeUpdate();
        preparedStatement_2.executeUpdate(); // Удаление билетов

        connection.commit(); // Фиксация транзакции

        ResultSet resultSet = preparedStatement_3.executeQuery();
        while (resultSet.next()) {
            System.out.println("id = " + resultSet.getInt(1));
        }
    } catch (SQLException e) {
        connection.rollback(); // Откат транзакции в случае ошибки
        e.printStackTrace();
    }
} catch (SQLException e) {
    e.printStackTrace();
}
```

Теперь, если произойдет ошибка в любом из запросов, все изменения будут откатаны, и база данных останется в непротиворечивом состоянии.

---

Причина, по которой в данном случае не получается использовать try-with-resources, заключается в том, что переменные Connection и PreparedStatement инициализируются вне блока try-with-resources, и это приводит к нескольким проблемам:

1. Переменные не могут быть инициализированы до блока try-with-resources: Когда вы используете try-with-resources, ресурсы должны быть созданы и инициализированы в том же блоке try, где они будут автоматически закрыты. В вашем коде переменные connection, preparedStatement_1, preparedStatement_2, и preparedStatement_3 объявлены и инициализированы до блока try-with-resources, что делает их недоступными для автоматического закрытия.

2. Переменные могут быть неинициализированы: В случае возникновения ошибки при инициализации ресурсов вне блока try, переменные могут остаться неинициализированными, что приведет к NullPointerException при попытке их закрыть в блоке finally (если он используется).
