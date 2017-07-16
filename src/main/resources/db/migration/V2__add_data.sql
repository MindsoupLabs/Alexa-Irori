INSERT INTO irori_objects(name, type) VALUES ('dagger', 'item');
INSERT INTO irori_objects(name, type) VALUES ('short sword', 'item');
INSERT INTO irori_objects(name, type) VALUES ('magic missile', 'spell');
INSERT INTO irori_objects(name, type) VALUES ('goblin', 'monster');
INSERT INTO irori_objects(name, type) VALUES ('armor class', 'formula');
INSERT INTO irori_objects(name, type) VALUES ('spell DC', 'formula');

INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'dagger'), 'cost', '2 gold pieces');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'dagger'), 'damage', '1D4');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'dagger'), 'threat range', '19 to 20');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'dagger'), 'critical damage', 'times 2');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'dagger'), 'range', '10 feet');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'dagger'), 'weight', '1 pound');

INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'short sword'), 'cost', '10 gold pieces');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'short sword'), 'damage', '1D6');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'short sword'), 'threat range', '19 to 20');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'short sword'), 'critical damage', 'times 2');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'short sword'), 'weight', '2 pounds');

INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'magic missile'), 'casting time', '1 standard action');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'magic missile'), 'school', 'evocation (force)');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'magic missile'), 'level', 'arcanist 1, bloodrager 1, magus 1, psychic 1, sorcerer/wizard 1');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'magic missile'), 'components', 'V, S');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'magic missile'), 'range', 'medium (100 feet plus 10 feet per level');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'magic missile'), 'duration', 'instantaneous');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'magic missile'), 'saving throw', 'none');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'magic missile'), 'spell resistance', 'yes');

INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'armor class'), 'formula', '10 plus your armor bonus, plus your shield bonus, plus your dexterity modifier, plus your natural armor bonus, plus various other bonuses like dodge, deflection and your size modifier');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'spell DC'), 'formula', '10 plus the spell level plus your casting stat modifier, plus bonuses like Spell Focus if applicable');

INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'spell resistance', 'yes');

INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'hit points', '6 (1d10+1)');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'experience', '135 experience');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'alignment', 'Neutral Evil');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'initiative', '+6');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'armor class', '16');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'touch armor class', '13');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'speed', '30 ft.');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'melee attack', 'short sword +2');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'goblin'), 'ranged attack', 'short bow +4');
