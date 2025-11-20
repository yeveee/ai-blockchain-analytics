# 🚀 Guide Maître de Déploiement Production

**Guide complet**: De Git à Railway/Vercel en production

---

## 📋 Table des Matières

1. [Préparation Initiale](#1-préparation-initiale)
2. [Développement Local](#2-développement-local)
3. [Git Workflow Complet](#3-git-workflow-complet)
4. [Déploiement Backend (Railway)](#4-déploiement-backend-railway)
5. [Déploiement Frontend (Vercel)](#5-déploiement-frontend-vercel)
6. [Configuration des Variables](#6-configuration-des-variables)
7. [Tests et Validation](#7-tests-et-validation)
8. [Troubleshooting](#8-troubleshooting)

---

## 1. Préparation Initiale

### Comptes Nécessaires
- ✅ GitHub (pour le code)
- ✅ Docker Hub (pour les images Docker)
- ✅ Railway (pour le backend)
- ✅ Vercel (pour le frontend)

### Installation CLIs
```bash
# Docker Desktop (interface graphique)
# Télécharger: https://www.docker.com/products/docker-desktop

# Railway CLI
npm install -g @railway/cli

# Vercel CLI
npm install -g vercel
```

---

## 2. Développement Local

### Démarrer l'Application

```bash
# Terminal 1: Base de données
cd infra
docker compose up -d db

# Terminal 2: Backend
cd backend/blockchain-analytics
./mvnw spring-boot:run

# Terminal 3: Frontend
cd frontend
npm start
```

### URLs Locales
- Frontend: http://localhost:3000
- Backend: http://localhost:8081
- Database: localhost:5433

### Arrêter l'Application
```bash
# Ctrl+C dans chaque terminal

# Arrêter PostgreSQL
cd infra
docker compose down
```

---

## 3. Git Workflow Complet

### 3.1 Vérifier les Changements

```bash
# Voir les fichiers modifiés
git status

# Voir les différences
git diff
```

### 3.2 Créer une Branche (Optionnel mais Recommandé)

```bash
# Créer et basculer vers une nouvelle branche
git checkout -b feature/production-deployment

# Ou si la branche existe déjà
git checkout feature/production-deployment
```

### 3.3 Ajouter les Changements

```bash
# Ajouter tous les fichiers
git add .

# OU ajouter des fichiers spécifiques
git add backend/blockchain-analytics/src/main/resources/application.properties
git add frontend/.env
git add DEPLOYMENT_MASTER_GUIDE.md
```

### 3.4 Commit

```bash
# Commit avec un message descriptif
git commit -m "feat: add production deployment configuration for Railway and Vercel

- Configure application.properties with DATABASE_URL, PORT, ALLOWED_ORIGINS
- Add railway.json and nixpacks.toml for Railway deployment
- Add vercel.json for Vercel SPA routing
- Update documentation with deployment guides"
```

### 3.5 Push vers GitHub

```bash
# Si c'est votre première fois sur cette branche
git push -u origin feature/production-deployment

# Ou simplement
git push origin feature/production-deployment

# Si vous êtes sur main directement
git push origin main
```

### 3.6 Créer une Pull Request (Merge Request)

**Via GitHub Interface:**
1. Aller sur https://github.com/votre-username/ai-blockchain-analytics
2. Vous verrez un bouton **"Compare & pull request"**
3. Cliquer dessus
4. Ajouter un titre: `feat: Production deployment configuration`
5. Ajouter une description:
   ```
   ## Changes
   - Backend configuration for Railway
   - Frontend configuration for Vercel
   - Docker image build for AMD64
   - CORS configuration
   
   ## Testing
   - ✅ Local development tested
   - ✅ Docker build successful
   - ✅ Ready for production deployment
   ```
6. Cliquer **"Create pull request"**

**Via CLI (avec GitHub CLI):**
```bash
# Installer gh si pas déjà fait: brew install gh
gh pr create --title "feat: Production deployment configuration" --body "Production ready deployment for Railway and Vercel"
```

### 3.7 Merger la Pull Request

**Via GitHub Interface:**
1. Réviser les changements
2. Cliquer **"Merge pull request"**
3. Cliquer **"Confirm merge"**
4. Optionnel: Supprimer la branche

**Via CLI:**
```bash
gh pr merge --merge
```

### 3.8 Revenir sur Main et Mettre à Jour

```bash
# Basculer sur main
git checkout main

# Mettre à jour main avec les derniers changements
git pull origin main
```

---

## 4. Déploiement Backend (Railway)

### Option A: Via Docker Hub (Actuel)

#### 4.1 Vérifier Docker

```bash
# S'assurer que Docker Desktop est lancé
docker ps
```

#### 4.2 Builder l'Image (Architecture AMD64)

```bash
cd backend/blockchain-analytics

# Activer buildx si nécessaire
docker buildx create --use

# Builder ET pusher l'image pour AMD64 (compatible Railway)
docker buildx build --platform linux/amd64 -t yeve/ai-blockchain-backend:latest --push .
```

**Attendre** que le build et le push se terminent (peut prendre 5-10 minutes).

#### 4.3 Vérifier sur Docker Hub

Aller sur https://hub.docker.com/r/yeve/ai-blockchain-backend/tags
Vous devriez voir le tag `latest` mis à jour.

#### 4.4 Configurer Railway

**Via Dashboard (Recommandé):**

1. **Aller sur** https://railway.app
2. **Ouvrir** votre projet `aware-imagination`
3. **Cliquer** sur le service `back`

4. **Ajouter PostgreSQL** (si pas déjà fait):
   - Cliquer sur **"+ New"**
   - Sélectionner **"Database" → "Add PostgreSQL"**
   - Railway créera automatiquement la variable `DATABASE_URL`

5. **Configurer les Variables**:
   - Aller dans l'onglet **"Variables"**
   - Ajouter les variables suivantes:
   
   ```bash
   ALLOWED_ORIGINS=https://ai-blockchain-analytics.vercel.app
   ```
   
   Ces variables sont déjà auto-configurées par Railway:
   - `DATABASE_URL` (depuis PostgreSQL)
   - `PORT` (assigné dynamiquement)

6. **Redéployer**:
   - Aller dans l'onglet **"Deployments"**
   - Cliquer sur les 3 points `...` du dernier déploiement
   - Cliquer **"Redeploy"**
   - Railway va tirer la nouvelle image Docker

#### 4.5 Vérifier les Logs

- Rester dans l'onglet **"Deployments"**
- Cliquer sur le déploiement en cours
- Onglet **"Deploy Logs"** pour voir le démarrage
- Attendre le message: `Started BlockchainAnalyticsApplication`

#### 4.6 Tester le Backend

```bash
# Remplacer par votre URL Railway
curl https://back-production-710c.up.railway.app/api/prices/snapshots
```

Vous devriez recevoir une réponse JSON (peut être vide `[]` si pas de données).

### Option B: Via GitHub Repository (Alternative)

Si vous préférez que Railway build directement depuis GitHub:

1. Dans Railway, changez la **Source** de "Docker Image" à "GitHub Repository"
2. Railway utilisera `railway.json` et `nixpacks.toml`
3. Chaque push sur GitHub déclenchera un build automatique

---

## 5. Déploiement Frontend (Vercel)

### 5.1 Push vers GitHub (si pas déjà fait)

```bash
cd /Users/yevheniibondarenko/ai-blockchain-analytics/ai-blockchain-analytics

# S'assurer que tout est commité
git status

# Si des changements non commités
git add .
git commit -m "update: frontend configuration for production"
git push origin main
```

### 5.2 Configurer les Variables d'Environnement

**Via Dashboard Vercel (Recommandé):**

1. **Aller sur** https://vercel.com
2. **Ouvrir** votre projet
3. **Settings** → **Environment Variables**
4. **Ajouter** la variable:
   - **Name**: `REACT_APP_BACKEND_URL`
   - **Value**: `https://back-production-710c.up.railway.app`
   - **Environment**: Cocher **Production** ✅
5. Cliquer **"Save"**

**Via CLI:**
```bash
cd frontend

# Se connecter si pas déjà fait
vercel login

# Ajouter la variable
vercel env add REACT_APP_BACKEND_URL production
# Quand demandé, entrer: https://back-production-710c.up.railway.app
```

### 5.3 Redéployer

**Option A: Automatique**
- Vercel détecte le push GitHub et redéploie automatiquement

**Option B: Manuel via Dashboard**
1. Onglet **"Deployments"**
2. Cliquer sur les 3 points `...` du dernier déploiement
3. Cliquer **"Redeploy"**

**Option C: Manuel via CLI**
```bash
cd frontend
vercel --prod
```

### 5.4 Vérifier les Logs

Dans le dashboard Vercel:
- Onglet **"Deployments"**
- Cliquer sur le déploiement en cours
- Voir les logs de build
- Attendre **"Deployment completed"** ✅

### 5.5 Tester le Frontend

```bash
# Ouvrir dans le navigateur
open https://ai-blockchain-analytics.vercel.app
```

---

## 6. Configuration des Variables

### Résumé Complet

| Plateforme | Variable | Valeur | Comment |
|------------|----------|--------|---------|
| **Railway** | `DATABASE_URL` | Auto-généré | Créé automatiquement par PostgreSQL |
| **Railway** | `PORT` | Auto-assigné | Assigné dynamiquement par Railway |
| **Railway** | `ALLOWED_ORIGINS` | `https://ai-blockchain-analytics.vercel.app` | **À configurer manuellement** |
| **Railway** | `COVALENT_API_KEY` | Votre clé API | Optionnel |
| **Vercel** | `REACT_APP_BACKEND_URL` | `https://back-production-710c.up.railway.app` | **À configurer manuellement** |

### Comment Fonctionne la Configuration

Les fichiers `application.properties` et `.env` utilisent des **valeurs par défaut** pour le local et des **variables d'environnement** pour la production:

**Backend (`application.properties`):**
```properties
# Local par défaut: jdbc:postgresql://localhost:5433/ai_blockchain
# Production: utilise DATABASE_URL de Railway
spring.datasource.url=${DATABASE_URL:jdbc:postgresql://localhost:5433/ai_blockchain}

# Local par défaut: 8081
# Production: utilise PORT de Railway
server.port=${PORT:8081}

# Local par défaut: http://localhost:3000
# Production: utilise ALLOWED_ORIGINS de Railway
cors.allowed-origins=${ALLOWED_ORIGINS:http://localhost:3000}
```

**Frontend (`.env`):**
```bash
# Utilisé en local
REACT_APP_BACKEND_URL=http://localhost:8081

# En production, Vercel utilise la variable d'environnement configurée
```

---

## 7. Tests et Validation

### 7.1 Test Backend

```bash
# Tester l'endpoint API
curl https://back-production-710c.up.railway.app/api/prices/snapshots

# Devrait retourner JSON (peut être vide [])
```

### 7.2 Test Frontend

1. **Ouvrir** https://ai-blockchain-analytics.vercel.app
2. **Ouvrir la console** du navigateur (F12)
3. **Vérifier**:
   - Pas d'erreurs CORS
   - Pas d'erreurs 404
   - Les composants se chargent

### 7.3 Test End-to-End

1. Dans le frontend, essayer de:
   - Voir les prix crypto (si données en DB)
   - Rechercher une adresse wallet
2. Vérifier que les appels API fonctionnent dans l'onglet **Network** du navigateur

### 7.4 Checklist Finale

- [ ] Backend Railway répond sur `/api/prices/snapshots`
- [ ] Frontend Vercel charge sans erreur
- [ ] Pas d'erreurs CORS dans la console
- [ ] Les appels API fonctionnent
- [ ] PostgreSQL connecté (vérifier logs Railway)

---

## 8. Troubleshooting

### Problème: Backend "Exec format error"

**Cause**: Image Docker buildée pour ARM64 (Mac M1/M2) au lieu de AMD64.

**Solution**:
```bash
docker buildx build --platform linux/amd64 -t yeve/ai-blockchain-backend:latest --push .
```

### Problème: CORS Error dans le Frontend

**Cause**: `ALLOWED_ORIGINS` mal configuré sur Railway.

**Solution**:
- Vérifier que `ALLOWED_ORIGINS=https://ai-blockchain-analytics.vercel.app`
- Pas de slash `/` à la fin
- Redéployer Railway après changement

### Problème: Backend ne peut pas se connecter à la DB

**Cause**: PostgreSQL pas ajouté ou `DATABASE_URL` manquant.

**Solution**:
1. Aller sur Railway dashboard
2. Ajouter PostgreSQL via "+ New" → "Database" → "Add PostgreSQL"
3. Vérifier que `DATABASE_URL` apparaît dans Variables
4. Redéployer

### Problème: Frontend ne peut pas atteindre le Backend

**Cause**: `REACT_APP_BACKEND_URL` mal configuré sur Vercel.

**Solution**:
1. Vérifier dans Vercel → Settings → Environment Variables
2. Doit être: `https://back-production-710c.up.railway.app`
3. Redéployer Vercel après changement

### Problème: Railway Build Failed

**Cause**: Image Docker corrompue ou build timeout.

**Solution**:
```bash
# Rebuild l'image localement
docker buildx build --platform linux/amd64 -t yeve/ai-blockchain-backend:latest --push .

# Attendre que le push se termine
# Redéployer sur Railway
```

### Voir les Logs

**Railway:**
- Dashboard → Deployments → Cliquer sur déploiement → Deploy Logs

**Vercel:**
- Dashboard → Deployments → Cliquer sur déploiement → Logs

---

## 📚 Commandes Rapides de Référence

### Git
```bash
git status                          # Voir les changements
git add .                           # Ajouter tous les fichiers
git commit -m "message"            # Commit
git push origin main               # Push vers GitHub
```

### Docker
```bash
docker ps                                      # Voir conteneurs actifs
docker buildx build --platform linux/amd64 \
  -t yeve/ai-blockchain-backend:latest --push . # Build & Push
```

### Local Development
```bash
# Database
docker compose up -d db

# Backend
./mvnw spring-boot:run

# Frontend
npm start
```

### Deployment
```bash
# Railway (via Docker Hub - rebuild image)
docker buildx build --platform linux/amd64 -t yeve/ai-blockchain-backend:latest --push .

# Vercel (via GitHub - push code)
git push origin main
```

---

## 🎯 Workflow Complet Résumé

```
1. Développer en local
   ↓
2. Tester en local
   ↓
3. git add . && git commit -m "message"
   ↓
4. git push origin main
   ↓
5. Docker: rebuild & push image
   ↓
6. Railway: redéployer (tire nouvelle image)
   ↓
7. Vercel: redéploie automatiquement (détecte push GitHub)
   ↓
8. Vérifier les deux déploiements
   ↓
9. Tester en production
```

---

## ✅ URLs Finales

**Production:**
- Frontend: https://ai-blockchain-analytics.vercel.app
- Backend: https://back-production-710c.up.railway.app

**Local:**
- Frontend: http://localhost:3000
- Backend: http://localhost:8081

---

**Dernière mise à jour**: 20 Novembre 2025  
**Status**: ✅ Production Ready
