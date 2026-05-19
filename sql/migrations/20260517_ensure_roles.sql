USE nexora;

-- Ensure roles table has correct entries
-- Role IDs: 1 = ADMIN, 2 = CREATOR, 3 = MEMBER
INSERT IGNORE INTO roles (id, name) VALUES (1, 'ADMIN');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'CREATOR');
INSERT IGNORE INTO roles (id, name) VALUES (3, 'MEMBER');
