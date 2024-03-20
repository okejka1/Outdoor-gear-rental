CREATE SEQUENCE ID_ADRES START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 10000000 NO CYCLE;
CREATE SEQUENCE ID_EGZEMPLARZ_SPRZETU START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 10000000 NO CYCLE;
CREATE SEQUENCE ID_KLIENT START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 10000000 NO CYCLE;
CREATE SEQUENCE ID_PRACOWNIK START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 200 NO CYCLE;
CREATE SEQUENCE ID_SPRZET START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 10000000 NO CYCLE;
CREATE SEQUENCE ID_STANOWISKO START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 20 NO CYCLE;
CREATE SEQUENCE ID_WYPOZYCZENIE START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 10000000 NO CYCLE;
CREATE SEQUENCE ID_WYPOZYCZENIE_SPRZETU START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 10000000 NO CYCLE;
CREATE SEQUENCE ID_ZWROT START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 10000000 NO CYCLE;
CREATE TABLE ADRES (
                       ID               int NOT NULL,
                       miasto           varchar(50) NOT NULL,
                       ulica            varchar(50) NOT NULL,
                       numer_domu       varchar(10) NULL,
                       numer_mieszkania varchar(10) NULL,
                       kod_pocztowy     varchar(10) NOT NULL,
                       PRIMARY KEY (ID));
CREATE TABLE EGZEMPLARZ_SPRZETU (
                                    ID       int NOT NULL,
                                    kolor    varchar(20) NOT NULL,
                                    rozmiar  varchar(5) NOT NULL,
                                    status   varchar(20) NOT NULL,
                                    SPRZETID int NULL,
                                    PRIMARY KEY (ID));
CREATE TABLE KATEGORIA (
                           nazwa varchar(40) NOT NULL,
                           PRIMARY KEY (nazwa));
CREATE TABLE KLIENT (
                        ID          int NOT NULL,
                        imie        varchar(40) NOT NULL,
                        nazwisko    varchar(40) NOT NULL,
                        nr_telefonu varchar(15) NOT NULL,
                        email       varchar(50) NOT NULL,
                        nr_dowodu   varchar(40) NOT NULL,
                        ADRESID     int NOT NULL,
                        PRIMARY KEY (ID));
CREATE TABLE PRACOWNIK (
                           ID                int NOT NULL,
                           imie              varchar(40) NOT NULL,
                           nazwisko          varchar(40) NOT NULL,
                           nr_telefonu       varchar(15) NOT NULL,
                           email             varchar(50) NOT NULL,
                           nr_dowodu         varchar(40) NOT NULL,
                           data_zatrudnienia date NOT NULL,
                           login             varchar(50) NOT NULL,
                           haslo             varchar(255) NOT NULL,
                           STANOWISKOID      int NOT NULL,
                           ADRESID           int NOT NULL,
                           PRIMARY KEY (ID));
CREATE TABLE SPRZET (
                        ID                int NOT NULL,
                        nazwa             varchar(60) NOT NULL,
                        cena_wypozyczenia float(20) NOT NULL,
                        promocja          varchar(30) NULL,
                        KATEGORIAID       varchar(40) NULL,
                        PRIMARY KEY (ID));
CREATE TABLE STANOWISKO (
                            ID    int NOT NULL,
                            nazwa varchar(50) NOT NULL,
                            PRIMARY KEY (ID));
CREATE TABLE WYPOZYCZENIE (
                              ID                int NOT NULL,
                              data_oddania      date NOT NULL,
                              calkowita_cena    float(20) NOT NULL,
                              data_wypozyczenia date NOT NULL,
                              PRACOWNIKID       int NOT NULL,
                              KLIENTID          int NOT NULL,
                              PRIMARY KEY (ID));
CREATE TABLE WYPOZYCZENIE_SPRZETU (
                                      ID                   int NOT NULL,
                                      WYPOZYCZENIEID       int NOT NULL,
                                      EGZEMPLARZ_SPRZETUID int NOT NULL,
                                      PRIMARY KEY (ID));
