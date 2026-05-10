# SafetyNet Alerts

Projet réalisé dans le cadre d’une formation Java / Spring Boot.

SafetyNet Alerts est une API REST destinée aux services de secours.  
L’application permet de consulter rapidement des informations utiles en situation d’urgence : habitants couverts par une caserne, enfants présents à une adresse, numéros de téléphone à contacter, informations médicales, foyers concernés par une alerte inondation, etc.

L’objectif du projet est de développer le back-end de l’application à partir d’un fichier de données JSON fourni, tout en respectant une architecture MVC, des bonnes pratiques de développement, des logs applicatifs et une couverture de tests.

---

## Sommaire

- [Contexte du projet](#contexte-du-projet)
- [Fonctionnalités](#fonctionnalités)
- [Stack technique](#stack-technique)
- [Architecture](#architecture)
- [Données utilisées](#données-utilisées)
- [Endpoints disponibles](#endpoints-disponibles)
- [Installation et lancement](#installation-et-lancement)
- [Tests et rapports](#tests-et-rapports)
- [Exemples de requêtes](#exemples-de-requêtes)
- [Structure du projet](#structure-du-projet)
- [Auteur](#auteur)

---

## Contexte du projet

SafetyNet est une société qui met en relation les particuliers et les services de secours.

Dans ce projet, l’application **SafetyNet Alerts** expose une API REST permettant aux systèmes de secours d’obtenir des informations précises à partir d’un fichier JSON local.

L’application doit notamment permettre de :

- connaître les personnes couvertes par une caserne de pompiers ;
- identifier les enfants présents à une adresse ;
- récupérer les numéros de téléphone des habitants couverts par une caserne ;
- obtenir les informations médicales d’une personne ou d’un foyer ;
- regrouper les foyers concernés par plusieurs casernes ;
- gérer les données des personnes, casernes et dossiers médicaux.

---

## Fonctionnalités

### Consultation des informations d’urgence

L’API permet de récupérer :

- les habitants couverts par une caserne ;
- le nombre d’adultes et d’enfants sur une zone donnée ;
- les enfants vivant à une adresse ;
- les numéros de téléphone des habitants liés à une caserne ;
- les habitants d’une adresse avec leurs informations médicales ;
- les foyers couverts par une ou plusieurs casernes ;
- les informations personnelles et médicales d’un habitant ;
- les adresses e-mail des habitants d’une ville.

### Gestion des données

L’application expose également des endpoints permettant de :

- ajouter, modifier et supprimer une personne ;
- ajouter, modifier et supprimer une association adresse / caserne ;
- ajouter, modifier et supprimer un dossier médical.

---

## Stack technique

Le projet utilise les technologies suivantes :

- **Java 21**
- **Spring Boot 3.4.1**
- **Maven**
- **Spring Web**
- **Jackson Databind** pour la lecture du fichier JSON
- **JUnit 5** pour les tests unitaires
- **Mockito** pour les mocks dans les tests
- **JaCoCo** pour la couverture de code
- **Maven Surefire** pour l’exécution des tests
- **Lombok**
- **Logging via SLF4J / Spring Boot**

---

## Architecture

Le projet suit une architecture de type MVC, adaptée à une API REST.

```text
Controller  -> reçoit les requêtes HTTP et retourne les réponses JSON
Service     -> contient la logique métier
DTO         -> structure les réponses renvoyées par l’API
Model       -> représente les données manipulées
Util        -> charge les données depuis le fichier JSON
```

Organisation principale :

```text
src/main/java/com/safetynet_alerts/safetynet_alerts
├── controller
├── dto
├── model
├── service
├── util
└── SafetynetAlertsApplication.java
```

### Rôle des principaux packages

| Package | Rôle |
|---|---|
| `controller` | Expose les endpoints REST |
| `service` | Contient la logique métier : filtres, calculs d’âge, regroupements, recherches |
| `dto` | Définit les formats de réponse JSON |
| `model` | Représente les entités `Person`, `Firestation` et `MedicalRecord` |
| `util` | Charge les données du fichier `data.json` au démarrage |

---

## Données utilisées

Les données de départ sont stockées dans le fichier :

```text
src/main/resources/data.json
```

Ce fichier contient trois types d’informations :

- les personnes ;
- les casernes et leurs adresses couvertes ;
- les dossiers médicaux.

Les données sont chargées au démarrage de l’application afin d’être exploitées par les services métier.

---

## Endpoints disponibles

### Endpoints de consultation

| Méthode | Endpoint | Description |
|---|---|---|
| `GET` | `/firestation?stationNumber={stationNumber}` | Retourne les personnes couvertes par une caserne, avec le nombre d’adultes et d’enfants |
| `GET` | `/childAlert?address={address}` | Retourne les enfants vivant à une adresse, ainsi que les autres membres du foyer |
| `GET` | `/phoneAlert?firestation={firestationNumber}` | Retourne les numéros de téléphone des habitants couverts par une caserne |
| `GET` | `/fire?address={address}` | Retourne les habitants d’une adresse, leurs informations médicales et la caserne associée |
| `GET` | `/flood/stations?stations={station1}&stations={station2}` | Retourne les foyers couverts par une ou plusieurs casernes |
| `GET` | `/personInfolastName?lastName={lastName}` | Retourne les informations personnelles et médicales des habitants portant ce nom |
| `GET` | `/communityEmail?city={city}` | Retourne les adresses e-mail des habitants d’une ville |

### Endpoints de gestion des personnes

| Méthode | Endpoint | Description |
|---|---|---|
| `POST` | `/person` | Ajoute une nouvelle personne |
| `PUT` | `/person` | Met à jour une personne existante |
| `DELETE` | `/person?firstName={firstName}&lastName={lastName}` | Supprime une personne |

### Endpoints de gestion des casernes

| Méthode | Endpoint | Description |
|---|---|---|
| `POST` | `/firestation` | Ajoute une association adresse / caserne |
| `PUT` | `/firestation` | Met à jour le numéro de caserne associé à une adresse |
| `DELETE` | `/firestation` | Supprime une association adresse / caserne |

### Endpoints de gestion des dossiers médicaux

| Méthode | Endpoint | Description |
|---|---|---|
| `POST` | `/medicalRecord` | Ajoute un dossier médical |
| `PUT` | `/medicalRecord` | Met à jour un dossier médical |
| `DELETE` | `/medicalRecord?firstName={firstName}&lastName={lastName}` | Supprime un dossier médical |

---

## Installation et lancement

### Prérequis

Avant de lancer le projet, vérifier que les outils suivants sont installés :

- Java 21
- Maven

Le projet contient aussi le wrapper Maven, ce qui permet de lancer les commandes avec `mvnw` sans installation globale de Maven.

---

### Cloner le repository

```bash
git clone https://github.com/Guillaume-S92/SafetyNet.git
cd SafetyNet/safetynet-alerts/safetynet-alerts
```

---

### Lancer l’application

Sous Windows :

```bash
mvnw.cmd spring-boot:run
```

Sous Linux / macOS :

```bash
./mvnw spring-boot:run
```

Avec Maven installé globalement :

```bash
mvn spring-boot:run
```

Une fois l’application lancée, l’API est disponible à l’adresse :

```text
http://localhost:8080
```

---

## Tests et rapports

### Lancer les tests unitaires

```bash
mvn test
```

Ou avec le wrapper Maven :

```bash
./mvnw test
```

Sous Windows :

```bash
mvnw.cmd test
```

---

### Générer les rapports Surefire et JaCoCo

```bash
mvn clean verify
```

Ou avec le wrapper Maven :

```bash
./mvnw clean verify
```

Sous Windows :

```bash
mvnw.cmd clean verify
```

---

### Emplacement des rapports

Après l’exécution des tests, les rapports sont disponibles dans le dossier `target`.

| Rapport | Emplacement |
|---|---|
| Rapport Surefire | `target/surefire-reports` |
| Rapport JaCoCo | `target/site/jacoco/index.html` |

Le rapport JaCoCo permet de vérifier la couverture de code du projet.

---

## Exemples de requêtes

### Récupérer les personnes couvertes par la caserne 1

```bash
curl "http://localhost:8080/firestation?stationNumber=1"
```

---

### Récupérer les enfants vivant à une adresse

```bash
curl "http://localhost:8080/childAlert?address=1509%20Culver%20St"
```

---

### Récupérer les numéros de téléphone liés à une caserne

```bash
curl "http://localhost:8080/phoneAlert?firestation=1"
```

---

### Récupérer les informations d’un foyer à une adresse

```bash
curl "http://localhost:8080/fire?address=1509%20Culver%20St"
```

---

### Récupérer les foyers couverts par plusieurs casernes

```bash
curl "http://localhost:8080/flood/stations?stations=1&stations=2"
```

---

### Récupérer les informations d’une personne par nom de famille

```bash
curl "http://localhost:8080/personInfolastName?lastName=Boyd"
```

---

### Récupérer les e-mails d’une ville

```bash
curl "http://localhost:8080/communityEmail?city=Culver"
```

---

### Ajouter une personne

```bash
curl -X POST "http://localhost:8080/person" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "address": "1 Main Street",
    "city": "Culver",
    "zip": "97451",
    "phone": "123-456-7890",
    "email": "john.doe@email.com"
  }'
```

---

### Mettre à jour une personne

```bash
curl -X PUT "http://localhost:8080/person" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "address": "2 Main Street",
    "city": "Culver",
    "zip": "97451",
    "phone": "987-654-3210",
    "email": "john.doe@email.com"
  }'
```

---

### Supprimer une personne

```bash
curl -X DELETE "http://localhost:8080/person?firstName=John&lastName=Doe"
```

---

## Structure du projet

```text
SafetyNet
└── safetynet-alerts
    └── safetynet-alerts
        ├── src
        │   ├── main
        │   │   ├── java
        │   │   │   └── com/safetynet_alerts/safetynet_alerts
        │   │   │       ├── controller
        │   │   │       ├── dto
        │   │   │       ├── model
        │   │   │       ├── service
        │   │   │       ├── util
        │   │   │       └── SafetynetAlertsApplication.java
        │   │   └── resources
        │   │       ├── application.properties
        │   │       └── data.json
        │   └── test
        │       └── java/com/safetynet_alerts/safetynet_alerts
        ├── pom.xml
        ├── mvnw
        └── mvnw.cmd
```

---

## Qualité du code

Le projet met en place plusieurs éléments attendus pour une API back-end maintenable :

- séparation des responsabilités entre contrôleurs, services, modèles et DTO ;
- endpoints REST dédiés aux besoins métier ;
- chargement centralisé des données JSON ;
- logs sur les requêtes et les réponses ;
- tests unitaires sur les contrôleurs et services ;
- génération de rapports Surefire et JaCoCo avec Maven.

---

## Auteur

Projet réalisé par **Guillaume S.** dans le cadre d’un parcours de formation Java / Spring Boot.
