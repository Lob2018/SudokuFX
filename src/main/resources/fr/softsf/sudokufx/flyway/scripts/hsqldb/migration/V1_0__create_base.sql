-- Creation of the "menu" table
CREATE
    TABLE
        menu(
            menuid TINYINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            mode TINYINT NOT NULL
        );

-- Creation of the "playerlanguage" table
CREATE
    TABLE
        playerlanguage(
            playerlanguageid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            iso VARCHAR(2) NOT NULL
        );

-- Creation of the "background" table
CREATE
    TABLE
        background(
            backgroundid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            hexcolor VARCHAR(8) NOT NULL,
            imagepath VARCHAR(1024),
            isimage BOOLEAN DEFAULT FALSE NOT NULL,
        );

-- Creation of the "gamelevel" table
CREATE
    TABLE
        gamelevel(
            levelid TINYINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            LEVEL TINYINT NOT NULL
        );

-- Creation of the "grid" table
CREATE
    TABLE
        grid(
            gridid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            defaultgridvalue VARCHAR(81) NOT NULL,
            gridvalue VARCHAR(810) NOT NULL,
            possibilities TINYINT
        );

-- Creation of the "player" table
CREATE
    TABLE
        player(
            playerid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            playerlanguageplayerlanguageid BIGINT FOREIGN KEY REFERENCES playerlanguage(playerlanguageid),
            backgroundbackgroundid BIGINT FOREIGN KEY REFERENCES background(backgroundid),
            menumenuid TINYINT FOREIGN KEY REFERENCES menu(menuid),
            name VARCHAR(256) NOT NULL UNIQUE,
            isselected BOOLEAN DEFAULT FALSE NOT NULL,
            createdat TIMESTAMP NOT NULL,
            updatedat TIMESTAMP NOT NULL
        );

CREATE
    INDEX idx_player_isselected ON
    player(isselected);

-- Creation of the "game" table
CREATE
    TABLE
        game(
            gameid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            gridgridid BIGINT FOREIGN KEY REFERENCES grid(gridid),
            playerplayerid BIGINT FOREIGN KEY REFERENCES player(playerid),
            levellevelid TINYINT FOREIGN KEY REFERENCES gamelevel(levelid),
            isselected BOOLEAN DEFAULT FALSE,
            createdat TIMESTAMP NOT NULL,
            updatedat TIMESTAMP NOT NULL
        );

CREATE
    INDEX idx_game_isselected ON
    game(isselected);
