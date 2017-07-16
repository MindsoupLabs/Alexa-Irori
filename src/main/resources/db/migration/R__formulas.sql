INSERT INTO irori_objects(name, type) VALUES ('armor class', 'formula');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'armor class'), 'formula', '10 plus your armor bonus, plus your shield bonus, plus your dexterity modifier, plus your natural armor bonus, plus various other bonuses like dodge, deflection and your size modifier');
INSERT INTO irori_objects(name, type) VALUES ('spell DC', 'formula');
INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = 'spell DC'), 'formula', '10 plus the spell level plus your casting stat modifier, plus bonuses like Spell Focus if applicable');
