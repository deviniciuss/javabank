CREATE
DATABASE javabank;
USE
javabank;


CREATE TABLE account
(
    id_account  INTEGER AUTO_INCREMENT,
    name        CHAR(100),
    id_customer INTEGER,
    age         INTEGER,
    created     datetime DEFAULT current_timestamp
        PRIMARY KEY (user_id)
        FOREIGN KEY (customer_id)
);

CREATE TABLE customer
(
    id_customer INTEGER AUTO_INCREMENT,
    name        CHAR(100),
    created     datetime DEFAULT current_timestamp
        PRIMARY KEY (id_customer)
);

