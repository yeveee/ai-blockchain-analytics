# AI-Powered Blockchain Analytics Platform

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
ai-blockchain-analytics/
├─ frontend/ # React app
├─ backend/ # Spring Boot API
├─ ml/ # Microservice IA FastAPI
├─ infra/ # Docker-compose, scripts
├─ docs/ # Documentation

