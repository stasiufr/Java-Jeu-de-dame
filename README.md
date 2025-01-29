# Jeu de Dames - Guide d'Installation et d'Utilisation

## Configuration Requise
- Java Development Kit (JDK) 8 ou supérieur
- Un environnement de développement (IDE) ou un terminal

## Installation

1. Créez un dossier pour le projet
2. Dans ce dossier, créez un sous-dossier nommé `jeuDeDames`
3. Placez tous les fichiers sources (.java) dans le dossier `jeuDeDames`

Structure des fichiers attendue :
```
[votre_dossier]
    └── jeuDeDames
        ├── Piece.java
        ├── Pion.java
        ├── Dame.java
        ├── Case.java
        ├── Plateau.java
        ├── Joueur.java
        ├── InterfaceGraphique.java
        └── Jeu.java
```

## Compilation et Lancement

Depuis le dossier racine (parent du dossier jeuDeDames), exécutez les commandes suivantes :

Pour compiler :
```bash
javac -d . jeuDeDames/*.java
```

Pour lancer le jeu :
```bash
java .\jeuDeDames\Jeu.java
```

## Règles du Jeu

- Le jeu se joue à deux joueurs sur un plateau de 10x10 cases
- Les pions blancs commencent la partie
- Les pions se déplacent en diagonale vers l'avant
- Les prises sont obligatoires
- Une prise multiple doit être terminée avant de passer au joueur suivant
- Un pion qui atteint la dernière rangée devient une dame
- Les dames peuvent se déplacer en diagonale dans toutes les directions

## Commandes de Jeu

- Cliquez sur une pièce pour la sélectionner
- Les cases bleues indiquent les mouvements possibles
- Cliquez sur une case bleue pour déplacer la pièce
- Un menu "Nouvelle Partie" permet de recommencer à tout moment

En cas d'erreur de compilation ou d'exécution, vérifiez que :
- Tous les fichiers sont dans le bon dossier
- Le JDK est correctement installé
- Les commandes sont exécutées depuis le bon répertoire
