USE BW;

CREATE TABLE CARDS (
    ID              int(3)          NOT NULL,
    CardName        varchar(15)     NOT NULL,
    Value           int(3)          NOT NULL,
    PointCost       int(5)          NOT NULL,
    Unit            varchar(5)      NOT NULL,
    WebID           varchar(25)     NOT NULL

);