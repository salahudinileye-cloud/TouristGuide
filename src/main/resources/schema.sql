
CREATE TABLE IF NOT EXISTS city
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS tourist_attraction
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100),
    description TEXT,
    city_id     INT,
    FOREIGN KEY (city_id) REFERENCES city (id)
);