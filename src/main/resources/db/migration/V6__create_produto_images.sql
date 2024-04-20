CREATE TABLE IF NOT EXISTS produto_images (
                                              id INTEGER PRIMARY KEY,
                                              produto_id INTEGER,
                                              path VARCHAR(500) NOT NULL,
                                              FOREIGN KEY (produto_id) REFERENCES produto (id)
);