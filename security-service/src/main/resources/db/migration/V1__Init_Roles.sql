INSERT INTO role (role_id, role_name)
SELECT unhex(replace(uuid(),'-','')), 'ADMIN'
WHERE NOT EXISTS (
    SELECT role_name FROM role WHERE role_name = 'ADMIN'
);

INSERT INTO role (role_id, role_name)
SELECT unhex(replace(uuid(),'-','')), 'INCOMPLETE_USER'
WHERE NOT EXISTS (
    SELECT role_name FROM role WHERE role_name = 'INCOMPLETE_USER'
);

INSERT INTO role (role_id, role_name)
SELECT unhex(replace(uuid(),'-','')), 'USER'
WHERE NOT EXISTS (
    SELECT role_name FROM role WHERE role_name = 'USER'
);

INSERT INTO role (role_id, role_name)
SELECT unhex(replace(uuid(),'-','')), 'PREMIUM_USER'
WHERE NOT EXISTS (
    SELECT role_name FROM role WHERE role_name = 'PREMIUM_USER'
);