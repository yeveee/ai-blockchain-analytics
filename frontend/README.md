# Frontend - Blockchain Analytics Dashboard

Interface React moderne pour l'analyse blockchain et le suivi des portefeuilles Ethereum.

## 🏗️ Architecture

Le frontend suit une architecture React moderne avec hooks et séparation des responsabilités :

```
src/
├── components/          # Composants React
│   ├── PriceChart.jsx           # Graphique de prix
│   ├── PriceChart.module.css
│   ├── WalletBalance.jsx        # Recherche de wallet
│   └── WalletBalance.module.css
├── constants/           # Constantes de l'application
│   ├── api.js                   # URLs et endpoints API
│   └── styles.js                # Thème et couleurs
├── hooks/               # Custom React hooks
│   └── useApi.js                # Hook pour appels API
├── utils/               # Fonctions utilitaires
│   └── formatters.js            # Formatage de données
├── App.jsx              # Composant principal
├── App.module.css
└── index.js             # Point d'entrée
```

## ✨ Composants

### PriceChart
Affiche un graphique des prix historiques pour une cryptomonnaie sélectionnée.

**Features:**
- Sélecteur de crypto (BTC, ETH, MATIC)
- Graphique interactif avec Recharts
- Actualisation automatique des données
- États de chargement et d'erreur

### WalletBalance
Permet de rechercher et afficher les balances ERC-20 d'un wallet Ethereum.

**Features:**
- Validation d'adresse Ethereum
- Affichage des tokens avec valeurs USD
- Calcul automatique du total
- Gestion d'erreurs

## 🎨 Styling

Le projet utilise **CSS Modules** pour un styling scopé et maintenable :

- Pas de conflits de noms de classes
- Styles co-localisés avec les composants
- Thème centralisé dans `constants/styles.js`
- Support du hover et des transitions

## 🔧 Configuration

### Variables d'Environnement

Créer un fichier `.env` à la racine du frontend :

```bash
REACT_APP_BACKEND_URL=http://localhost:8081
```

### Constants API

Les endpoints sont configurés dans `src/constants/api.js` :

```javascript
export const API_ENDPOINTS = {
  WALLET_BALANCE: (address) => `${API_BASE_URL}/api/wallet/${address}`,
  PRICE_SNAPSHOTS: `${API_BASE_URL}/api/prices/snapshots`,
};
```

## 🚀 Scripts Disponibles

### `npm start`
Lance l'application en mode développement sur http://localhost:3000

### `npm test`
Exécute les tests en mode watch

### `npm run build`
Compile l'application pour la production dans le dossier `build`

### `npm run eject`
⚠️ Opération irréversible qui expose la configuration

## 📦 Dépendances

### Production
- **react** (18.2.0) - Bibliothèque UI
- **react-dom** (18.2.0) - Rendu React
- **axios** (1.13.2) - Client HTTP
- **recharts** (2.9.0) - Graphiques
- **react-scripts** (5.0.1) - Toolchain Create React App

### Développement
- **@testing-library/react** - Tests de composants
- **@testing-library/jest-dom** - Matchers Jest personnalisés
- **@testing-library/user-event** - Simulation d'événements utilisateur

## 🧪 Tests

```bash
# Exécuter tous les tests
npm test

# Avec couverture
npm test -- --coverage
```

## 🎯 Bonnes Pratiques Appliquées

- ✅ **CSS Modules** pour éviter les conflits de styles
- ✅ **Custom hooks** pour la logique réutilisable
- ✅ **Constants** pour les valeurs partagées
- ✅ **Utility functions** pour le formatage
- ✅ **Error boundaries** et gestion d'erreurs
- ✅ **Loading states** pour une meilleure UX
- ✅ **Validation** des entrées utilisateur
- ✅ **Code splitting** avec imports dynamiques
- ✅ **Responsive design** mobile-first

## 🌐 Déploiement

### Build de Production

```bash
npm run build
```

L'application optimisée est générée dans le dossier `build/`.

### Déploiement Vercel

```bash
# Installer Vercel CLI
npm i -g vercel

# Déployer
vercel --prod
```

### Variables d'Environnement Production

Configurer `REACT_APP_BACKEND_URL` avec l'URL du backend en production.

## 🐛 Debugging

### React DevTools
Installer l'extension React DevTools pour Chrome/Firefox

### Console Logs
Les logs sont visibles dans la console du navigateur :
- Données API reçues
- Erreurs de réseau
- États des composants

## 📚 Ressources

- [React Documentation](https://react.dev/)
- [Create React App](https://create-react-app.dev/)
- [Recharts Documentation](https://recharts.org/)
- [CSS Modules](https://github.com/css-modules/css-modules)
