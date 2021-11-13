USE DA2;

CREATE TABLE BOT (
    ID          int(5)          NOT NULL,
    LastIP      varchar(18)     NOT NULL,
    Hypervisor  int(5)          NOT NULL    DEFAULT(0),
    LogVerbose  int(1)          NOT NULL    DEFAULT(1),
    AppVerbose  int(1)          NOT NULL    DEFAULT(0),
    RemoteCLI   int(1)          NOT NULL    DEFAULT(1),
    LocalCLI    int(1)          NOT NULL    DEFAULT(0),
    LastRunTime TIMESTAMP       

);