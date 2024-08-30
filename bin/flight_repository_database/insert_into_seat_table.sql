USE flight_repository;

INSERT INTO
	seat (
			aircraft_id,
            seat_no
		)
SELECT
	a.id,
    s.seat_no
FROM
	aircraft a
CROSS JOIN (
    SELECT 'A1' AS seat_no
    UNION ALL
    SELECT 'A2'
    UNION ALL
    SELECT 'B1'
    UNION ALL
    SELECT 'B2'
    UNION ALL
    SELECT 'C1'
    UNION ALL
    SELECT 'C2'
    UNION ALL
    SELECT 'D1'
    UNION ALL
    SELECT 'D2'
) as s
ORDER BY a.id;
