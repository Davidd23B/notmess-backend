# NotMess Backend

## Descripción del Proyecto

**NotMess Backend** es la API REST del sistema NotMess, desarrollada con Spring Boot. Esta aplicación backend proporciona los servicios y funcionalidades necesarias para gestionar la lógica de negocio, el acceso a datos y la comunicación con el frontend de la aplicación.

## Características Principales

- 🚀 **API REST** desarrollada con Spring Boot
- 📊 **Gestión de datos** con Spring Data JPA
- 🔒 **Seguridad** implementada con Spring Security
- 📦 **Gestión de dependencias** con Maven

## Tecnologías Utilizadas

- **Java 17+**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **Spring Security**
- **Maven**
- **PostgreSQL**

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/notmess/backend/
│   │       ├── NotMessBackendApplication.java
│   │       ├── controller/          # Controladores REST
│   │       ├── service/             # Lógica de negocio
│   │       ├── repository/          # Acceso a datos
│   │       ├── model/               # Entidades JPA
│   │       ├── dto/                 # Data Transfer Objects
│   │       └── config/              # Configuraciones
│   └── resources/
│       ├── application.properties   # Configuración de la aplicación
└── test/                           # Tests unitarios e integración
```
