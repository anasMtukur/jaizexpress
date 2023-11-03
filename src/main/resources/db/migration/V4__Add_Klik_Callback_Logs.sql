SET SCHEMA 'jaizex';
CREATE TABLE CALLBACK_LOGS
(
    id                  UUID PRIMARY KEY DEFAULT (uuid_generate_v1()) NOT NULL,
    amount  		    VARCHAR(255)                                  NOT NULL,
    reference_id        VARCHAR(255)                                  NOT NULL,
    currency  	        VARCHAR(255)                                  NOT NULL,
    trace_id  	        VARCHAR(255)                                  NOT NULL,
    status  	        VARCHAR(255)                                  NOT NULL
)
INHERITS (auditable_entity);