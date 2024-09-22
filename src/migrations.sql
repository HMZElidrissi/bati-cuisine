-- Client
CREATE TABLE clients
(
    id                SERIAL PRIMARY KEY,
    nom               VARCHAR(100) NOT NULL,
    adresse           VARCHAR(255) NOT NULL,
    telephone         VARCHAR(20)  NOT NULL,
    est_professionnel BOOLEAN      NOT NULL
);

-- Projet
CREATE TABLE projets
(
    id                 SERIAL PRIMARY KEY,
    nom_projet         VARCHAR(100)   NOT NULL,
    marge_beneficiaire DECIMAL(5, 2)  NOT NULL,
    cout_total         DECIMAL(10, 2) NOT NULL,
    etat_projet        VARCHAR(20)    NOT NULL,
    client_id          INTEGER REFERENCES clients (id)
);

-- Composant
CREATE TABLE composants
(
    id             SERIAL PRIMARY KEY,
    nom            VARCHAR(100)  NOT NULL,
    taux_tva       DECIMAL(5, 2) NOT NULL,
    type_composant VARCHAR(20)   NOT NULL,
    projet_id      INTEGER REFERENCES projets (id)
);

-- Materiel
CREATE TABLE materiels
(
    cout_unitaire       DECIMAL(10, 2) NOT NULL,
    quantite            DECIMAL(10, 2) NOT NULL,
    cout_transport      DECIMAL(10, 2) NOT NULL,
    coefficient_qualite DECIMAL(3, 2)  NOT NULL
) INHERITS (composants);

-- MainDoeuvre
CREATE TABLE mains_doeuvre
(
    taux_horaire         DECIMAL(10, 2) NOT NULL,
    heures_travail       DECIMAL(5, 2)  NOT NULL,
    productivite_ouvrier DECIMAL(3, 2)  NOT NULL
) INHERITS (composants);

-- Devis table
CREATE TABLE devis
(
    id             SERIAL PRIMARY KEY,
    montant_estime DECIMAL(10, 2) NOT NULL,
    date_emission  DATE           NOT NULL,
    date_validite  DATE           NOT NULL,
    accepte        BOOLEAN        NOT NULL,
    projet_id      INTEGER REFERENCES projets (id)
);

-- Insert mock data

-- Clients
INSERT INTO clients (nom, adresse, telephone, est_professionnel)
VALUES ('Fatima Alaoui', '123 Rue Hassan II, Casablanca', '0661234567', false),
       ('Ahmed Benjelloun', '45 Avenue Mohammed V, Rabat', '0662345678', false),
       ('Société Immobilière Atlas', '78 Boulevard Anfa, Casablanca', '0523456789', true),
       ('Karim Tazi', '12 Rue Bab Agnaou, Marrakech', '0667890123', false),
       ('Entreprise de Construction Maghreb', '56 Zone Industrielle, Tanger', '0539876543', true);

-- Projets
INSERT INTO projets (nom_projet, marge_beneficiaire, cout_total, etat_projet, client_id)
VALUES ('Rénovation Cuisine Moderne', 15.00, 85000.00, 'EN_COURS', 1),
       ('Installation Salle de Bain de Luxe', 20.00, 120000.00, 'EN_COURS', 2),
       ('Aménagement Cuisine Restaurant', 18.00, 250000.00, 'EN_COURS', 3),
       ('Rénovation Cuisine Traditionnelle', 12.00, 70000.00, 'EN_COURS', 4),
       ('Installation Cuisines Appartements', 22.00, 500000.00, 'EN_COURS', 5);

-- Matériels
INSERT INTO materiels (nom, taux_tva, type_composant, cout_unitaire, quantite, cout_transport, coefficient_qualite,
                      projet_id)
VALUES ('Carrelage Zellige', 20.00, 'MATERIEL', 250.00, 20, 500.00, 1.2, 1),
       ('Robinet Cuisine Moderne', 20.00, 'MATERIEL', 800.00, 1, 100.00, 1.1, 1),
       ('Plan de Travail en Marbre', 20.00, 'MATERIEL', 3000.00, 1, 800.00, 1.3, 2),
       ('Meuble Cuisine sur Mesure', 20.00, 'MATERIEL', 15000.00, 1, 1000.00, 1.2, 3),
       ('Évier Double en Inox', 20.00, 'MATERIEL', 2500.00, 1, 200.00, 1.1, 4);

-- Main d'œuvre
INSERT INTO mains_doeuvre (nom, taux_tva, type_composant, taux_horaire, heures_travail, productivite_ouvrier, projet_id)
VALUES ('Ouvrier Spécialisé Carrelage', 20.00, 'MAIN_DOEUVRE', 80.00, 40, 1.1, 1),
       ('Plombier Professionnel', 20.00, 'MAIN_DOEUVRE', 100.00, 16, 1.2, 2),
       ('Menuisier Expert', 20.00, 'MAIN_DOEUVRE', 90.00, 50, 1.1, 3),
       ('Électricien Qualifié', 20.00, 'MAIN_DOEUVRE', 85.00, 20, 1.0, 4),
       ('Chef de Chantier', 20.00, 'MAIN_DOEUVRE', 120.00, 60, 1.2, 5);

-- Devis
INSERT INTO devis (montant_estime, date_emission, date_validite, accepte, projet_id)
VALUES (85000.00, '2024-09-15', '2024-10-15', true, 1),
       (120000.00, '2024-09-16', '2024-10-16', false, 2),
       (250000.00, '2024-09-17', '2024-10-17', true, 3),
       (70000.00, '2024-09-18', '2024-10-18', true, 4),
       (500000.00, '2024-09-19', '2024-10-19', false, 5);