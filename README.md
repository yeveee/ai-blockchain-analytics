# AI-Powered Blockchain Analytics Platform

![Logo](https://via.placeholder.com/150)

## Table des matières
1. [Objectif](#objectif)  
2. [Stack technique](#stack-technique)  
3. [Fonctionnalités](#fonctionnalités)  
4. [Architecture du projet](#architecture-du-projet)  
5. [Installation et setup](#installation-et-setup)  
6. [Backend](#backend)  
7. [Frontend](#frontend)  
8. [Microservice IA](#microservice-ia)  
9. [Base de données et stockage](#base-de-données-et-stockage)  
10. [Alertes](#alertes)  
11. [Déploiement](#déploiement)  
12. [Tests et CI/CD](#tests-et-cicd)  
13. [Liens et ressources](#liens-et-ressources)

---

## Objectif
Le projet **AI-Powered Blockchain Analytics Platform** permet :  
- Analyse en temps réel des prix crypto et historique  
- Exploration des wallets ERC-20 et tokens  
- Alertes personnalisées sur les changements de prix ou balances  
- Microservice IA pour générer des résumés automatiques  

Le projet est modulable, extensible et conçu pour un **MVP rapide** puis des évolutions futures.

---

## Stack technique
- **Backend** : Java, Spring Boot, Spring Data JPA, PostgreSQL  
- **Frontend** : React, Axios, Recharts  
- **Microservice IA** : Python, FastAPI, Uvicorn  
- **Base de données** : PostgreSQL  
- **Infrastructure / DevOps** : Docker, docker-compose, Render, Vercel  
- **API Blockchain** : CoinGecko, Covalent ou Moralis  
- **Tests** : JUnit, Mockito, React Testing Library  
- **CI/CD** : GitHub Actions  

---

## Fonctionnalités
- Récupération automatique des prix crypto  
- Endpoints REST pour prices, wallets et alertes  
- Interface graphique pour visualisation des données  
- Sélecteur de crypto (BTC, ETH, MATIC)  
- Microservice IA pour résumés et analyses  
- Alertes configurables  
- Historique complet des données avec backup  

---

## Architecture du projet
