USE BW;

CREATE TABLE BWACCOUNTS (
    ID                  int(3)          NOT NULL,
    Active              int(1)          NOT NULL,
    FirstName           varchar(45)     NOT NULL,
    LastName            varchar(45)     NOT NULL,
    Birthday            date            NOT NULL,
    Email               varchar(45)     NOT NULL,
    Password            varchar(20)     NOT NULL,
    CurrentPoints       int(10)         NOT NULL,
    HistoricPoints      int(10)         NOT NULL

);