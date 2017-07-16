INSERT INTO irori_objects(name, type) VALUES ('dagger', 'item');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'dagger'), 'cost', '2 gold pieces');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'dagger'), 'damage', '1D4');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'dagger'), 'threat range', '19 to 20');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'dagger'), 'critical damage', 'times 2');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'dagger'), 'range', '10 feet');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'dagger'), 'weight', '1 pound');
INSERT INTO irori_objects(name, type) VALUES ('short sword', 'item');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'short sword'), 'cost', '10 gold pieces');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'short sword'), 'damage', '1D6');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'short sword'), 'threat range', '19 to 20');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'short sword'), 'critical damage', 'times 2');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'short sword'), 'weight', '2 pounds');