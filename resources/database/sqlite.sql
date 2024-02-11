-- #!sqlite

-- #{ economy
-- #{ createTable

CREATE TABLE IF NOT EXISTS economy(
    playerName VARCHAR(50) NOT NULL,
    money INT DEFAULT 0
);
-- #}

-- # { addPlayer
-- # :playerName string
INSERT INTO economy (playerName) VALUES (:playerName);
-- # }

-- # { updatePlayerMoney
-- # :playerName string
-- # :money int
UPDATE economy SET money = :money WHERE playerName = :playerName;
-- # }

-- # { getPlayerMoney
-- # :playerName string
SELECT money FROM economy WHERE playerName = :playerName;
-- # }
-- #}