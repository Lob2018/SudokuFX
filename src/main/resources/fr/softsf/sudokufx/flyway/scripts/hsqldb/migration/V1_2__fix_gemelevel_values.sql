-- Update gamelevel values only if there are exactly 3 records
-- This avoids data corruption if the table state is unexpected
UPDATE
    gamelevel
SET
    LEVEL = 1
WHERE
    levelid =(
        SELECT
            MIN( levelid )
        FROM
            gamelevel
    )
    AND(
        SELECT
            COUNT(*)
        FROM
            gamelevel
    )= 3;

UPDATE
    gamelevel
SET
    LEVEL = 2
WHERE
    levelid =(
        SELECT
            levelid
        FROM
            gamelevel
        ORDER BY
            levelid LIMIT 1 OFFSET 1
    )
    AND(
        SELECT
            COUNT(*)
        FROM
            gamelevel
    )= 3;

UPDATE
    gamelevel
SET
    LEVEL = 3
WHERE
    levelid =(
        SELECT
            MAX( levelid )
        FROM
            gamelevel
    )
    AND(
        SELECT
            COUNT(*)
        FROM
            gamelevel
    )= 3;
