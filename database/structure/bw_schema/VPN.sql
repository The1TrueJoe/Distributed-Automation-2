USE BW;

CREATE TABLE VPN (
    ID              int(3)      not null,
    IP_Addr         varchar(45) not null    DEFAULT '10.0.0.1',
    VPN_Location    varchar(45) not null    DEFAULT 'no change'

)

INSERT INTO VPN VALUES (0, '10.0.0.1', 'no change');