# Medilabo Solutions

Application de prévention du diabète de type 2 pour le compte d'Abernathy Clinic.
L'application permet de gérer les patients, ajouter des notes médicales et évaluer le risque de diabète d'un patient.

## Architecture

Le projet est découpé en microservices :

- **patient** (port 8080) : gestion des patients, base MySQL
- **note** (port 8082) : gestion des notes médicales, base MongoDB
- **assessment** (port 8083) : évaluation du risque de diabète
- **gateway** (port 8081) : point d'entrée unique (Spring Cloud Gateway)
- **front** (port 4200) : interface utilisateur en Angular

## Lancer le projet

Prérequis : Docker Desktop installé.

À la racine du projet :

```bash
docker compose up --build
```

L'application est accessible sur http://localhost:4200

Identifiants par défaut : `admin` / `admin`

Pour arrêter :

```bash
docker compose down
```

Pour réinitialiser les données (vide les bases) :

```bash
docker compose down -v
```

## Pistes pour appliquer le Green Code

Le Green Code regroupe les pratiques qui visent à réduire la consommation de
ressources d'un logiciel (CPU, mémoire, réseau, stockage). Moins de ressources
utilisées, c'est moins d'énergie consommée par les serveurs.

Voici quelques pistes applicables à ce projet :

- **Images Docker légères**
  Utiliser des images de base Alpine et faire un build multi-stage permet de
  réduire la taille des images. Une image plus petite, c'est moins de stockage
  et moins de bande passante au déploiement. (Déjà appliqué dans ce projet.)

- **Compresser les réponses HTTP**
  Activer la compression gzip dans nginx (front) et dans la gateway permet de
  diviser par 3 ou 4 le poids des réponses envoyées au navigateur.

- **Indexer la base MongoDB**
  Ajouter un index sur le champ `patId` de la collection `notes` évite à
  MongoDB de parcourir toute la collection à chaque recherche. Moins de CPU et
  moins de lecture disque.

- **Limiter les niveaux de log en production**
  Passer les logs au niveau `INFO` (au lieu de `DEBUG`) réduit les écritures
  disque et le bruit dans les fichiers de logs.

- **Supprimer le code et les dépendances inutiles**
  Faire le ménage dans les imports, supprimer les méthodes non utilisées et
  retirer les dépendances Maven/npm qui ne servent plus. Un projet plus léger
  compile plus vite et démarre plus vite.
