CREATE TABLE IF NOT EXISTS pedido_produto (
											  id INTEGER PRIMARY KEY,
                                              pedido_id INTEGER,
                                              produto_id INTEGER,
                                              quantidade INTEGER,
                                              FOREIGN KEY (pedido_id) REFERENCES pedido (id),
                                              FOREIGN KEY (produto_id) REFERENCES produto (id)
);