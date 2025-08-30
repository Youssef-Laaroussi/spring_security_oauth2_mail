# 🔐 Spring Security + Angular 16 - Full Stack Authentication

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen.svg)
![Angular](https://img.shields.io/badge/Angular-16-red.svg)
![Java](https://img.shields.io/badge/Java-17-orange.svg)
![TypeScript](https://img.shields.io/badge/TypeScript-5.0-blue.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

Un projet full-stack moderne avec authentification complète utilisant **Spring Security** pour le backend et **Angular 16** pour le frontend. Ce projet implémente toutes les fonctionnalités d'authentification essentielles avec les meilleures pratiques de sécurité.

## 🌟 Fonctionnalités

### 🔑 Authentification & Autorisation
- ✅ **Login/Register** classique avec validation
- ✅ **JWT Token** avec gestion automatique des sessions
- ✅ **OAuth2** intégration (Google, GitHub)
- ✅ **Protection des routes** avec Guards Angular
- ✅ **Rôles utilisateurs** (USER, ADMIN)

### 📧 Gestion des comptes
- ✅ **Vérification email** obligatoire après inscription
- ✅ **Mot de passe oublié** avec reset par email
- ✅ **Profil utilisateur** avec informations de connexion
- ✅ **Tokens d'expiration** gérés automatiquement

### 🛡️ Sécurité
- ✅ **CORS** configuré correctement
- ✅ **CSRF** protection
- ✅ **Validation** côté client et serveur
- ✅ **Chiffrement** des mots de passe (BCrypt)
- ✅ **Headers sécurisés** et best practices

### 🎨 Interface utilisateur
- ✅ **Design responsive** avec Bootstrap 5
- ✅ **Feedback utilisateur** complet
- ✅ **Gestion d'erreurs** élégante
- ✅ **Loading states** et animations
- ✅ **Navigation intuitive**

## 🏗️ Architecture du projet

```
spring-security-project/
├── backend/                          # Spring Boot Application
│   ├── src/main/java/com/example/security/
│   │   ├── config/                   # Configuration sécurité
│   │   ├── controller/               # REST Controllers
│   │   ├── dto/                      # Data Transfer Objects
│   │   ├── entity/                   # JPA Entities
│   │   ├── repository/               # Repositories JPA
│   │   ├── service/                  # Business Logic
│   │   └── util/                     # Utilitaires (JWT)
│   ├── src/main/resources/
│   │   └── application.yml           # Configuration Spring
│   └── pom.xml                       # Dependencies Maven
├── frontend/                         # Angular Application
│   ├── src/app/
│   │   ├── auth/                     # Module d'authentification
│   │   ├── core/                     # Services, Guards, Interceptors
│   │   ├── shared/                   # Composants partagés
│   │   └── features/                 # Fonctionnalités métier
│   ├── src/environments/             # Configuration environnements
│   └── package.json                  # Dependencies npm
└── README.md
```

## 🚀 Installation et configuration

### Prérequis

- **Java 17+**
- **Node.js 16+** et npm
- **Maven 3.6+**
- **Git**

### 1. Cloner le repository

```bash
git clone https://github.com/votre-username/spring-security-angular.git
cd spring-security-angular
```

### 2. Configuration du Backend

#### Configuration de la base de données et des services

Créer un fichier `backend/src/main/resources/application-local.yml` :

```yaml
spring:
  profiles:
    active: local
  
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password: password
  
  mail:
    host: smtp.gmail.com
    port: 587
    username: votre-email@gmail.com
    password: votre-mot-de-passe-app
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
  
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: votre-google-client-id
            client-secret: votre-google-client-secret
          github:
            client-id: votre-github-client-id
            client-secret: votre-github-client-secret

app:
  jwt:
    secret: votreClé4SecretJWT1234567890SupperLongue
    expiration: 86400000 # 24 heures
```

#### Démarrer le backend

```bash
cd backend
./mvnw spring-boot:run
```

Le serveur démarre sur `http://localhost:8080`

### 3. Configuration du Frontend

#### Installation des dépendances

```bash
cd frontend
npm install
```

#### Configuration de l'environnement

Modifier `frontend/src/environments/environment.ts` :

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api',
  googleClientId: 'votre-google-client-id'
};
```

#### Démarrer le frontend

```bash
ng serve
```

L'application démarre sur `http://localhost:4200`

## 🔧 Configuration OAuth2

### Google OAuth2

1. Aller sur [Google Cloud Console](https://console.cloud.google.com/)
2. Créer un nouveau projet ou sélectionner un existant
3. Activer l'API Google+ 
4. Créer des identifiants OAuth 2.0
5. Ajouter `http://localhost:8080/login/oauth2/code/google` dans les URIs de redirection

### GitHub OAuth2

1. Aller dans GitHub Settings > Developer settings > OAuth Apps
2. Créer une nouvelle OAuth App
3. Ajouter `http://localhost:8080/login/oauth2/code/github` comme Authorization callback URL

## 📧 Configuration Email

### Gmail SMTP

1. Activer l'authentification à 2 facteurs sur votre compte Gmail
2. Générer un mot de passe d'application
3. Utiliser ce mot de passe dans la configuration `application.yml`

## 🧪 Tests

### Backend Tests

```bash
cd backend
./mvnw test
```

### Frontend Tests

```bash
cd frontend
npm run test
```

## 📱 Utilisation de l'application

### 1. Inscription

1. Accéder à `http://localhost:4200/auth/register`
2. Remplir le formulaire d'inscription
3. Vérifier votre email et cliquer sur le lien de vérification
4. Votre compte est maintenant activé

### 2. Connexion

- **Connexion classique** : email/mot de passe
- **OAuth2** : boutons Google ou GitHub
- **Mot de passe oublié** : lien de réinitialisation par email

### 3. Fonctionnalités

- **Dashboard** : page d'accueil après connexion
- **Profil** : informations utilisateur
- **Déconnexion** : suppression sécurisée de la session

## 🔒 Endpoints API

### Authentication

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/auth/login` | Connexion utilisateur |
| POST | `/api/auth/register` | Inscription utilisateur |
| GET | `/api/auth/verify-email` | Vérification email |
| POST | `/api/auth/forgot-password` | Mot de passe oublié |
| POST | `/api/auth/reset-password` | Reset mot de passe |

### User

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/user/profile` | Profil utilisateur |

### OAuth2

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/oauth2/authorization/google` | Connexion Google |
| GET | `/oauth2/authorization/github` | Connexion GitHub |

## 🚢 Déploiement

### Backend (Heroku)

```bash
cd backend
heroku create votre-app-backend
heroku config:set SPRING_PROFILES_ACTIVE=prod
heroku config:set JWT_SECRET=votre-clé-secrète
git push heroku main
```

### Frontend (Netlify)

```bash
cd frontend
ng build --prod
# Déployer le dossier dist/ sur Netlify
```

## 🤝 Contribution

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📝 Roadmap

- [ ] **Refresh tokens** automatiques
- [ ] **Two-factor authentication** (2FA)
- [ ] **Social logins** supplémentaires (Facebook, Twitter)
- [ ] **Admin panel** pour la gestion des utilisateurs
- [ ] **Rate limiting** sur les APIs
- [ ] **Audit logs** pour la sécurité
- [ ] **Docker** containerization
- [ ] **Tests d'intégration** complets

## 🐛 Problèmes connus

- Les tokens OAuth2 ne sont pas automatiquement rafraîchis
- La validation côté client pourrait être renforcée
- Les tests e2e ne sont pas encore implémentés

## 📄 License

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 👥 Auteurs

- **Youssef Laaroussi** - [@Youssef-Laaroussi](https://github.com/Youssef-Laaroussi)

## 🙏 Remerciements

- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [Angular Documentation](https://angular.io/docs)
- [JWT.io](https://jwt.io/) pour les outils JWT
- [Bootstrap](https://getbootstrap.com/) pour l'UI

---

⭐ **Si ce projet vous a été utile, n'oubliez pas de lui donner une étoile !** ⭐
