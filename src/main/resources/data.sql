INSERT INTO customer (id, document_id, full_name, nickname, email, password) VALUES (1, '052.382.456-85', 'admin', 'admin', 'admin@admin.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi');
INSERT INTO customer (id, document_id, full_name, nickname, email, password) VALUES (2, '052.632.147-12', 'user', 'user', 'enabled@user.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC');

INSERT INTO authority (type) VALUES ('USER');
INSERT INTO authority (type) VALUES ('ADMIN');

INSERT INTO customer_authorities (customer_id, authority_id) VALUES (1, 1);
INSERT INTO customer_authorities (customer_id, authority_id) VALUES (1, 2);
INSERT INTO customer_authorities (customer_id, authority_id) VALUES (2, 1);