CREATE TABLE ZWROT (
                       ID             int NOT NULL,
                       data_zwrotu    date NOT NULL,
                       WYPOZYCZENIEID int NOT NULL,
                       PRIMARY KEY (ID));
CREATE UNIQUE INDEX KLIENT
    ON KLIENT (ID, imie, nazwisko);
CREATE UNIQUE INDEX PRACOWNIK
    ON PRACOWNIK (ID, imie, nazwisko);
ALTER TABLE KLIENT ADD CONSTRAINT FKKLIENT603131 FOREIGN KEY (ADRESID) REFERENCES ADRES (ID);
ALTER TABLE PRACOWNIK ADD CONSTRAINT FKPRACOWNIK906182 FOREIGN KEY (STANOWISKOID) REFERENCES STANOWISKO (ID);
ALTER TABLE WYPOZYCZENIE ADD CONSTRAINT FKWYPOZYCZEN90067 FOREIGN KEY (PRACOWNIKID) REFERENCES PRACOWNIK (ID);
ALTER TABLE ZWROT ADD CONSTRAINT FKZWROT760275 FOREIGN KEY (WYPOZYCZENIEID) REFERENCES WYPOZYCZENIE (ID);
ALTER TABLE WYPOZYCZENIE ADD CONSTRAINT FKWYPOZYCZEN449524 FOREIGN KEY (KLIENTID) REFERENCES KLIENT (ID);
ALTER TABLE WYPOZYCZENIE_SPRZETU ADD CONSTRAINT FKWYPOZYCZEN636505 FOREIGN KEY (WYPOZYCZENIEID) REFERENCES WYPOZYCZENIE (ID);
ALTER TABLE WYPOZYCZENIE_SPRZETU ADD CONSTRAINT FKWYPOZYCZEN150407 FOREIGN KEY (EGZEMPLARZ_SPRZETUID) REFERENCES EGZEMPLARZ_SPRZETU (ID);
ALTER TABLE EGZEMPLARZ_SPRZETU ADD CONSTRAINT FKEGZEMPLARZ317349 FOREIGN KEY (SPRZETID) REFERENCES SPRZET (ID);
ALTER TABLE SPRZET ADD CONSTRAINT FKSPRZET442057 FOREIGN KEY (KATEGORIAID) REFERENCES KATEGORIA (nazwa);
ALTER TABLE PRACOWNIK ADD CONSTRAINT FKPRACOWNIK20834 FOREIGN KEY (ADRESID) REFERENCES ADRES (ID);
CREATE VIEW [dbo].[egzemplarz_view]
AS
SELECT dbo.EGZEMPLARZ_SPRZETU.ID, dbo.SPRZET.nazwa AS nazwa_sprzetu, dbo.KATEGORIA.nazwa AS kategoria, dbo.SPRZET.cena_wypozyczenia AS cena, dbo.SPRZET.promocja, dbo.EGZEMPLARZ_SPRZETU.kolor,
       dbo.EGZEMPLARZ_SPRZETU.rozmiar, dbo.EGZEMPLARZ_SPRZETU.status
FROM     dbo.KATEGORIA INNER JOIN
         dbo.SPRZET ON dbo.KATEGORIA.nazwa = dbo.SPRZET.KATEGORIAID INNER JOIN
         dbo.EGZEMPLARZ_SPRZETU ON dbo.SPRZET.ID = dbo.EGZEMPLARZ_SPRZETU.SPRZETID;
CREATE VIEW [dbo].[klient_view]
AS
SELECT ID, imie, nazwisko, nr_telefonu
FROM     dbo.KLIENT;
CREATE VIEW [dbo].[klient_view2]
AS
SELECT dbo.KLIENT.ID, dbo.KLIENT.imie, dbo.KLIENT.nazwisko, dbo.KLIENT.nr_telefonu, dbo.KLIENT.email, dbo.KLIENT.nr_dowodu, dbo.ADRES.miasto, dbo.ADRES.ulica, dbo.ADRES.numer_domu, dbo.ADRES.numer_mieszkania,
       dbo.ADRES.kod_pocztowy
