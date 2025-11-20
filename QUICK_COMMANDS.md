# ⚡ Quick Commands Reference

Guide ultra-rapide - juste les commandes essentielles.

---

## 🚀 Déploiement Complet (3 Étapes)

### 1. Git Push
```bash
git add .
git commit -m "feat: your changes"
git push origin main
```

### 2. Docker Build & Push (Backend)
```bash
cd backend/blockchain-analytics
docker buildx build --platform linux/amd64 -t yeve/ai-blockchain-backend:latest --push .
```

### 3. Redéployer
- **Railway**: Dashboard → Deployments → Redeploy
- **Vercel**: Automatique (détecte le push GitHub)

---

## 💻 Local Development

```bash
# Terminal 1: Database
cd infra && docker compose up -d db

# Terminal 2: Backend
cd backend/blockchain-analytics && ./mvnw spring-boot:run

# Terminal 3: Frontend
cd frontend && npm start
```

**URLs**: Frontend http://localhost:3000 | Backend http://localhost:8081

---

## 🔧 Variables d'Environnement

### Railway
```bash
ALLOWED_ORIGINS=https://ai-blockchain-analytics.vercel.app
```

### Vercel
```bash
REACT_APP_BACKEND_URL=https://back-production-710c.up.railway.app
```

---

## 🧪 Tests Production

```bash
# Backend
curl https://back-production-710c.up.railway.app/api/prices/snapshots

# Frontend
open https://ai-blockchain-analytics.vercel.app
```

---

## 🆘 Fix Communs

**Backend Crash**: Rebuild avec `--platform linux/amd64`  
**CORS Error**: Vérifier `ALLOWED_ORIGINS` sur Railway  
**Frontend 404**: Vérifier `REACT_APP_BACKEND_URL` sur Vercel  

---

**Guide complet**: `DEPLOYMENT_MASTER_GUIDE.md`
