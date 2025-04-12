-- Creating values for software
INSERT INTO software (currentversion, lastversion, createdat, updatedat)
VALUES ('1.0.0', '1.0.0',now(), now());

-- Creating values for playerlanguage
INSERT INTO playerlanguage (iso) VALUES ('FR');
INSERT INTO playerlanguage (iso) VALUES ('EN');

-- Creating values for background
INSERT INTO background (hexcolor, imagepath, isimage) VALUES ('000000ff', '', FALSE);

-- Creating values for menu
INSERT INTO menu (mode) VALUES (1);
INSERT INTO menu (mode) VALUES (2);
INSERT INTO menu (mode) VALUES (3);

-- Creating values for gamelevel
INSERT INTO gamelevel (level) VALUES (1);
INSERT INTO gamelevel (level) VALUES (2);
INSERT INTO gamelevel (level) VALUES (3);

-- Creating values for player
INSERT INTO player (name, playerlanguageplayerlanguageid, backgroundbackgroundid, menumenuid, isselected, createdat, updatedat)
VALUES ('anonyme', (SELECT playerlanguageid FROM playerlanguage WHERE iso = 'FR'), (SELECT backgroundid FROM background WHERE hexcolor = '#000000'), (SELECT menuid FROM menu WHERE mode = 1), TRUE, now(), now());