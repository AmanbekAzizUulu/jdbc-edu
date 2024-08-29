# Code Explanation

Пробрасывание исключений, как в нашем коде, с помощью `RuntimeException`, имеет свои плюсы и минусы. Давайте разберёмся, почему такой подход может считаться оптимизацией и в каких ситуациях он применяется.

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

`create_flight_repository` - создает базу данных полетов

Слудующие скрипты вставлют данные в соответствующие таблицы

 - `insert_into_aircraft_table`
 - `insert_into_airport_table`
 - `insert_into_flight_table`
 - `insert_into_seat_table`
 - `insert_into_ticket_table`
