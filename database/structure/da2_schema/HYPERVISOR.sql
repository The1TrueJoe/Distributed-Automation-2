USE DA2;

CREATE TABLE HYPERVISOR (
    ID              int(3)      not null,
    HYP_TYPE        int(3)      not null    DEFAULT 0,
    IP_Addr         varchar(45) not null    DEFAULT '10.0.0.1',
    RAM_GB          int(4)      not null    DEFAULT '4',
    Username        varchar(45) not null    DEFAULT 'root',
    Pass            varchar(45) not null    DEFAULT 'Passw0rd!'

);