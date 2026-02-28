-- Tabela Authority
CREATE TABLE IF NOT EXISTS authority (
                                         name VARCHAR(50) PRIMARY KEY
    );

-- Tabela Role
CREATE TABLE IF NOT EXISTS role (
                                    name VARCHAR(50) PRIMARY KEY
    );

-- Tabela de associação Role <-> Authority
CREATE TABLE IF NOT EXISTS role_authority (
                                              role_name VARCHAR(50) NOT NULL,
    authority_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (role_name, authority_name),
    CONSTRAINT fk_role FOREIGN KEY(role_name) REFERENCES role(name) ON DELETE CASCADE,
    CONSTRAINT fk_authority FOREIGN KEY(authority_name) REFERENCES authority(name) ON DELETE CASCADE
    );

INSERT INTO authority(name) VALUES
                                ('USER_READ'),
                                ('USER_UPDATE'),
                                ('TRANSACTION_CREATE'),
                                ('TRANSACTION_READ'),
                                ('TRANSACTION_REQUEST_CANCEL'),
                                ('CHAT_USER'),
                                ('CHAT_ENTERPRISE'),
                                ('USER_MANAGE'),
                                ('TRANSACTION_CANCEL'),
                                ('TRANSACTION_APPROVE'),
                                ('ADMIN_MANAGE'),
                                ('FINANCE_MANAGE')
    ON CONFLICT DO NOTHING;


INSERT INTO role(name) VALUES
                           ('ROLE_USER'),
                           ('ROLE_ENTERPRISE'),
                           ('ROLE_ADMIN'),
                           ('ROLE_SUPERADMIN')
    ON CONFLICT DO NOTHING;

INSERT INTO role_authority(role_name, authority_name) VALUES
                                                          ('ROLE_USER','USER_READ'),
                                                          ('ROLE_USER','USER_UPDATE'),
                                                          ('ROLE_USER','TRANSACTION_CREATE'),
                                                          ('ROLE_USER','TRANSACTION_READ'),
                                                          ('ROLE_USER','CHAT_USER')
    ON CONFLICT DO NOTHING;

INSERT INTO role_authority(role_name, authority_name) VALUES
                                                          ('ROLE_ENTERPRISE','USER_UPDATE'),
                                                          ('ROLE_ENTERPRISE','TRANSACTION_CREATE'),
                                                          ('ROLE_ENTERPRISE','TRANSACTION_READ'),
                                                          ('ROLE_ENTERPRISE','CHAT_ENTERPRISE'),
                                                          ('ROLE_ENTERPRISE','TRANSACTION_REQUEST_CANCEL')
    ON CONFLICT DO NOTHING;


INSERT INTO role_authority(role_name, authority_name) VALUES
                                                          ('ROLE_SUPERADMIN','USER_READ'),
                                                          ('ROLE_SUPERADMIN','USER_UPDATE'),
                                                          ('ROLE_SUPERADMIN','TRANSACTION_CREATE'),
                                                          ('ROLE_SUPERADMIN','TRANSACTION_READ'),
                                                          ('ROLE_SUPERADMIN','TRANSACTION_REQUEST_CANCEL'),
                                                          ('ROLE_SUPERADMIN','CHAT_USER'),
                                                          ('ROLE_SUPERADMIN','CHAT_ENTERPRISE'),
                                                          ('ROLE_SUPERADMIN','USER_MANAGE'),
                                                          ('ROLE_SUPERADMIN','TRANSACTION_CANCEL'),
                                                          ('ROLE_SUPERADMIN','TRANSACTION_APPROVE'),
                                                          ('ROLE_SUPERADMIN','ADMIN_MANAGE'),
                                                          ('ROLE_SUPERADMIN','FINANCE_MANAGE')
    ON CONFLICT DO NOTHING;