INSERT INTO role (id, name, description, version) VALUES (1, 'admin', 'Administrator', 1);
INSERT INTO role (id, name, description, version) VALUES (2, 'user', 'User', 1);

INSERT INTO permissions (id, route, version ) VALUES (1, 'task-list', 1);
INSERT INTO permissions (id, route, version) VALUES (2, 'user-list', 1);

INSERT INTO roles_to_permissions VALUES (1, 1);
INSERT INTO roles_to_permissions VALUES (1, 2);
INSERT INTO roles_to_permissions VALUES (2, 1);

INSERT INTO user (id, name, lastname, login, password, updatedate, version) VALUES (1, 'John', 'Doe', 'j.doe', '$2a$10$dESdxsC6Di1HIIdi5JTGJeYUD9E8JF0weaYocAjzQ4cMn.cQ1KBTe', NOW(), 1);

INSERT INTO user (id, name, lastname, login, password, updatedate, version) VALUES (2, 'Test', 'User', 't.user', '$2a$10$dESdxsC6Di1HIIdi5JTGJeYUD9E8JF0weaYocAjzQ4cMn.cQ1KBTe', NOW(), 1);

INSERT INTO users_to_roles VALUES (1, 1);
INSERT INTO users_to_roles VALUES (2, 2);

INSERT INTO task (id, title, description, isdone, userentity_id, updatedate, version) values (1, 'test title', 'test description', false, 2, NOW(), 1);