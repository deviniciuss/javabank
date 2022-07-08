USE javabank;

CREATE TABLE customer(
    id_customer INTEGER AUTO_INCREMENT,
    id_account  INTEGER,
    name        CHAR(100),
    created     datetime DEFAULT current_timestamp,
    PRIMARY KEY (id_customer),
    FOREIGN KEY (id_account) REFERENCES account(id_account)
);



