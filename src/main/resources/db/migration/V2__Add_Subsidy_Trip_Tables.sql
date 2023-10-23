SET SCHEMA 'jaizex';
CREATE TABLE SUBSIDIES
(
    id              UUID PRIMARY KEY DEFAULT (uuid_generate_v1()) NOT NULL,
    service_id      UUID                                          NOT NULL,
    title  		    VARCHAR                                       NOT NULL,
    description     TEXT                                          NOT NULL,
    discount        DOUBLE PRECISION DEFAULT 0                    NOT NULL,
    status          VARCHAR                                       NOT NULL
)
INHERITS (auditable_entity);

ALTER TABLE SUBSIDIES
  ADD FOREIGN KEY (service_id) REFERENCES SERVICES (id);

CREATE TABLE TRIPS
(
    id              UUID PRIMARY KEY DEFAULT (uuid_generate_v1()) NOT NULL,
    user_id         UUID                                          NOT NULL,
    card            UUID                                          NULL,
    service_id      UUID                                          NOT NULL,
    origin          UUID                                          NOT NULL,
    destination     UUID                                          NULL,
    amount  		DOUBLE PRECISION DEFAULT 0                    NOT NULL,
    discount        DOUBLE PRECISION DEFAULT 0                    NULL,
    subsidy         UUID                                          NULL,
    qr_url          VARCHAR                                       NOT NULL,
    purchase_mode   VARCHAR                                       NOT NULL,
    payment_method  VARCHAR                                       NULL,
    payment_status  VARCHAR DEFAULT 'WAITING'                     NOT NULL,
    status          VARCHAR DEFAULT 'NEW'                         NOT NULL
)
INHERITS (auditable_entity);

ALTER TABLE TRIPS
  ADD FOREIGN KEY (user_id) REFERENCES END_USERS (id);
ALTER TABLE TRIPS
  ADD FOREIGN KEY (service_id) REFERENCES SERVICES (id);
ALTER TABLE TRIPS
  ADD FOREIGN KEY (subsidy) REFERENCES SUBSIDIES (id);
ALTER TABLE TRIPS
  ADD FOREIGN KEY (origin) REFERENCES STATIONS (id);
ALTER TABLE TRIPS
  ADD FOREIGN KEY (destination) REFERENCES STATIONS (id);