CREATE TABLE IF NOT EXISTS produto (
                                       id INTEGER PRIMARY KEY,
                                       nome VARCHAR(100) NOT NULL,
                                       descricao VARCHAR(500),
                                       categoria_id INTEGER,
                                       valor NUMERIC(10, 2) NOT NULL,
                                           FOREIGN KEY (categoria_id) REFERENCES categoria (id)
);