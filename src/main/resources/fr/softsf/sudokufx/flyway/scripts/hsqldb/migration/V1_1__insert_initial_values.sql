-- Creating values for playerlanguage
INSERT
    INTO
        playerlanguage(iso)
    VALUES('FR');

INSERT
    INTO
        playerlanguage(iso)
    VALUES('EN');

-- Creating values for background
INSERT
    INTO
        background(
            hexcolor,
            imagepath,
            isimage,
            isopaque
        )
    VALUES(
        '000000ff',
        '',
        FALSE,
        TRUE
    );

-- Creating values for menu
INSERT
    INTO
        menu(mode)
    VALUES(1);

INSERT
    INTO
        menu(mode)
    VALUES(2);

INSERT
    INTO
        menu(mode)
    VALUES(3);

-- Creating values for gamelevel
INSERT
    INTO
        gamelevel(LEVEL)
    VALUES(1);

INSERT
    INTO
        gamelevel(LEVEL)
    VALUES(2);

INSERT
    INTO
        gamelevel(LEVEL)
    VALUES(3);

-- Creating values for player
INSERT
    INTO
        player(
            name,
            playerlanguageplayerlanguageid,
            backgroundbackgroundid,
            menumenuid,
            isselected,
            createdat,
            updatedat
        )
    VALUES(
        '—',
        (
            SELECT
                playerlanguageid
            FROM
                playerlanguage
            WHERE
                iso = 'FR'
        ),
        (
            SELECT
                backgroundid
            FROM
                background
            WHERE
                hexcolor = '000000ff'
        ),
        (
            SELECT
                menuid
            FROM
                menu
            WHERE
                mode = 1
        ),
        TRUE,
        now(),
        now()
    );

-- Creating values for initial grid
INSERT
    INTO
        grid(
            defaultgridvalue,
            gridvalue,
            possibilities
        )
    VALUES(
        '',
        '',
        100
    );

-- Creating values for initial game with initial grid
INSERT
    INTO
        game(
            gridgridid,
            playerplayerid,
            levellevelid,
            isselected,
            createdat,
            updatedat
        )
    VALUES(
        (
            SELECT
                gridid
            FROM
                grid
            ORDER BY
                gridid DESC LIMIT 1
        ),
        (
            SELECT
                playerid
            FROM
                player
            WHERE
                name = '—' LIMIT 1
        ),
        (
            SELECT
                levelid
            FROM
                gamelevel
            WHERE
                LEVEL = 1 LIMIT 1
        ),
        TRUE,
        NOW(),
        NOW()
    );
