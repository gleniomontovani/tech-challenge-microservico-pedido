CREATE TABLE IF NOT EXISTS pedido (
                                      id INTEGER PRIMARY KEY,
                                      cliente_id INTEGER,
                                      data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      status_pedido_id INTEGER,
                                      FOREIGN KEY (cliente_id) REFERENCES Cliente (id)
);
