--Crée la table contacts
CREATE TABLE contacts
(
    email     NVARCHAR(150) PRIMARY KEY,
    nom       NVARCHAR(50) NOT NULL,
    prenom    NVARCHAR(100) NOT NULL,
    telephone NVARCHAR(12)
);

CREATE TABLE medecins
(
    id         NVARCHAR(11) PRIMARY KEY,
    nom        NVARCHAR(50) NOT NULL,
    prenom     NVARCHAR(100) NOT NULL,
    specialite NVARCHAR(100) NOT NULL
);

CREATE TABLE rendez_vous
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    jour       DATE NOT NULL,
    debut      TIME NOT NULL,
    id_medecin NVARCHAR(11) NOT NULL,
    id_patient NVARCHAR(150) NOT NULL
);

ALTER TABLE rendez_vous
    add constraint FK_RDV_MEDECINS foreign key (id_medecin) references medecins (id);
ALTER TABLE rendez_vous
    add constraint FK_RDV_CONTACTS foreign key (id_patient) references contacts (email);


