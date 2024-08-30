CREATE DATABASE flight_repository;

-- airport (аэропорт)
--         code (уникальный код аэропорта)
--              country (страна)
--                      city (город)

USE flight_repository;

CREATE TABLE airport
(
    code CHAR(3) PRIMARY KEY,
    country VARCHAR(256) NOT NULL,
    city VARCHAR(128) NOT NULL
);

-- aircraft (самолет)
--          id
--  	model (модель самолета - unique)
CREATE TABLE aircraft
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(128) NOT NULL UNIQUE
);

-- seat (место в самолете)
--      aircraft_id (самолет)
--                  seat_no (номер места в самолете)
CREATE TABLE seat
(
    aircraft_id INT,
    seat_no VARCHAR(4) NOT NULL,
    PRIMARY KEY (aircraft_id, seat_no),
    FOREIGN KEY (aircraft_id) REFERENCES aircraft(id)
);

-- flight (рейс)
--        id (номер рейса не уникальный, поэтому нужен id)
--  flight_no (номер рейса)
--  departure_date (дата вылета)
--  departure_airport_code (аэропорт вылета)
--  arrival_date (дата прибытия)
--  arrival_airport_code (аэропорт прибытия)
--  aircraft_id (самолет)
--     status (статус рейса: cancelled, arrived, departed, scheduled)
CREATE TABLE flight
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    flight_no VARCHAR(16) NOT NULL,
    departure_date DATETIME NOT NULL,
    departure_airport_code CHAR(3) NOT NULL,
    arrival_date DATETIME NOT NULL,
    arrival_airport_code CHAR(3) NOT NULL,
    aircraft_id INT NOT NULL,
    status VARCHAR(32) NOT NULL,
    FOREIGN KEY (departure_airport_code) REFERENCES airport(code),
    FOREIGN KEY (arrival_airport_code) REFERENCES airport(code),
    FOREIGN KEY (aircraft_id) REFERENCES aircraft(id)
);

-- ticket (билет на самолет)
--        id
--  	passenger_no (номер паспорта пассажира)
--  	passenger_name (имя и фамилия пассажира)
--  	flight_id (рейс)
--  	seat_no (номер места в самолете – flight_id + seat-no - unique)
--  	cost (стоимость)
CREATE TABLE ticket
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    passenger_no VARCHAR(32) NOT NULL,
    passenger_name VARCHAR(128) NOT NULL,
    flight_id BIGINT NOT NULL,
    seat_no VARCHAR(4) NOT NULL,
    cost DECIMAL(8, 2) NOT NULL,
    UNIQUE (flight_id, seat_no),
    FOREIGN KEY (flight_id) REFERENCES flight(id)
);

-- Примеры запросов:

-- 1. Кто летел позавчера рейсом Минск (MNK) - Лондон (LDN) на месте B1?
SELECT *
FROM ticket
JOIN flight f ON ticket.flight_id = f.id
WHERE seat_no = 'B1'
  AND f.departure_airport_code = 'MNK'
  AND f.arrival_airport_code = 'LDN'
  AND DATE(f.departure_date) = CURDATE() - INTERVAL 2 DAY;

-- 2. Сколько мест осталось незанятыми 2020-06-14 на рейсе MN3002?

SELECT s.seat_no
FROM seat s
WHERE s.aircraft_id = 1
  AND NOT EXISTS (
    SELECT 1
    FROM ticket t
    JOIN flight f ON f.id = t.flight_id
    WHERE f.flight_no = 'MN3002'
      AND DATE(f.departure_date) = '2020-06-14'
      AND s.seat_no = t.seat_no
);

-- 3. Какие 2 перелета были самые длительные за все время?
SELECT f.id,
       f.arrival_date,
       f.departure_date,
       TIMEDIFF(f.arrival_date, f.departure_date) AS duration
FROM flight f
ORDER BY TIMEDIFF(f.arrival_date, f.departure_date) DESC
LIMIT 2;

-- 4. Какая максимальная и минимальная продолжительность перелетов между Минском и Лондоном
-- и сколько было всего таких перелетов?
SELECT
    MAX(TIMEDIFF(f.arrival_date, f.departure_date)) AS max_duration,
    MIN(TIMEDIFF(f.arrival_date, f.departure_date)) AS min_duration,
    COUNT(*) AS total_flights
FROM flight f
JOIN airport a ON a.code = f.arrival_airport_code
JOIN airport d ON d.code = f.departure_airport_code
WHERE a.city = 'Лондон'
  AND d.city = 'Минск';

-- 5. Какие имена встречаются чаще всего и какую долю от числа всех пассажиров они составляют?
SELECT t.passenger_name,
       COUNT(*) AS count,
       ROUND(100.0 * COUNT(*) / (SELECT COUNT(*) FROM ticket), 2) AS percentage
FROM ticket t
GROUP BY t.passenger_name
ORDER BY count DESC;
