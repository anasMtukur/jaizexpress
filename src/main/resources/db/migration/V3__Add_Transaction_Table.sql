SET SCHEMA 'jaizex';
CREATE TABLE CARDS
(
    id              UUID PRIMARY KEY DEFAULT (uuid_generate_v1()) NOT NULL,
    user_id         UUID                                          NULL,
    number 		    VARCHAR                                       NOT NULL,
    balance         DOUBLE PRECISION DEFAULT 0                    NOT NULL,
    status          VARCHAR DEFAULT 'ACTIVE'                      NOT NULL
)
INHERITS (auditable_entity);

CREATE TABLE WALLETS
(
  id          		UUID PRIMARY KEY DEFAULT (uuid_generate_v1()) NOT NULL,
  user_id   		UUID REFERENCES END_USERS (id)				  NOT NULL,
  balance  			DOUBLE PRECISION                              NOT NULL,
  status  			VARCHAR(255)                                  NOT NULL
)
INHERITS (auditable_entity);

CREATE TABLE TRANSACTIONS
(
    id                  UUID PRIMARY KEY DEFAULT (uuid_generate_v1()) NOT NULL,
    user_id             UUID REFERENCES END_USERS (id)                NOT NULL,
    transaction_target  VARCHAR                                       NOT NULL,
    card                UUID REFERENCES CARDS (id)                    NULL,
    wallet              UUID REFERENCES WALLETS (id)                  NULL,
    amount  		    DOUBLE PRECISION DEFAULT 0                    NOT NULL,
    transaction_type    VARCHAR                                       NOT NULL,
    is_balanced		    BOOLEAN DEFAULT FALSE 						  NOT NULL,
    status              VARCHAR DEFAULT 'WAITING'                     NOT NULL
)
INHERITS (auditable_entity);

CREATE TABLE PAYMENTS
(
    id                  UUID PRIMARY KEY DEFAULT (uuid_generate_v1()) NOT NULL,
    transaction_id      UUID REFERENCES TRANSACTIONS (id)             NOT NULL,
    amount  		    DOUBLE PRECISION DEFAULT 0                    NOT NULL,
    ref  		        VARCHAR(255)                                  	  NULL,
    status  	        VARCHAR(255)                                  	  NULL,
    method  	        VARCHAR(255)                                  	  NULL,
    message  	        VARCHAR(255)                                  	  NULL,
    url   	            VARCHAR(255)                                  	  NULL,
    payment_date  	    VARCHAR(255)                                  	  NULL
)
INHERITS (auditable_entity);