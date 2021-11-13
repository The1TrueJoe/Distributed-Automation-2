USE BW;

CREATE TABLE HIST_REDEPTIONS (
    RedeptionID             int(3)          NOT NULL,
    DA2ID                   int(3)          NOT NULL,
    Status                  int(1)          NOT NULL,
    RequestTime             timestamp       NOT NULL,
    RequestCompleteTime     timestamp       NOT NULL,
    CardID                  int(3)          NOT NULL,
    PointCost               int(10)         NOT NULL,
    CardValue               int(10)         NOT NULL,
    CurrencyUnit            varchar(6)      NOT NULL,
    HighCardsCount          int(5)          NOT NULL,
    MedCardsCount           int(5)          NOT NULL,
    LowCardsCount           int(5)          NOT NULL

);