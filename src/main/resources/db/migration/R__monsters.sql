INSERT INTO irori_objects(name, type) VALUES ('goblin', 'monster');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'hit points', '6 (1d10+1)');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'experience', '135 experience');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'alignment', 'Neutral Evil');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'initiative', '+6');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'armor class', '16');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'touch armor class', '13');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'speed', '30 ft.');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'melee attack', 'short sword +2');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'ranged attack', 'short bow +4');