INSERT INTO airports (id, name, code, country) VALUES (1, 'Amsterdam Schiphol Airport', 'AMS', 'Netherlands');
INSERT INTO airports (id, name, code, country) VALUES (2, 'London Heathrow Airport', 'LHR', 'United Kingdom');
INSERT INTO airports (id, name, code, country) VALUES (3, 'Charles de Gaulle Airport', 'CDG', 'France');

INSERT INTO flights (id, code, origin_airport_code, destination_airport_code, departure_date_time, arrival_date_time, passenger_count) 
VALUES (1, 'EVAC-001', 'AMS', 'LHR', DATEADD('HOUR', 2, CURRENT_TIMESTAMP), DATEADD('HOUR', 3, CURRENT_TIMESTAMP), 0);
