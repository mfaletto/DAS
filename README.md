# Ristorino

Repositorio de Marcos Faletto, integrante del proyecto final "Ristorino" para la materia DAS.

Aplicación web para gestión de reservas en restaurantes.

## Contenido del Repositorio

En esta rama se verán los siguientes contenidos:
- Consigna y caso de estudio
- Diagramas SOA
- Mockups
- Desarrollo completo del proyecto (Frontend y Backend)

## Estructura del Proyecto

```
Ristorino/
├── frontend/          # Aplicación Angular
├── backend/           # API Spring Boot
├── Consigna/          # Consignas y casos de estudio
├── Mockups/           # Mockups HTML y screenshots
├── SOA/               # Diagramas SOA
├── ScriptsDB/         # Scripts de creación de base de datos
└── ScriptsLlenado/    # Scripts de inserción de datos
```

## Tecnologías

### Frontend
- Angular 20.3.0
- Angular Material
- TypeScript

### Backend
- Spring Boot 3.5.7
- Java 17
- SQL Server
- JPA/Hibernate

## Configuración

### Backend
1. Configurar la base de datos en `backend/src/main/resources/application.properties`
2. Ejecutar los scripts SQL en `ScriptsDB/`
3. Ejecutar: `mvn spring-boot:run`

### Frontend
1. Instalar dependencias: `npm install`
2. Ejecutar: `npm start`
3. La aplicación estará disponible en `http://localhost:4200`

## Base de Datos
- Nombre: `ristorinoDB`
- Motor: SQL Server
- Scripts de creación: `ScriptsDB/QueryCreacionRistorino.sql`
