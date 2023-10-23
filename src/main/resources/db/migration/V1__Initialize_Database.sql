CREATE SCHEMA IF NOT EXISTS jaizex;
SET SCHEMA 'jaizex';
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE TABLE auditable_entity
(
    created_at TIMESTAMP DEFAULT now() NOT NULL,
    updated_at TIMESTAMP DEFAULT now() NOT NULL
);

CREATE TABLE END_USERS
(
    id          UUID PRIMARY KEY DEFAULT (uuid_generate_v1()) 	  NOT NULL,
    username    		VARCHAR(255)                                  NOT NULL,
    password    		VARCHAR(255)                                  NOT NULL,
    first_name    	    VARCHAR(255)                                  NOT NULL,
    last_name    		VARCHAR(255)                                  NOT NULL,
    email       		VARCHAR(255)                                      NULL,
    mobile       		VARCHAR(255)                                      NULL,
    block_code  		VARCHAR(255)                                  	  NULL,
    is_deleted   		BOOLEAN DEFAULT FALSE                         NOT NULL,
    is_blocked   		BOOLEAN DEFAULT FALSE                         NOT NULL,
    reset_token 		TEXT										      NULL
)
INHERITS (auditable_entity);

CREATE TABLE USER_ROLES
(
    id          UUID PRIMARY KEY DEFAULT (uuid_generate_v1()) 	  NOT NULL,
    user_id     UUID                                  			  NOT NULL,
    role_name   VARCHAR(255)                                        NOT NULL
)
INHERITS (auditable_entity);

ALTER TABLE USER_ROLES
  ADD FOREIGN KEY (user_id) REFERENCES END_USERS (id);

CREATE UNIQUE INDEX user_unique_username_constraint
  ON END_USERS (username);
CREATE UNIQUE INDEX user_unique_email_constraint
  ON END_USERS (email);
ALTER TABLE END_USERS
  ADD CONSTRAINT email_lower CHECK (email = lower(email));
ALTER TABLE END_USERS ADD CONSTRAINT email_check_user CHECK (END_USERS.email ~* '^[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+[.][A-Za-z]+$');

INSERT INTO END_USERS (created_at, updated_at, id, username, password, first_name, last_name, email, mobile, block_code, is_deleted, is_blocked, reset_token)
    VALUES ('2018-03-22 06:33:27.829', '2018-03-22 06:33:27.829', '4e6787fc-446f-11e8-842f-0ed5f89f718b', 'admin@jaizex.com', '$2a$10$ynpFFn38QUsNIqBBI6PSju/yurLD4WJXqTa5btEtC4UabOZUfBAPS', 'System', 'Admin', 'admin@jaizex.com', '+2348166617281', NULL, false, false, NULL);
INSERT INTO END_USERS (created_at, updated_at, id, username, password, first_name, last_name, email, mobile, block_code, is_deleted, is_blocked, reset_token)
    VALUES ('2023-10-06 14:35:07.959', '2023-10-06 14:35:07.959', '393135c7-65eb-4185-92dc-49d11592af72', 'user@demo.com', '$2a$10$ynpFFn38QUsNIqBBI6PSju/yurLD4WJXqTa5btEtC4UabOZUfBAPS', 'Demo', 'User', 'user@demo.com', '+2348020967114', NULL, false, false, NULL);

INSERT INTO USER_ROLES (created_at, updated_at, id, user_id, role_name)
    VALUES ('2018-03-22 06:33:27.829', '2018-03-22 06:33:27.829', '98faf434-64f3-11ee-ae9d-47a7287c241b', '4e6787fc-446f-11e8-842f-0ed5f89f718b', 'SYSTEM_ADMIN');
INSERT INTO USER_ROLES (created_at, updated_at, id, user_id, role_name)
    VALUES ('2018-03-22 06:33:27.829', '2018-03-22 06:33:27.829', '204bc49b-8fd1-4b5d-80a4-7b30147f5eeb', '393135c7-65eb-4185-92dc-49d11592af72', 'USER');

CREATE TABLE PROVIDERS
(
    id        UUID PRIMARY KEY DEFAULT (uuid_generate_v1())   NOT NULL,
    title     VARCHAR(255)                                    NOT NULL,
    email     VARCHAR(255)                                    NOT NULL,
    mobile    VARCHAR(255)                                    NOT NULL,
    logo      VARCHAR(255)                                    NOT NULL,
    url       VARCHAR(255)                                    NOT NULL
)
INHERITS (auditable_entity);

CREATE TABLE STAFFS
(
    id            UUID PRIMARY KEY DEFAULT (uuid_generate_v1())   NOT NULL,
    user_id       UUID                                  		    NOT NULL,
    provider_id   UUID                                            NOT NULL
)
INHERITS (auditable_entity);

ALTER TABLE STAFFS
  ADD FOREIGN KEY (user_id) REFERENCES END_USERS (id);

ALTER TABLE STAFFS
  ADD FOREIGN KEY (provider_id) REFERENCES PROVIDERS (id);

CREATE TABLE SERVICES
(
    id              UUID PRIMARY KEY DEFAULT (uuid_generate_v1()) NOT NULL,
    provider_id     UUID                                          NOT NULL,
    logo    	    VARCHAR(255)                                  NOT NULL,
    service_type    VARCHAR(255)                                  NOT NULL,
    title  		    VARCHAR(255)                                  NOT NULL,
    description     TEXT                                          NOT NULL,
    state           VARCHAR(255)                                  NOT NULL,
    status          VARCHAR(255)                                  NOT NULL
)
INHERITS (auditable_entity);

ALTER TABLE SERVICES
  ADD FOREIGN KEY (provider_id) REFERENCES PROVIDERS (id);

CREATE TABLE ZONES
(
    id            UUID PRIMARY KEY DEFAULT (uuid_generate_v1()) NOT NULL,
    service_id    UUID                                          NOT NULL,
    name    	  VARCHAR(255)                                  NOT NULL,
    base_charge   double precision                              NOT NULL
)
INHERITS (auditable_entity);

ALTER TABLE ZONES
  ADD FOREIGN KEY (service_id) REFERENCES SERVICES (id);

CREATE TABLE STATIONS
(
    id              UUID PRIMARY KEY DEFAULT (uuid_generate_v1()) NOT NULL,
    zone_id         UUID                                          NOT NULL,
    pos_index  	    INTEGER                                       NOT NULL,
    name    	    VARCHAR(255)                                  NOT NULL,
    locality        VARCHAR(255)                                  NOT NULL,
    lat             double precision                              NULL,
    lon             double precision                              NULL,
    geo_hash        VARCHAR                                       NULL,
    addon_charge    double precision                              NOT NULL,
    status          VARCHAR(255) DEFAULT 'OPEN'                   NOT NULL
)
INHERITS (auditable_entity);

ALTER TABLE STATIONS
ADD FOREIGN KEY (zone_id) REFERENCES ZONES (id);