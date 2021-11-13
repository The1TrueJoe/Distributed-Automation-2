USE BW;

CREATE TABLE FRANCHISE (
    FranchiseNum        int(3)  NOT NULL,
    OwnerID             int(3)  NOT NULL,
    OwnerShare          int(3)  NOT NULL,
    MaintainerID        int(3)  NOT NULL,
    MaintainerShare     int(3)  NOT NULL,
    LicenseID           int(3)  NOT NULL,
    LicenseShare        int(3)  NOT NULL

);