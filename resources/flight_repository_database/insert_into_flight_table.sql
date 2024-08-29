-- use flight_repository;
use hibernate_tutorial_db;

insert into
	flight
		(
			flight_no,
            departure_date,
            departure_airport_code,
            arrival_date,
            arrival_airport_code,
            aircraft_id,
			status
		)
values
	('MN3002', '2020-06-14T14:30', 'MNK', '2020-06-14T18:07', 'LDN', 1, 'ARRIVED'),
	('MN3002', '2020-06-16T09:15', 'LDN', '2020-06-16T13:00', 'MNK', 1, 'ARRIVED'),
	('BC2801', '2020-07-28T23:25', 'MNK', '2020-07-29T02:43', 'LDN', 2, 'ARRIVED'),
	('BC2801', '2020-08-01T11:00', 'LDN', '2020-08-01T14:15', 'MNK', 2, 'DEPARTED'),
	('TR3103', '2020-05-03T13:10', 'MSK', '2020-05-03T18:38', 'BSL', 3, 'ARRIVED'),
	('TR3103', '2020-05-10T07:15', 'BSL', '2020-05-10T012:44', 'MSK', 3, 'CANCELLED'),
	('CV9827', '2020-09-09T18:00', 'MNK', '2020-09-09T19:15', 'MSK', 4, 'SCHEDULED'),
	('CV9827', '2020-09-19T08:55', 'MSK', '2020-09-19T10:05', 'MNK', 4, 'SCHEDULED'),
	('QS8712', '2020-12-18T03:35', 'MNK', '2020-12-18T06:46', 'LDN', 2, 'ARRIVED');