FROM     dbo.ADRES INNER JOIN
         dbo.KLIENT ON dbo.ADRES.ID = dbo.KLIENT.ADRESID;
CREATE VIEW [dbo].[pracownik_view]
AS
SELECT dbo.STANOWISKO.nazwa AS stanowisko, dbo.PRACOWNIK.ID, dbo.PRACOWNIK.imie, dbo.PRACOWNIK.nazwisko, dbo.PRACOWNIK.nr_telefonu, dbo.PRACOWNIK.email, dbo.PRACOWNIK.nr_dowodu,
       dbo.PRACOWNIK.data_zatrudnienia
FROM     dbo.STANOWISKO INNER JOIN
         dbo.PRACOWNIK ON dbo.STANOWISKO.ID = dbo.PRACOWNIK.STANOWISKOID;
CREATE VIEW [dbo].[sprzet_view]
AS
SELECT dbo.KATEGORIA.nazwa AS kategoria, dbo.SPRZET.nazwa, dbo.SPRZET.cena_wypozyczenia, dbo.SPRZET.promocja, dbo.SPRZET.ID
FROM     dbo.KATEGORIA INNER JOIN
         dbo.SPRZET ON dbo.KATEGORIA.nazwa = dbo.SPRZET.KATEGORIAID;
CREATE VIEW [dbo].[sprzet_view2]
AS
SELECT dbo.KATEGORIA.nazwa AS kategoria, dbo.SPRZET.nazwa, dbo.SPRZET.cena_wypozyczenia, dbo.SPRZET.promocja, dbo.SPRZET.ID
FROM     dbo.KATEGORIA INNER JOIN
         dbo.SPRZET ON dbo.KATEGORIA.nazwa = dbo.SPRZET.KATEGORIAID;
CREATE VIEW [dbo].[wpozyczenie_zwrot_view]
AS
SELECT dbo.ZWROT.ID, dbo.ZWROT.WYPOZYCZENIEID AS wypID, dbo.ZWROT.data_zwrotu, dbo.WYPOZYCZENIE.PRACOWNIKID AS empID, dbo.KLIENT.imie, dbo.KLIENT.nazwisko
FROM     dbo.KLIENT INNER JOIN
         dbo.WYPOZYCZENIE ON dbo.KLIENT.ID = dbo.WYPOZYCZENIE.KLIENTID INNER JOIN
         dbo.ZWROT ON dbo.WYPOZYCZENIE.ID = dbo.ZWROT.WYPOZYCZENIEID;
CREATE VIEW [dbo].[wypozyczenie_view]
AS
SELECT dbo.WYPOZYCZENIE.ID, dbo.WYPOZYCZENIE.PRACOWNIKID, dbo.KLIENT.imie, dbo.KLIENT.nazwisko, dbo.KATEGORIA.nazwa AS kategoria, dbo.SPRZET.nazwa, dbo.SPRZET.cena_wypozyczenia,
       dbo.EGZEMPLARZ_SPRZETU.ID AS egzID, dbo.EGZEMPLARZ_SPRZETU.kolor, dbo.EGZEMPLARZ_SPRZETU.rozmiar, dbo.WYPOZYCZENIE.data_wypozyczenia, dbo.WYPOZYCZENIE.data_oddania
