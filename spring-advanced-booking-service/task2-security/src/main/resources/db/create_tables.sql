DROP TABLE IF EXISTS PUBLIC.wins CASCADE;
DROP TABLE IF EXISTS PUBLIC.counters CASCADE;
DROP TABLE IF EXISTS PUBLIC.tickets CASCADE;
DROP TABLE IF EXISTS PUBLIC.user CASCADE;
DROP TABLE IF EXISTS PUBLIC.ticket CASCADE;
DROP TABLE IF EXISTS PUBLIC.event CASCADE;

CREATE TABLE PUBLIC.event
(
  id            INT PRIMARY KEY NOT NULL IDENTITY,
  name          VARCHAR(256)    NOT NULL,
  date          DATETIME DEFAULT NULL,
  ticketPrice   FLOAT    DEFAULT NULL,
  rating        INT      DEFAULT NULL,
  auditorium_id INT      DEFAULT NULL
);

CREATE TABLE PUBLIC.ticket (
  id       INT PRIMARY KEY NOT NULL IDENTITY,
  price    FLOAT           NOT NULL,
  seat     INT             NOT NULL,
  event_id INT DEFAULT NULL,
  CONSTRAINT FK_EVENT_ID FOREIGN KEY (event_id) REFERENCES event (id)
);

CREATE TABLE PUBLIC.user (
  id       INT PRIMARY KEY NOT NULL IDENTITY,
  name     VARCHAR(256) NOT NULL,
  email    VARCHAR(256) DEFAULT NULL,
  birthday DATE         DEFAULT NULL,
  password VARCHAR(64)
);

CREATE TABLE PUBLIC.role (
  id       INT PRIMARY KEY NOT NULL IDENTITY,
  name     VARCHAR(256) NOT NULL
);

CREATE TABLE PUBLIC.roles(
  id        INT PRIMARY KEY NOT NULL IDENTITY,
  user_id   INT NOT NULL,
  role_id INT NOT NULL,
  CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id) REFERENCES role (id),
  CONSTRAINT FK_USER_ROLE_ID FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE PUBLIC.tickets (
  id        INT PRIMARY KEY NOT NULL IDENTITY,
  user_id   INT NOT NULL,
  ticket_id INT NOT NULL,
  CONSTRAINT FK_TICKET_ID FOREIGN KEY (ticket_id) REFERENCES ticket (id),
  CONSTRAINT FK_USER_ID FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE PUBLIC.counters (
  id    INT PRIMARY KEY NOT NULL IDENTITY,
  name  VARCHAR(256) NOT NULL,
  counter INT DEFAULT NULL
);

CREATE TABLE PUBLIC.wins (
  id      INT PRIMARY KEY NOT NULL IDENTITY,
  user_id INT NOT NULL,
  date    DATETIME NOT NULL,
  CONSTRAINT FKW_USER_ID FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE PUBLIC.persistent_logins (
  username VARCHAR(64) NOT NULL,
  series VARCHAR(64) NOT NULL,
  token VARCHAR(64) NOT NULL,
  last_used TIMESTAMP NOT NULL,
  PRIMARY KEY (series)
);

INSERT INTO role(id,name) VALUES (0,'REGISTERED_USER');
INSERT INTO role(id,name) VALUES (1,'BOOKING_MANAGER');

INSERT INTO user(id,name, email, birthday, password) VALUES (0,'john','john@gmail.com',date'1980-11-03','$2a$08$l65pz/4gXDQAxgXuVCC.rOzv0MMzlk2JNGFxDwGV2zMsHuAry2Qki');
INSERT INTO user(id,name, email, birthday, password) VALUES (1,'neo','neo@gmail.com',date'1985-12-08','$2a$08$l65pz/4gXDQAxgXuVCC.rOzv0MMzlk2JNGFxDwGV2zMsHuAry2Qki');

INSERT INTO event(id, name, date, ticketPrice, rating, auditorium_id) VALUES (1, 'Terminator-6','2016-11-03 21:00:00',25.0,2,1);
INSERT INTO event(id, name, date, ticketPrice, rating, auditorium_id) VALUES (2, 'Matrix-4','2016-11-03 21:00:00',30.0,2,1);

INSERT INTO ticket(id, price, seat, event_id) VALUES (1, 10, 23, 1);
INSERT INTO ticket(id, price, seat, event_id) VALUES (2, 10, 24, 1);
INSERT INTO ticket(id, price, seat, event_id) VALUES (3, 10, 25, 1);
INSERT INTO ticket(id, price, seat, event_id) VALUES (4, 20, 6, 2);
INSERT INTO ticket(id, price, seat, event_id) VALUES (5, 20, 7, 2);

INSERT INTO roles(user_id, role_id) VALUES (0,0);
INSERT INTO roles(user_id, role_id) VALUES (0,1);
INSERT INTO roles(user_id, role_id) VALUES (1,0);
