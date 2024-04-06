CREATE TABLE IF NOT EXISTS Cliente (
                                       id INTEGER PRIMARY KEY,
                                       nome VARCHAR(255),
                                       cpf VARCHAR(14)  UNIQUE,
                                       email VARCHAR(255),
                                       senha VARCHAR(255),
                                       status BOOLEAN NOT NULL
);