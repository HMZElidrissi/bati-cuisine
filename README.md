# Bati-Cuisine

## Titre: Application d'estimation des coûts de construction des cuisines

**Description rapide:** Application d'estimation des coûts de construction des cuisines

## Contexte :

Bati-Cuisine est une application Java destinée aux professionnels de la construction et de la
rénovation de cuisines. L'application calcule le coût total des travaux en tenant compte des matériaux
utilisés et du coût de la main-d'œuvre, cette dernière étant facturée à l'heure.

Le programme inclut des fonctionnalités avancées telles que la gestion des clients, la génération de
devis personnalisés, et une vue d'ensemble sur les aspects financiers et logistiques des projets de
rénovation.

Ce projet vise à offrir aux professionnels un outil puissant et pratique pour estimer avec précision les
coûts et gérer efficacement les projets de rénovation de cuisines.

## Exigences fonctionnelles:

### 1. Gestion des Projets

- Ajouter un client associé au projet.
- Ajouter et gérer des composants (matériaux, main-d'œuvre).
- Associer un devis au projet pour estimer les coûts avant les travaux.
- Un projet est caractérisé par:
    - nomProjet (String): Nom du projet de construction ou de rénovation.
    - margeBeneficiaire (double): Marge bénéficiaire appliquée au coût total.
    - coutTotal (double): Coût total calculé pour le projet.
    - etatProjet (Enum): Statut du projet (En cours, Terminé, Annulé).

### 2. Gestion des Composants

#### Matériaux : Gestion des coûts des matériaux.

Les matériaux sont caractérisés:
- nom (String): Nom du composant.
- coutUnitaire (double): Coût unitaire du composant.
- quantite (double): Quantité de composants utilisée.
- typeComposant (String): Type de composant (Matériel ou Main-d'œuvre).
- tauxTVA (double): Taux de TVA applicable au composant.
- coutTransport (double): Coût du transport du matériau.
- coefficientQualite (double): Coefficient reflétant la qualité du matériau.

#### Main-d'œuvre : Calcul des coûts basés sur le taux horaire, les heures travaillées, et la productivité des ouvriers.

- nom (String): Nom du composant.
- typeComposant (String): Type de composant (Matériel ou Main-d'œuvre).
- tauxTVA (double): Taux de TVA applicable au composant.
- tauxHoraire (double): Taux horaire de la main-d'œuvre.
- heuresTravail (double): Nombre d'heures travaillées.
- productiviteOuvrier (double): Facteur de productivité des ouvriers.

### 3. Gestion des Clients

- Enregistrer les informations de base d'un client
- Différencier les clients professionnels et particuliers, ce qui peut influer sur les remises ou les taxes appliquées.
- Calculer et appliquer des remises spécifiques selon le type de client (ex. : remise pour les clients réguliers ou professionnels).
- On enregistre pour les clients les informations suivantes:
    - nom (String): Nom du client.
    - adresse (String): Adresse du client.
    - telephone (String): Numéro de téléphone du client.
    - estProfessionnel (boolean): Indique si le client est un professionnel.

### 4. Création de Devis

- Générer un devis avant le début des travaux, incluant une estimation des coûts des matériaux, de la main-d'œuvre, des équipements, et des taxes.
- Inclure une date d'émission et une date de validité du devis.
- Indiquer si le devis a été accepté par le client.
- Un devis est caractérisé par:
    - montantEstime (double): Montant estimé du projet sur la base des devis.
    - dateEmission (Date): Date d'émission du devis.
    - dateValidite (Date): Date de validité du devis.
    - accepte (boolean): Indique si le devis a été accepté par le client.

### 5. Calcul des Coûts

- Intégrer les coûts des composants (matériaux, main-d'œuvre) dans le calcul du coût total du projet.
- Appliquer une marge bénéficiaire pour obtenir le coût final du projet.
- Tenir compte des taxes (TVA) et des remises applicables.
- Gérer les ajustements des coûts basés sur la qualité des matériaux ou la productivité de la main-d'œuvre.

### 6. Affichage des Détails et Résultats

- Afficher les détails complets du projet (client, composants, coût total).
- Afficher les informations d'un client, d'un devis.
- Générer un récapitulatif détaillé du coût total incluant la main-d'œuvre, les matériaux, les taxes, et la marge bénéficiaire.

## User stories:

1. En tant que professionnel de la construction: je veux créer un nouveau projet de rénovation ou de construction, afin que je puisse suivre tous les détails du projet et les coûts associés.
2. En tant que professionnel, je veux associer un client à chaque projet, afin que je puisse garder une trace des informations client pour la facturation et les devis.
3. En tant qu'utilisateur, je veux ajouter des matériaux à un projet avec leur coût unitaire, leur quantité et leur transport, afin que je puisse estimer précisément les coûts des matériaux pour ce projet.
4. En tant que professionnel, je veux enregistrer les heures de travail des ouvriers avec leur taux horaire et leur productivité, afin que je puisse calculer le coût total de la main-d'œuvre.
5. En tant que chef de projet, je veux pouvoir gérer plusieurs types de matériaux et travailleurs spécialisés, afin que je puisse calculer précisément les coûts selon le niveau de qualité ou d'expertise requis.
6. En tant que professionnel, je veux générer un devis basé sur les coûts estimés des matériaux, de la main-d'œuvre, et des équipements, afin que je puisse fournir une estimation claire et transparente au client.
7. En tant qu'utilisateur, je veux indiquer une date d'émission et une date de validité du devis, afin que le client sache jusqu'à quand l'offre est valable.
8. En tant que client, je veux pouvoir accepter ou refuser un devis, afin que le projet puisse avancer selon mon accord.
9. En tant que professionnel, je veux enregistrer les informations de mes clients afin que je puisse les contacter facilement pour le suivi des projets et la facturation.
10. En tant qu'utilisateur, je veux différencier les clients professionnels et particuliers, afin que je puisse appliquer des remises ou des conditions spécifiques selon le type de client.
11. En tant que professionnel, je veux calculer le coût total du projet en incluant les matériaux, la main-d'œuvre, les équipements, et les taxes, afin que je puisse avoir une vision claire des coûts avant et après la marge bénéficiaire.
12. En tant que gestionnaire, je veux pouvoir ajuster les coûts selon la qualité des matériaux ou la productivité des ouvriers, afin que je puisse refléter avec précision les variations dans l'estimation finale.
13. En tant que professionnel, je veux voir les taxes appliquées à chaque composant du projet, afin que je puisse inclure la TVA et autres charges dans l'estimation finale.

NB: Aucun traitement d'authentification/autorisation n'est demandé.

## Exemple d'utilisation:

```
=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===

=== Menu Principal ===
1. Créer un nouveau projet
2. Afficher les projets existants
3. Calculer le coût d'un projet
4. Quitter
Choisissez une option : 1

--- Recherche de client ---
Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?
1. Chercher un client existant
2. Ajouter un nouveau client
Choisissez une option : 1

--- Recherche de client existant ---
Entrez le nom du client : Mme Dupont
Client trouvé !
Nom : Mme Dupont
Adresse : 12 Rue des Fleurs, Paris
Numéro de téléphone : 06 12345678
Souhaitez-vous continuer avec ce client ? (y/n) : y

--- Création d'un Nouveau Projet ---
Entrez le nom du projet : Rénovation Cuisine Mme Dupont
Entrez la surface de la cuisine (en m²) : 20

--- Ajout des matériaux ---
Entrez le nom du matériau : Carrelage
Entrez la quantité de ce matériau (en m²) : 20
Entrez le coût unitaire de ce matériau (€/m²) : 30
Entrez le coût de transport de ce matériau (€) : 50
Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : 1.1
Matériau ajouté avec succès !
Voulez-vous ajouter un autre matériau ? (y/n) : y

Entrez le nom du matériau : Peinture
Entrez la quantité de ce matériau (en litres) : 10
Entrez le coût unitaire de ce matériau (€/litre) : 15
Entrez le coût de transport de ce matériau (€) : 20
Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : 1.0
Matériau ajouté avec succès !
Voulez-vous ajouter un autre matériau ? (y/n) : n

--- Ajout de la main-d'œuvre ---
Entrez le type de main-d'œuvre (e.g., Ouvrier de base, Spécialiste) : Ouvrier de base
Entrez le taux horaire de cette main-d'œuvre (€/h) : 20
Entrez le nombre d'heures travaillées : 40
Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : 1.0
Main-d'œuvre ajoutée avec succès !
Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : y

Entrez le type de main-d'œuvre (e.g., Ouvrier de base, Spécialiste) : Ouvrier spécialisé
Entrez le taux horaire de cette main-d'œuvre (€/h) : 35
Entrez le nombre d'heures travaillées : 20
Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : 1.1
Main-d'œuvre ajoutée avec succès !
Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : n

--- Calcul du coût total ---
Souhaitez-vous appliquer une TVA au projet ? (y/n) : y
Entrez le pourcentage de TVA (%) : 20
Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : y
Entrez le pourcentage de marge bénéficiaire (%) : 15
Calcul du coût en cours...

--- Résultat du Calcul ---
Nom du projet : Rénovation Cuisine Mme Dupont
Client : Mme Dupont
Adresse du chantier : 12 Rue des Fleurs, Paris
Surface : 20 m²

--- Détail des Coûts ---
1. Matériaux :
- Carrelage : 710.00 € (quantité : 20 m², coût unitaire : 30 €/m², qualité : 1.1, transport : 50 €)
- Peinture : 170.00 € (quantité : 10 litres, coût unitaire : 15 €/litre, transport : 20 €)
**Coût total des matériaux avant TVA : 880.00 €**
**Coût total des matériaux avec TVA (20%) : 1 056.00 €**

2. Main-d'œuvre :
- Ouvrier de base : 800.00 € (taux horaire : 20 €/h, heures travaillées : 40 h, productivité : 1.0)
- Ouvrier spécialisé : 770.00 € (taux horaire : 35 €/h, heures travaillées : 20 h, productivité : 1.1)
**Coût total de la main-d'œuvre avant TVA : 1 570.00 €**
**Coût total de la main-d'œuvre avec TVA (20%) : 1 884.00 €**

3. Coût total avant marge : 2 940.00 €
4. Marge bénéficiaire (15%) : 441.00 €
**Coût total final du projet : 3 381.00 €**

--- Enregistrement du Devis ---
Entrez la date d'émission du devis (format : jj/mm/aaaa) : 10/09/2024
Entrez la date de validité du devis (format : jj/mm/aaaa) : 10/10/2024
Souhaitez-vous enregistrer le devis ? (y/n) : y
Devis enregistré avec succès !
--- Fin du projet ---
```

## Les exigences techniques:

- Stream
- Collection
- Hashmap
- Optional
- Enum
- Database postgres
- Design pattern: Singleton, Repository Pattern
- Application organisée en couches: Service,...
- Validation des données (Dates, … )
- Java Time API pour la gestion des dates
- Git et JIRA
- Inclure la génération d'un fichier jar et la création d'un README.md détaillé

## Compétences techniques visées :

- C1N1 Installer et configurer son environnement de travail en fonction du projet
- C3N1 Développer des composants métier
- C4N1 Contribuer à la gestion d'un projet informatique
- C6N1 Définir l'architecture logicielle d'une application
- C7N1 Concevoir et mettre en place une base de données
- C81N Développer des composants d'accès aux données

## Compétences transversales visées :

- C1N1 Planifier le travail à effectuer individuellement et en équipe afin d'optimiser le travail nécessaire à l'atteinte de l'objectif visé
- C3N1 Définir le périmètre d'un problème rencontré en adoptant une démarche inductive afin de permettre la recherche de solution
- C4N1 Rechercher de façon méthodique une ou des solutions au problème rencontré afin de retenir une solution adaptée au contexte
- C5N1 Partager la solution adoptée en utilisant les moyens de partage de connaissance ou de documentation disponibles afin de contribuer au développement de la connaissance de l'entreprise.
- C6N1 Présenter un travail réalisé en synthétisant ses résultats, sa démarche et en répondant aux questions afin de le restituer au commanditaire

## Modalités pédagogique :

- Un travail individuel
- Date de lancement: 18/09/2024
- Deadline: 24/09/2024

## Modalités d'évaluation :

- Présentation de 30 minutes :
    - 10 minutes : Démonstration des fonctionnalités de l'application.
    - 10 minutes : Explication du code et de son organisation ainsi que la modélisation.
    - 5 minutes : Mise en situation.
    - 5 minutes : évaluation des savoirs (Q/A)

## Livrables

- Le code source complet sur un dépôt Git
- Le fichier JAR exécutable de l'application
- Les scripts SQL pour la création et l'initialisation de la base de données PostgreSQL
- Le diagramme de classes UML
- Le lien du projet JIRA
- Le fichier README.md

## Critères de performance

- L'application doit être développée avec Java 8 et utiliser efficacement ses fonctionnalités
- Le code doit démontrer une utilisation correcte des streams, des optionals, et des expressions lambda
- La structure en couches de l'application doit être respectée
- La base de données PostgreSQL doit être bien conçue et efficacement utilisée via JDBC
- L'interface console doit être intuitive et robuste
- Le code doit être propre, bien commenté et suivre les conventions de nommage Java
- L'utilisation de Git doit montrer une progression logique du développement
- Le diagramme de classes UML doit être complet et cohérent avec l'implémentation
- Le README.md doit être clair et complet
- L'application doit démontrer une bonne gestion des erreurs et une validation des entrées utilisateur

## Soutenance

### Avant le début de la soutenance:

- Préparer votre IDE (Ouvrir le projet)
- Ouvrir votre diagramme de classe version image ou pdf (bonne résolution)
- Insérer au moins 5 enregistrements dans chacune des tables de la base de données.
- Ouvrir votre repo github dans une fenêtre.
- Préparer et ouvrir des slides de présentation simples et claires

### Déroulement de la soutenance:

- Introduction: Commencez par une brève présentation du projet, son objectif et son utilité.
- Démonstration: préparer une démonstration complète qui permet d'ajouter un projet, client, composants, et qui finit par l'affichage du résultat de l'estimation des coûts de construction et enregistre le devis
- Architecture: Expliquez l'architecture globale du projet, notamment la structure des entités, les relations entre elles, les couches services, dao, et présentation (ui)
- Mise en situation: on vous demandera de coder une méthode ou d'apporter une modification à votre code.
- Evaluation des savoirs: Le formateur évaluateur vous posera des questions pour évaluer votre degré de maîtrise des concepts et technologies abordés.
- 