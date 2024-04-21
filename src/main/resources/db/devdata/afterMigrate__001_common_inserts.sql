INSERT INTO categoria (id, nome) VALUES (1, 'Lanche' ) ON CONFLICT DO NOTHING;
INSERT INTO categoria (id, nome) VALUES (2, 'Acompanhamento') ON CONFLICT DO NOTHING;
INSERT INTO categoria (id, nome) VALUES (3, 'Bebida') ON CONFLICT DO NOTHING;
INSERT INTO categoria (id, nome) VALUES (4, 'Sobremesa') ON CONFLICT DO NOTHING;

INSERT INTO status_pedido (id, nome) VALUES (1, 'Realizado') ON CONFLICT DO NOTHING;
INSERT INTO status_pedido (id, nome) VALUES (2, 'Pendente') ON CONFLICT DO NOTHING;
INSERT INTO status_pedido (id, nome) VALUES (3, 'Confirmado') ON CONFLICT DO NOTHING;
INSERT INTO status_pedido (id, nome) VALUES (4, 'Em preparação') ON CONFLICT DO NOTHING;
INSERT INTO status_pedido (id, nome) VALUES (5, 'Concluído') ON CONFLICT DO NOTHING;
INSERT INTO status_pedido (id, nome) VALUES (6, 'Cancelado') ON CONFLICT DO NOTHING;
