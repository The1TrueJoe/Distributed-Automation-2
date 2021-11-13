USE DA2;

CREATE TABLE HYPERVISOR_TYPE (
    HYP_TYPE    varchar(45) NOT NULL,
    DESCRPT     varchar(45) NOT NULL,
    NOTE        varchar(45) NOT NULL

);

INSERT INTO HYPERVISOR_TYPE VALUES (0, 'Simple Worker', 'Worker server that is always on');
INSERT INTO HYPERVISOR_TYPE VALUES (1, 'Worker', 'Worker server that is sometimes on');
INSERT INTO HYPERVISOR_TYPE VALUES (2, 'Administrative', 'Administrative server that is always on');