FROM     dbo.KATEGORIA INNER JOIN
         dbo.SPRZET ON dbo.KATEGORIA.nazwa = dbo.SPRZET.KATEGORIAID INNER JOIN
         dbo.EGZEMPLARZ_SPRZETU ON dbo.SPRZET.ID = dbo.EGZEMPLARZ_SPRZETU.SPRZETID INNER JOIN
         dbo.WYPOZYCZENIE_SPRZETU ON dbo.EGZEMPLARZ_SPRZETU.ID = dbo.WYPOZYCZENIE_SPRZETU.EGZEMPLARZ_SPRZETUID INNER JOIN
         dbo.WYPOZYCZENIE ON dbo.WYPOZYCZENIE_SPRZETU.WYPOZYCZENIEID = dbo.WYPOZYCZENIE.ID INNER JOIN
         dbo.KLIENT ON dbo.WYPOZYCZENIE.KLIENTID = dbo.KLIENT.ID;
CREATE VIEW [dbo].[wypozyczenie_view2]
AS
SELECT dbo.WYPOZYCZENIE.ID, dbo.WYPOZYCZENIE.data_wypozyczenia, dbo.WYPOZYCZENIE.data_oddania, dbo.KLIENT.imie, dbo.KLIENT.nazwisko, dbo.KLIENT.nr_telefonu, dbo.PRACOWNIK.ID AS empId
FROM     dbo.KLIENT INNER JOIN
         dbo.WYPOZYCZENIE ON dbo.KLIENT.ID = dbo.WYPOZYCZENIE.KLIENTID INNER JOIN
         dbo.PRACOWNIK ON dbo.WYPOZYCZENIE.PRACOWNIKID = dbo.PRACOWNIK.ID;
CREATE VIEW [dbo].[wypozyczenie_view3] AS
SELECT
    PRACOWNIK.imie AS imie_pracownika,
    PRACOWNIK.nazwisko AS nazwisko_pracownika,
    WYPOZYCZENIE.*,
    KLIENT.imie AS imie_klienta,
    KLIENT.nazwisko AS nazwisko_klienta
FROM
    KLIENT INNER JOIN
    WYPOZYCZENIE ON KLIENT.ID = WYPOZYCZENIE.KLIENTID INNER JOIN
    PRACOWNIK ON WYPOZYCZENIE.PRACOWNIKID = PRACOWNIK.ID;
CREATE VIEW [dbo].[wypozyczenie_zwrot_view]
AS
SELECT dbo.ZWROT.ID, dbo.ZWROT.WYPOZYCZENIEID AS wypID, dbo.ZWROT.data_zwrotu, dbo.WYPOZYCZENIE.PRACOWNIKID AS empID, dbo.KLIENT.imie, dbo.KLIENT.nazwisko
FROM     dbo.KLIENT INNER JOIN
         dbo.WYPOZYCZENIE ON dbo.KLIENT.ID = dbo.WYPOZYCZENIE.KLIENTID INNER JOIN
         dbo.ZWROT ON dbo.WYPOZYCZENIE.ID = dbo.ZWROT.WYPOZYCZENIEID;
CREATE PROCEDURE [dbo].[ManageAddress]
    @AddressID INT OUTPUT, -- Use OUTPUT parameter for returning the generated ID
    @City VARCHAR(50),
    @Street VARCHAR(50),
    @HouseNumber VARCHAR(10),
    @FlatNumber VARCHAR(10),
    @PostalCode VARCHAR(10),
    @Action VARCHAR(10) -- 'Insert', 'Update', 'Delete'
AS
BEGIN
    IF @Action = 'Insert'
BEGIN
SELECT @AddressID = NEXT VALUE FOR ID_ADRES;

INSERT INTO ADRES (ID, miasto, ulica, numer_domu, numer_mieszkania, kod_pocztowy)
VALUES (@AddressID, @City, @Street, @HouseNumber, @FlatNumber, @PostalCode);

-- Debugging line to print the generated AddressID
PRINT 'Generated AddressID: ' + CAST(@AddressID AS NVARCHAR(255));
END
ELSE IF @Action = 'Update'
BEGIN
UPDATE ADRES
SET miasto = @City, ulica = @Street, numer_domu = @HouseNumber,
    numer_mieszkania = @FlatNumber, kod_pocztowy = @PostalCode
