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
`\033[0m` — Сброс цвета и форматирования.
`\033[0;31m` — Красный цвет.
`\033[0;32m` — Зеленый цвет.
`\033[0;33m` — Желтый цвет.
`\033[0;34m` — Синий цвет.

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

В PostgreSQL, возможность выполнения нескольких SQL-команд в одном запросе более гибкая по сравнению с MySQL. Вот основные причины, почему ваш код работает в PostgreSQL, но вызывает ошибку в MySQL:

1. **Множественные SQL-команды**: PostgreSQL поддерживает выполнение нескольких SQL-команд в одном запросе, если команды разделены точкой с запятой. Это позволяет выполнять такие запросы как `USE database_name; INSERT INTO ...; INSERT INTO ...;`. Однако в MySQL такая возможность по умолчанию может быть ограничена, и выполнение нескольких команд может требовать настройки или дополнительных методов.

2. **Управление транзакциями**: PostgreSQL поддерживает выполнение нескольких SQL-команд в одной транзакции и может автоматически обрабатывать такие команды, как `BEGIN`, `COMMIT`, и `ROLLBACK`. В MySQL вы можете столкнуться с проблемами, если выполняете несколько команд в одном вызове `execute()`, потому что они могут требовать явного управления транзакциями.

3. **Разные уровни SQL-движков**: PostgreSQL и MySQL имеют разные уровни поддержки стандартов SQL и различные особенности реализации. То, что работает в одном СУБД, не всегда будет работать в другой без модификации.

### Почему это важно для вашего случая

- **Разделение команд**: Чтобы обеспечить совместимость с MySQL, лучше выполнять каждую команду по отдельности. Это позволит избежать проблем с синтаксисом и транзакциями, которые могут возникнуть при попытке выполнить несколько команд в одном вызове.

- **Проверка на ошибки**: MySQL и PostgreSQL могут обрабатывать ошибки SQL по-разному. В PostgreSQL более прощен к множественным запросам, тогда как MySQL может потребовать явного управления запросами.

### Рекомендация

Для совместимости с MySQL всегда разделяйте SQL-команды и выполняйте их по отдельности, как это сделано в исправленном коде выше. Это сделает ваш код более переносимым и надежным при работе с различными базами данных.


---

Ваша ошибка `java.sql.SQLSyntaxErrorException` указывает на проблему в SQL-синтаксисе. В вашем коде ошибка вызвана использованием нескольких SQL-команд в одном строковом литерале, что может быть проблематичным при выполнении SQL в Java.

Давайте разберем основные проблемы и предложим исправление:

1. **Множественные SQL-команды в одном запросе**: MySQL может не поддерживать выполнение нескольких SQL-команд через один вызов `Statement.execute()`. Для этого лучше использовать отдельные запросы для каждой операции.

2. **Неудачный формат многострочных строк**: Вы используете текстовый блок (многострочные строки) в Java, но для выполнения нескольких SQL-команд их лучше разделять на отдельные строки и выполнить каждую команду отдельно.

Вот как можно переписать ваш код, чтобы избежать ошибки:

```java
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = ConnectionManager.open()) {
            System.out.println("\033[0;32mConnection established successfully!\033[0m");
            Statement statement = connection.createStatement();

            // Выбор базы данных
            statement.execute("USE literature");

            // Вставка данных
            statement.execute("INSERT INTO authors (first_name, last_name, date_of_birth, date_of_death, nationality, biography, email, website, gender, profile_picture) " +
                    "VALUES ('Mark', 'Twain', '1835-11-30', '1910-04-21', 'American', 'American writer known for The Adventures of Tom Sawyer and Adventures of Huckleberry Finn.', 'mark.twain@example.com', 'http://twain.com', 'Male', 'twain.jpg')");

            statement.execute("INSERT INTO authors (first_name, last_name, date_of_birth, date_of_death, nationality, biography, email, website, gender, profile_picture) " +
                    "VALUES ('Virginia', 'Woolf', '1882-01-25', '1941-03-28', 'British', 'English writer known for Mrs Dalloway and To the Lighthouse.', 'virginia.woolf@example.com', 'http://woolf.com', 'Female', 'woolf.jpg')");

            statement.execute("INSERT INTO authors (first_name, last_name, date_of_birth, date_of_death, nationality, biography, email, website, gender, profile_picture) " +
                    "VALUES ('Gabriel', 'Garcia Marquez', '1927-03-06', '2014-04-17', 'Colombian', 'Colombian novelist known for One Hundred Years of Solitude.', 'gabriel.garcia@example.com', 'http://garcia-marquez.com', 'Male', 'garcia.jpg')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

В этом коде:
- Я разделил команды `INSERT` на несколько вызовов `statement.execute()`.
- Убедился, что `USE literature;` выполняется отдельно перед выполнением команд `INSERT`.

Таким образом, код будет корректно исполнять SQL-команды и не вызовет ошибку синтаксиса.
