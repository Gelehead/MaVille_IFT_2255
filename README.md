# MaVille---IFT-2255

## Description
MaVille est une application qui vise à améliorer la communication et la coordination des travaux publics et privés à Montréal. Elle permet une meilleure planification et gestion des travaux tout en facilitant la communication entre les résidents, les intervenants publics et privés.

## Colaborators
 - [Syrine Cherni](https://github.com/syswasy)
 - [Othmane Zeroual](https://github.com/othmane-zer)
 - [Oscar Lavolet](https://github.com/Gelehead)


## Fonctionnalités principales

### Pour les Résidents
- Création et gestion de compte
- Modification des préférences horaires
- Consultation des notifications
- Consultation des travaux en cours et à venir
- Recherche de travaux par quartier
- Consultation des entraves
- Soumission et suivi des requêtes de travail

### Pour les Intervenants
- Création et gestion de compte
- Soumission et gestion des candidatures pour les travaux
- Soumission et gestion des projets
- Modification du statut des projets
- Envoi de notifications aux résidents du quartier

## Installation

### Prérequis
- Java 17 ou supérieur
- Maven 3.8.1 ou supérieur

### Instructions d'installation
1. Cloner le repository
2. Naviguer vers le dossier du projet
3. Exécuter mvn clean install pour installer les dépendances

## Exécution de l'application

### Version ligne de commande (MaVille.jar)
Sur votre terminal : 
java -jar MaVille\MaVille.jar


### Version interface graphique et API REST (MaVilleAPI.jar)
Sur votre terminal : 
java -jar MaVilleAPI\MaVille.jar
L'interface graphique sera accessible à l'adresse: http://localhost:3000

## Données incluses dans l'application

### Résidents préconfiguré
1. Alice Benoit (Verdun)
   - Email: alice@example.com
   - Mot de passe: password123
   - Adresse: 123 Rue Udem, Montréal

2. Julie Tremblay (LaSalle)
   - Email: julie@example.com
   - Mot de passe: password123
   - Adresse: 234 Rue Udem, Montréal

3. Marc Lafleur (LaSalle)
   - Email: marc@example.com
   - Mot de passe: password123
   - Adresse: 345 Rue Udem, Montréal

4. Sophie Roy (Montreal-Nord)
   - Email: sophie@example.com
   - Mot de passe: password123
   - Adresse: 456 Rue Udem, Montréal

5. Chantal Gagnon (Anjou)
   - Email: chantal@example.com
   - Mot de passe: password123
   - Adresse: 234 Rue Udem, Montréal

### Intervenants préconfigurés
1. Carlos Durand (Entrepreneur privé)
   - Email: carlos@example.com
   - Mot de passe: password123
   - ID: 11111111

2. Pierre Gauthier (Entreprise publique)
   - Email: pierre@example.com
   - Mot de passe: password123
   - ID: 22222222

3. Nadia Fortin (Individuel)
   - Email: nadia@example.com
   - Mot de passe: password123
   - ID: 33333333

4. Ismael Caron (Non catégorisé)
   - Email: ismael@example.com
   - Mot de passe: password123
   - ID: 44444444

5. Laura Picard (Entrepreneur privé)
   - Email: laura@example.com
   - Mot de passe: password123
   - ID: 88888855

### Requêtes de travail préconfigurées
1. Réparation trottoir (LaSalle)
   - Soumis par: Alice Benoit
   - Candidatures: Carlos Durand, Pierre Gauthier

2. Aménagement paysager (LaSalle)
   - Soumis par: Julie Tremblay
   - Candidature: Nadia Fortin

3. Rénovation façade (Verdun)
   - Soumis par: Marc Lafleur

4. Nettoyage graffitis (Montreal-Nord)
   - Soumis par: Sophie Roy

5. Égout bouché (Anjou)
   - Soumis par: Chantal Gagnon

### Projets préconfigurés
1. Projet route future (LaSalle)
   - Date: 10-15 décembre 2024
   - Type: Travaux routiers

2. Projet résidentiel (Verdun)
   - Date: juin-juillet 2025
   - Type: Travaux résidentiel

3. Projet construction (Montreal-Nord)
   - Date: février-mars 2025
   - Type: Construction ou rénovation

4. Projet paysager (Anjou)
   - Date: 7-20 décembre 2024
   - Type: Entretien paysager

5. Projet urbain (Dorval)
   - Date: janvier-février 2025
   - Type: Entretien urbain

## Organisation des fichiers du répertoire

- src/main/java: Contient le code source de l'application
  - backend : Contient la logique métier et la gestion de la base de données
  - utils: Contient les utilitaires
  - instances: Contient les classes représentant les entités
  - server: Contient la gestion de l'application avec interface graphique et API REST
- src/resources: Contient les ressources nécessaires
- src/test/java: Contient les tests unitaires
- target: Contient les fichiers compilés
- docs: Contient la documentation JavaDoc
- out/artifacts: Contient les fichiers JAR
- public: Contient le rapport HTML

## Exécution des tests
Pour exécuter les tests unitaires:

mvn test

Pour générer le rapport de couverture de tests:
mvn jacoco:report
Le rapport sera disponible dans:  targer/site/jacoco/index.html

## Documentation
La documentation JavaDoc est disponible dans le dossier docs/. Ouvrez docs/index.html dans un navigateur pour la consulter.
