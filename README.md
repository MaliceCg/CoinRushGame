# Titre du projet

Bienvenue sur notre projet qui combine une application backend développée en Java avec Quarkus et une application frontend développée en React.js. Dans ce répertoire, vous trouverez tout ce dont vous avez besoin pour exécuter notre projet en local.

## Prérequis

Avant de commencer, assurez-vous d'avoir les éléments suivants installés sur votre machine :

* Java Development Kit (JDK) version 11 ou supérieure
* Node.js version 14 ou supérieure
* npm version 6 ou supérieure
* Gradle version 6 ou supérieure

## Installation

Pour installer les dépendances nécessaires à l'exécution du projet, suivez les étapes suivantes :

1. Clonez ce répertoire sur votre machine locale.
2. Accédez au répertoire du backend en utilisant la commande `cd code-with-quarkus`.
3. Exécutez la commande `./gradlew clean build` pour télécharger les dépendances et compiler le code Java.
4. Accédez au répertoire du frontend en utilisant la commande `cd ../game`.
5. Exécutez la commande `npm install` pour télécharger les dépendances nécessaires à l'exécution de l'application React.

## Lancement du projet

Pour lancer le projet, suivez les étapes suivantes :

1. Dans le répertoire du backend, exécutez la commande `./gradlew quarkusDev` pour démarrer l'application backend en mode développement.
2. Dans le répertoire du frontend, exécutez la commande `npm run start` pour démarrer l'application frontend en mode développement.

Le backend et le frontend communiquent via WebSocket. Vous pouvez accéder à l'application en ouvrant votre navigateur à l'adresse suivante : `http://localhost:3000`.

## Tests

Des tests unitaires ont été écrits pour le backend à l'aide de JUnit, Mockito et AssertJ. Pour exécuter les tests, accédez au répertoire du backend et exécutez la commande `./gradlew test`.

## Qualité logiciel

La qualité du code est vérifiée à l'aide de Sonar. Pour analyser le code avec Sonar, accédez au répertoire du backend et exécutez la commande `./gradlew sonarqube`. Vous pouvez ensuite accéder aux résultats de l'analyse en vous connectant à votre instance Sonar.

