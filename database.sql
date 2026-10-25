DROP DATABASE IF EXISTS sport_info;
CREATE DATABASE sport_info;
USE sport_info;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    email VARCHAR(100),
    password VARCHAR(255),
    role VARCHAR(20)
);
CREATE TABLE sport_objects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    price DOUBLE
);
CREATE TABLE time (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sport_object_id INT,
    start_time DATETIME,
    end_time DATETIME,
    status_available BOOLEAN,
    FOREIGN KEY (sport_object_id) REFERENCES sport_objects(id)
);
CREATE TABLE booking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    time_id INT,
    status VARCHAR(30),
    count_f1 INT,
    hours_f2 DOUBLE,
    extra_eq_f3 INT,
    `index` DOUBLE,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (time_id) REFERENCES time(id)
);
INSERT INTO users VALUES
(1, 'admin', 'admin@gmail.com', 'admin123', 'ADMIN'),
(2, 'aleksander', 'alek@gmail.com', '4567', 'CLIENT'),
(3, 'olena', 'olena@gmail.com', '2959', 'CLIENT'),
(4, 'andrii', 'andri@gmail.com', '0012', 'CLIENT');

INSERT INTO sport_objects VALUES
(1, 'Тенісний корт', 'Відкритий корт з твердим покриттям', 350.0),
(2, 'Футбольне поле', 'Штучне покриття, нічне освітлення', 600.0),
(3, 'Тренажерний зал', 'Повний комплект професійного обладнання', 500.0);

INSERT INTO time VALUES
(1, 1, '2026-10-09 10:00:00', '2026-10-09 12:00:00', TRUE),
(2, 2, '2026-10-13 14:00:00', '2026-10-13 16:00:00', TRUE),
(3, 3, '2026-10-20 18:00:00', '2026-10-20 20:00:00', FALSE);

INSERT INTO booking VALUES
(1, 2, 1, 'СТВОРЕНО', 4, 2.0, 1,2.55, NOW()),
(2, 3, 2, 'ПІДТВЕРДЖЕНО', 10, 2.0, 2, 5.2, NOW()),
(3, 4, 3, 'АКТИВНЕ', 2, 2.0, 0, 1.5, NOW());
