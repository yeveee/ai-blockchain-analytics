# Backend - Blockchain Analytics

API REST Spring Boot pour l'analyse blockchain et le suivi des prix de cryptomonnaies.

## 🏗️ Architecture

Le backend suit une architecture en couches classique Spring Boot :

```
src/main/java/com/ai/blockchain_analytics/
├── config/              # Configuration de l'application
│   ├── WebConfig.java           # Configuration CORS
│   └── RestTemplateConfig.java  # Configuration HTTP client
├── controller/          # Controllers REST
│   ├── PriceSnapshotController.java
│   └── WalletController.java
├── dto/                 # Data Transfer Objects
│   └── WalletBalanceDTO.java
├── model/               # Entités JPA
│   └── PriceSnapshot.java
├── repository/          # Repositories Spring Data
│   └── PriceSnapshotRepository.java
└── service/             # Logique métier
    ├── CoinGeckoService.java
    └── WalletService.java
```

## 🔌 APIs Externes

### CoinGecko API
- **Usage** : Récupération des prix de cryptomonnaies
- **Endpoint** : `https://api.coingecko.com/api/v3/simple/price`
- **Fréquence** : Toutes les 5 minutes (configurable)
- **Rate limit** : Gratuit jusqu'à 50 appels/minute

### Covalent API
- **Usage** : Récupération des balances ERC-20 de wallets
- **Endpoint** : `https://api.covalenthq.com/v1/{chain_id}/address/{address}/balances_v2/`
- **Authentification** : API Key requise
- **Documentation** : https://www.covalenthq.com/docs/

## 📊 Modèle de Données

### PriceSnapshot
Stocke les snapshots de prix des cryptomonnaies.

```java
@Entity
public class PriceSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String symbol;           // BTC, ETH, MATIC
    private BigDecimal price;        // Prix en USD
    private LocalDateTime timestamp; // Date/heure du snapshot
}
```

## ⚙️ Configuration

### application.properties

```properties
# Database
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5433/ai_blockchain}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:user}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:password}

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=${SPRING_JPA_HIBERNATE_DDL_AUTO:update}
spring.jpa.show-sql=${SPRING_JPA_SHOW_SQL:false}

# Server
server.port=${SERVER_PORT:8081}

# APIs
covalent.api.key=${COVALENT_API_KEY:}
covalent.api.url=${COVALENT_API_URL:https://api.covalenthq.com/v1}

# CORS
cors.allowed-origins=${CORS_ALLOWED_ORIGINS:http://localhost:3000,http://localhost:3001}

# Logging
logging.level.com.ai.blockchain_analytics=DEBUG
```

## 🚀 Démarrage

### Prérequis
- Java 17+
- Maven 3.6+
- PostgreSQL 15+

### Installation

```bash
cd backend/blockchain-analytics

# Compiler le projet
mvn clean install

# Lancer l'application
mvn spring-boot:run
```

L'API sera accessible sur `http://localhost:8081`

## 🧪 Tests

```bash
# Exécuter tous les tests
mvn test

# Exécuter les tests avec rapport de couverture
mvn test jacoco:report
```

## 📝 Endpoints

### GET /api/prices/snapshots
Récupère tous les snapshots de prix.

**Réponse:**
```json
[
  {
    "symbol": "BTC",
    "timestamp": "2024-11-19T14:30:00",
    "price": 43250.50
  }
]
```

### GET /api/wallet/{address}
Récupère les balances ERC-20 d'un wallet.

**Paramètres:**
- `address`: Adresse Ethereum (0x...)

**Réponse:**
```json
[
  {
    "contractName": "USD Coin",
    "symbol": "USDC",
    "balance": 1000.000000,
    "balanceUsd": 1000.00
  }
]
```

## 🔒 Sécurité

- **CORS** : Configuré via `WebConfig.java`
- **Validation** : Validation des adresses Ethereum
- **Error Handling** : Gestion centralisée des erreurs avec ResponseEntity
- **Logging** : Logs détaillés avec SLF4J/Logback

## 🎯 Bonnes Pratiques Appliquées

- ✅ Architecture en couches (Controller → Service → Repository)
- ✅ Injection de dépendances via constructeur
- ✅ DTOs pour la communication API
- ✅ Logging structuré
- ✅ Gestion d'erreurs avec try-catch
- ✅ Configuration externalisée avec variables d'environnement
- ✅ Documentation JavaDoc
- ✅ Beans Spring pour composants réutilisables (RestTemplate)
