INSERT INTO groups(id, "name", description) values(1, 'COMMON_USER', 'common user group');
INSERT INTO groups(id, "name", description) values(2, 'COMMON_ADMIN', 'common admin group');
INSERT INTO groups(id, "name", description) values(3, 'COMMON_CLIENT', 'common client group');

INSERT INTO roles(id, "name", description) values(1, 'ROLE_USER', 'role common user');
INSERT INTO roles(id, "name", description) values(2, 'ROLE_ADMIN', 'role admin user');
INSERT INTO roles(id, "name", description) values(3, 'SCOPE_READ', 'scope permission read');
INSERT INTO roles(id, "name", description) values(4, 'SCOPE_WRITE', 'scope permission write');
INSERT INTO roles(id, "name", description) values(5, 'ROLE_CLIENT', 'role client user');

INSERT INTO group_roles(group_id, role_id) values(1, 1);
INSERT INTO group_roles(group_id, role_id) values(1, 3);

INSERT INTO group_roles(group_id, role_id) values(2, 2);
INSERT INTO group_roles(group_id, role_id) values(2, 3);
INSERT INTO group_roles(group_id, role_id) values(2, 4);

INSERT INTO group_roles(group_id, role_id) values(3, 5);
INSERT INTO group_roles(group_id, role_id) values(3, 3);
INSERT INTO group_roles(group_id, role_id) values(3, 4);
