/* Data added manually via the database manager*/

CREATE TABLE departments
(
    id   INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE sellers
(
    id            INT          NOT NULL AUTO_INCREMENT,
    name          VARCHAR(60)  NOT NULL,
    email         VARCHAR(100) NOT NULL,
    birth_date    DATETIME     NOT NULL,
    base_salary DOUBLE NOT NULL,
    department_id INT          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (department_id) REFERENCES departments (id)
);

INSERT INTO departments (name)
VALUES ('Computers'),
       ('Electronics'),
       ('Fashion'),
       ('Books');

INSERT INTO sellers (name, email, birth_date, base_salary, department_id)
VALUES ('Bob Brown', 'bob@gmail.com', '1998-04-21 00:00:00', 1000, 1),
       ('Maria Green', 'maria@gmail.com', '1979-12-31 00:00:00', 3500, 2),
       ('Alex Grey', 'alex@gmail.com', '1988-01-15 00:00:00', 2200, 1),
       ('Martha Red', 'martha@gmail.com', '1993-11-30 00:00:00', 3000, 4),
       ('Donald Blue', 'donald@gmail.com', '2000-01-09 00:00:00', 4000, 3),
       ('Alex Pink', 'bob@gmail.com', '1997-03-04 00:00:00', 3000, 2);