WHERE ID = @AddressID;
END
ELSE IF @Action = 'Delete'
BEGIN
DELETE FROM ADRES WHERE ID = @AddressID;
END
END;
CREATE PROCEDURE [dbo].[ManageClient]
    @ClientID INT,
    @FirstName VARCHAR(40),
    @LastName VARCHAR(40),
    @PhoneNumber VARCHAR(15),
    @Email VARCHAR(50),
    @IDNumber VARCHAR(40),
    @AddressID INT,
    @Action VARCHAR(10) -- 'Insert', 'Update', 'Delete'
AS
BEGIN
    IF @Action = 'Insert'
BEGIN
SELECT @ClientID = NEXT VALUE FOR ID_KLIENT;
INSERT INTO KLIENT (ID, imie, nazwisko, nr_telefonu, email, nr_dowodu, ADRESID)
VALUES (@ClientID, @FirstName, @LastName, @PhoneNumber, @Email, @IDNumber, @AddressID);
END
ELSE IF @Action = 'Update'
BEGIN
UPDATE KLIENT
SET imie = @FirstName, nazwisko = @LastName, nr_telefonu = @PhoneNumber, email = @Email,
    nr_dowodu = @IDNumber, ADRESID = @AddressID
WHERE ID = @ClientID;
END
ELSE IF @Action = 'Delete'
BEGIN
DELETE FROM KLIENT WHERE ID = @ClientID;
END
END;
CREATE PROCEDURE [dbo].[ManageEmployees]
    @EmployeeID INT OUTPUT,
    @FirstName VARCHAR(40),
    @LastName VARCHAR(40),
    @PhoneNumber VARCHAR(15),
    @Email VARCHAR(50),
    @IDNumber VARCHAR(40),
    @HireDate DATE,
    @Username VARCHAR(50),
    @Password VARCHAR(255),
    @PositionID INT,
    @AddressID INT,
    @Action VARCHAR(10) -- 'Insert', 'Update', 'Delete'
AS
BEGIN

    IF @Action = 'Insert'
BEGIN
SELECT @EmployeeID = NEXT VALUE FOR ID_PRACOWNIK;
INSERT INTO PRACOWNIK (ID, imie, nazwisko, nr_telefonu, email, nr_dowodu, data_zatrudnienia, login, haslo, STANOWISKOID, ADRESID)
VALUES (@EmployeeID, @FirstName, @LastName, @PhoneNumber, @Email, @IDNumber, @HireDate, @Username, @Password, @PositionID, @AddressID);
END
ELSE IF @Action = 'Update'
BEGIN
UPDATE PRACOWNIK
SET imie = @FirstName, nazwisko = @LastName, nr_telefonu = @PhoneNumber, email = @Email,
    nr_dowodu = @IDNumber, data_zatrudnienia = @HireDate, login = @Username, haslo = @Password,
    STANOWISKOID = @PositionID, ADRESID = @AddressID
WHERE ID = @EmployeeID;
END
ELSE IF @Action = 'Delete'
BEGIN
DELETE FROM PRACOWNIK WHERE ID = @EmployeeID;
END
END;
CREATE PROCEDURE [dbo].[ManageEntityRentals]
    @ID INT OUTPUT,
    @RentalID INT,
    @EquipmentEntityID INT,

    @Action VARCHAR(15) -- 'Create', 'Complete', 'Return', 'Search'
AS
BEGIN
    IF @Action = 'Insert'
BEGIN
SELECT @ID = NEXT VALUE FOR ID_WYPOZYCZENIE_SPRZETU;
INSERT INTO WYPOZYCZENIE_SPRZETU(ID,WYPOZYCZENIEID,EGZEMPLARZ_SPRZETUID)
VALUES (@ID,@RentalID,@EquipmentEntityID);
END
ELSE IF @Action = 'Delete'
BEGIN
DELETE FROM WYPOZYCZENIE_SPRZETU WHERE ID = @ID;
END
END;
CREATE PROCEDURE [dbo].[ManageEquipment]
    @EquipmentID INT OUTPUT,
    @EquipmentName VARCHAR(60),
    @RentalPrice FLOAT(20),
    @Promotion VARCHAR(30),
    @CategoryName VARCHAR(40),
    @Action VARCHAR(10) -- 'Insert', 'Update', 'Delete'
