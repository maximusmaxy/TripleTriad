DROP TABLE IF EXISTS Users;

CREATE TABLE Users(
UserId SMALLINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
username VARCHAR(64) NOT NULL,
PASSWORD VARCHAR(64) NOT NULL,
collection VARCHAR(64) NOT NULL);

INSERT INTO Users(username, PASSWORD, collection) VALUES
('maxie', 'password', 'hwsmZWkB'),
('mohommad', 'password', 'I1PJvvwB'),
('master', 'password', '//////8B');