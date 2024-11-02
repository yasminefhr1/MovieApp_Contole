# Application de Gestion de Films - Jetpack Compose

Ce projet est une application Android de gestion de films, développée avec Jetpack Compose. L'application permet d'afficher une liste de films, de consulter les détails de chaque film, de les noter et de les ajouter aux favoris. Elle inclut une navigation fluide entre les écrans et une interface utilisateur conforme aux standards de Material Design.

## Jetpack Compose 
Jetpack Compose est le framework moderne de Google pour le développement d’interfaces utilisateur en Android, introduit en 2020. Il remplace les layouts XML traditionnels par une approche déclarative qui permet de construire des interfaces utilisateur de manière plus intuitive et efficace. Avant l’introduction de Jetpack Compose, les développeurs Android utilisaient principalement XML pour définir l’interface utilisateur, ce qui impliquait de créer des fichiers XML séparés pour chaque activité ou fragment, puis de manipuler ces éléments via du code Java ou Kotlin. Cette méthode était souvent sujette à des erreurs de référence, nécessitant une gestion minutieuse des états et des interactions utilisateur.


## Fonctionnalités
- **Splash Screen** : Écran de démarrage avec animation.
- **Liste des Films** : Affichage de la liste des films avec options de recherche et de filtrage.
- **Détails du Film** : Consultation des informations détaillées sur chaque film, y compris la bande-annonce.
- **Favoris** : Possibilité de marquer les films comme favoris et de les afficher dans une liste séparée.
- **Notation** : Noter les films via une barre de notation en étoile.
- **Swipe pour Noter et Supprimer** : Glisser pour évaluer ou supprimer un film des favoris.

## Technologies et Bibliothèques
Le projet utilise les technologies et bibliothèques suivantes :

- **Jetpack Compose** : Pour la création de l'interface utilisateur de manière déclarative.
- **Material Design 3** : Pour des composants d'interface utilisateur modernes.
- **Navigation Compose** : Pour gérer la navigation entre les écrans.
- **Coil** : Pour le chargement d'images de manière asynchrone.
- **Accompanist SwipeRefresh** : Pour ajouter une fonctionnalité de rafraîchissement par glissement.
- **WebKit** : Intégration de la navigation web via WebView.

## Installation et Configuration
1. **Prérequis** :
   - Android Studio Bumblebee ou version plus récente.
   - JDK 11 ou version supérieure.
   - Un émulateur Android ou un appareil physique connecté pour tester l'application.

2. **Cloner le dépôt** 

3. **Ouvrir le projet dans Android Studio** :
   - Ouvrez Android Studio et sélectionnez **Open an existing project**.
   - Naviguez jusqu'au dossier du projet et ouvrez-le.

4. **Exécuter l'application** :
   - Sélectionnez votre émulateur ou appareil de test.
   - Cliquez sur **Run** pour lancer l'application.

## Structure du Projet
- **app** : Contient les fichiers principaux du projet.
- **data** : Classe `Item` et fichier `SampleData` pour stocker les informations des films.
- **ui** : Composants d'interface utilisateur, organisés par écran (SplashScreen, MovieListScreen, MovieDetailScreen, etc.).

## Utilisation
### 1. Splash Screen
L'application démarre avec un écran de chargement animé, offrant une première introduction visuelle.

### 2. Liste des Films
- La page d'accueil affiche la liste des films sous forme de cartes.
- Les utilisateurs peuvent faire glisser les cartes pour noter un film ou accéder à ses détails.

### 3. Détails du Film
Chaque film a une page de détails avec :
   - Image et bande-annonce.
   - Informations détaillées : titre, genre, réalisateur, durée, pays, etc.
   - Barre de notation pour évaluer le film.

### 4. Favoris
Les films marqués comme favoris sont stockés dans une liste accessible depuis le menu de navigation latéral.

### 5. Recherche de Films
Un champ de recherche est disponible pour faciliter la navigation dans la liste des films.



## Video demo 