AS
BEGIN
    IF @Action = 'Insert'
BEGIN
SELECT @EquipmentID = NEXT VALUE FOR ID_SPRZET;
INSERT INTO SPRZET (ID, nazwa, cena_wypozyczenia, promocja, KATEGORIAID)
VALUES (@EquipmentID, @EquipmentName, @RentalPrice, @Promotion, @CategoryName);
END
ELSE IF @Action = 'Update'
BEGIN
UPDATE SPRZET
SET nazwa = @EquipmentName, cena_wypozyczenia = @RentalPrice, promocja = @Promotion, KATEGORIAID = @CategoryName
WHERE ID = @EquipmentID;
END
ELSE IF @Action = 'Delete'
BEGIN
DELETE FROM SPRZET WHERE ID = @EquipmentID;
END
END;
CREATE PROCEDURE [dbo].[ManageEquipmentEntity]
    @InstanceID INT OUTPUT,
    @Color VARCHAR(20),
    @Size VARCHAR(5),
    @Status VARCHAR(20),
    @EquipmentID INT,
    @Action VARCHAR(10) -- 'Insert', 'Update', 'Delete'
AS
BEGIN
    IF @Action = 'Insert'
BEGIN
SELECT @InstanceID = NEXT VALUE FOR ID_EGZEMPLARZ_SPRZETU;
INSERT INTO EGZEMPLARZ_SPRZETU (ID, kolor, rozmiar, status, SPRZETID)
VALUES (@InstanceID, @Color, @Size, @Status, @EquipmentID);
END
ELSE IF @Action = 'Update'
BEGIN
UPDATE EGZEMPLARZ_SPRZETU
SET kolor = @Color, rozmiar = @Size, status = @Status, SPRZETID = @EquipmentID
WHERE ID = @InstanceID;
END
ELSE IF @Action = 'Delete'
BEGIN
DELETE FROM EGZEMPLARZ_SPRZETU WHERE ID = @InstanceID;
END
END;
CREATE PROCEDURE [dbo].[ManageRentals]
    @RentalID INT OUTPUT,
    @ReturnDate DATE,
    @TotalPrice FLOAT,
    @RentalDate DATE,
    @EmployeeID INT,
    @ClientID INT,
    @Action VARCHAR(15) -- 'Create', 'Complete', 'Return', 'Search'
AS
BEGIN
    IF @Action = 'Insert'
BEGIN
SELECT @RentalID = NEXT VALUE FOR ID_WYPOZYCZENIE;
INSERT INTO WYPOZYCZENIE(ID,data_oddania,calkowita_cena,data_wypozyczenia,PRACOWNIKID,KLIENTID)
VALUES (@RentalID, @ReturnDate, @TotalPrice, @RentalDate, @EmployeeID, @ClientID);
END
ELSE IF @Action = 'Delete'
BEGIN
DELETE FROM WYPOZYCZENIE WHERE ID = @RentalID;
END
END;
CREATE PROCEDURE [dbo].[ManageReturns]
    @ReturnID INT OUTPUT,
    @ReturnDate DATE,
	@RentalID INT,
	@Action VARCHAR(10)
AS
BEGIN
    IF @Action = 'Insert'
BEGIN
SELECT @ReturnID = NEXT VALUE FOR ID_ZWROT;
INSERT INTO ZWROT(ID,data_zwrotu,WYPOZYCZENIEID)
VALUES (@RentalID, @ReturnDate, @RentalID);
END
ELSE IF @Action = 'Delete'
BEGIN
DELETE FROM ZWROT WHERE ID = @ReturnID;
END
END;
