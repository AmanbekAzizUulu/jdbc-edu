# Code Explanation

Пробрасывание исключений, как в вашем коде, с помощью `RuntimeException`, имеет свои плюсы и минусы. Давайте разберёмся, почему такой подход может считаться оптимизацией и в каких ситуациях он применяется.

### Код

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


## Скрипт для создания базы данных

This script creates a flight repository database, including tables for airports, aircraft, seats, flights, and tickets. It also inserts sample data and contains several SQL queries to analyze the data. Here's an overview of the key points and tasks:

1. **Table Creation**:
   - `airport` table holds airport codes, country, and city.
   - `aircraft` table contains aircraft models with unique IDs.
   - `seat` table tracks seats on each aircraft by aircraft ID and seat number.
   - `flight` table records details of each flight, including departure and arrival airports, dates, aircraft, and status.
   - `ticket` table captures ticket details including passenger information, flight ID, seat number, and cost.

2. **Data Insertion**:
   - Airports are added with codes and locations.
   - Aircraft models are inserted.
   - Seats are generated for each aircraft.
   - Flights are scheduled with specific dates, aircraft, and statuses.
   - Tickets are issued for different passengers on various flights.

3. **Queries**:
   - **Query 3**: Identifies passengers who flew from Minsk to London two days ago, seated in `B1`.
   - **Query 4**: Calculates how many seats were unoccupied on flight `MN3002` on `2020-06-14`.
   - **Query 5**: Lists the two longest flights by duration.
   - **Query 6**: Finds the maximum and minimum duration of flights between Minsk and London, and counts how many flights occurred.
   - **Query 7**: Determines the most common passenger names and their proportion of the total passenger count.

4. **Corrections**:
   - The primary keys, foreign keys, and constraints are carefully placed to ensure data integrity.
   - For query 4, it correctly identifies unoccupied seats by comparing seat counts and matching against ticket records.
   - Window functions are used in query 6 to find the maximum and minimum durations.

5. **Incomplete Query**:
   - The last query (Query 8) is incomplete, but it aims to compare ticket counts by passenger name with the maximum tickets bought by any passenger.

Let me know if you need any adjustments or further explanations!
