# DAS - Sistema de Reservas de Restaurantes

## Descripcion General

Monorepo universitario (UBP - Universidad Blas Pascal) para la materia **Desarrollo de Aplicaciones para Servicios (DAS)**. Es un sistema de reservas de restaurantes que integra multiples tecnologias de servicios web (REST, SOAP, JDBC) en una arquitectura de microservicios.

El sistema permite a los usuarios registrarse, consultar disponibilidad en distintos restaurantes (cada uno expuesto con un tipo de servicio diferente), realizar reservas, y visualizar un historial de reservas.

---

## Arquitectura General

```
+---------------------------------------------+
|      RISTORINO-FRONTEND (Angular 21)        |
|      http://localhost:4200                   |
+----------------------+-----------------------+
                       |
                 HTTP REST (JSON)
                       |
    +------------------v---------------------+
    |   RISTORINO-BACKEND (Spring Boot)      |
    |   http://localhost:8080                 |
    |                                        |
    |   - Auth (login/registro)              |
    |   - Reservas (CRUD)                    |
    |   - Portal (disponibilidad unificada)  |
    |   - Marketing (clicks/promociones)     |
    |   - IA (interpretacion NLP, copy)      |
    |                                        |
    |   Base de Datos: SQL Server (JDBC)     |
    +--------+-------------------+-----------+
             |                   |
        SOAP |                   | REST
             |                   |
    +--------v------+   +--------v-----------+
    |   PERUKAI     |   |   LABELLAPIZZA     |
    |   (SOAP)      |   |   (REST)           |
    |   Port 8082   |   |   Port 8081        |
    +---------------+   +--------------------+
```

---

## Modulos del Proyecto

### 1. `ristorino-backend` (Puerto 8080) - Agregador Principal

**Rol:** Backend central que actua como agregador. Consume servicios REST y SOAP de los otros backends, gestiona usuarios, reservas y marketing. Se conecta a SQL Server via JDBC.

**Stack:**
- Spring Boot 4.0.2, Java 17, Maven (WAR packaging)
- Base de datos: Microsoft SQL Server (`ristorino_db`)
- Cliente SOAP dinamico (Jakarta SOAP/JAX-WS + JAXB)
- Cliente REST (Jersey)
- Seguridad: OAuth2/JWT (infraestructura presente, **deshabilitada** por defecto con `security.enabled=false`)

**Conexion a BD:**
```
URL: jdbc:sqlserver://localhost:1433;databaseName=ristorino_db
User: sa / Password: 123456
```

**Controllers:**

| Controller | Ruta Base | Endpoints | Descripcion |
|---|---|---|---|
| `AuthController` | `/api/auth` | `POST /login`, `POST /registro` | Autenticacion de usuarios |
| `ReservaController` | `/api/reservas` | `POST /crear`, `GET /usuario/{id}`, `PUT /{nroReserva}` | CRUD de reservas |
| `PortalController` | `/api/portal` | `POST /disponibilidad` | Consulta unificada (despacha a REST o SOAP segun config en BD) |
| `MarketingController` | `/api/marketing` | `GET /contenidos`, `POST /click` | Contenido promocional y tracking de clicks |
| `IAController` | `/api/ia` | `POST /interpretar`, `GET /generar-copy` | Interpretacion NLP y generacion de copy |
| `PerukaiClienteController` | `/api/restaurantes/perukai` | `GET /disponibilidad` | Cliente SOAP directo a Perukai |

**Services:**
- `ReservaService` - Logica transaccional de reservas (usa stored procedures)
- `PortalService` - Despacho unificado de disponibilidad (REST vs SOAP segun tipo de restaurante en BD)
- `MarketingService` - Contenido promocional con deteccion anti-fraude de clicks
- `JerseyRestClientService` - Wrapper para llamadas REST via Jersey

**Repositories (JDBC):**
- `ReservaRepository` - Usa `JdbcTemplate` + `SimpleJdbcCall` (SP: `sp_crear_reserva` con OUTPUT param)
- `ClienteRepository` - Login, registro, validacion de email unico (`NamedParameterJdbcTemplate`)
- `RestauranteRepository` - Catalogo de restaurantes externos (`BeanPropertyRowMapper`)
- `ClickRepository` - Tracking de clicks (`sp_registrar_click`, deteccion de fraude por dia)

**Modelos:**
- `ReservaBean` - nroReserva, nroCliente, nroRestaurante, fechaReserva, horaReserva, cantAdultos, cantMenores, observaciones
- `ClienteBean` - nroCliente, nombre, apellido, correo, clave, telefono, direccion, habilitado
- `RestauranteBean` - id, razonSocial, tipoConexion, endpointUrl

**Utilidades:**
- `SOAPClient` (321 lineas) - Cliente SOAP dinamico con patron Builder. Usa `Dispatch<SOAPMessage>`, marshalling JAXB, soporte WS-Security (UsernameToken). Configuracion via JSON.

**Configs:**
- `SecurityConfig` - OAuth2/JWT (deshabilitado)
- `NoSecurityConfig` - Permite todo cuando seguridad esta off
- `ContentNegotiationConfig` - JSON/XML
- `CorsConfig` - Permite `http://localhost:4200`

---

### 2. `labellapizza-backend` (Puerto 8081) - Proveedor REST

**Rol:** Servicio REST simple que expone datos de sucursales, disponibilidad y promociones de "La Bella Pizza". No usa base de datos (datos en memoria).

**Stack:**
- Spring Boot 4.0.2, Java 17, Maven
- Jackson XML para soporte de respuestas XML
- Sin base de datos (repositorios estaticos en memoria)

**Endpoints:**

| Metodo | Ruta | Descripcion |
|---|---|---|
| `GET` | `/api/v1/public/sucursales` | Listar todas las sucursales |
| `GET` | `/api/v1/public/disponibilidad` | Consultar disponibilidad (usa `SucursalArgumentResolver` con Base64) |
| `GET` | `/api/v1/public/marketing` | Obtener promociones de una sucursal |
| `PUT` | `/api/v1/public/sucursales/{id}` | Actualizar info de sucursal |

**Modelos:**
- `Sucursal` - id, nombre, direccion
- `Promocion` - titulo, descripcion, tipo, activa

**Componentes destacados:**
- `SucursalArgumentResolver` - Resuelve parametro `hash` (Base64) a objeto `Sucursal`
- `RistorinoInterceptor` - Logging en rutas `/api/**`
- `WebConfig` - Negociacion de contenido (JSON default, XML con `?mediaType=xml`)

---

### 3. `perukai-backend` (Puerto 8082) - Proveedor SOAP

**Rol:** Servicio SOAP que expone disponibilidad de "Perukai" (restaurante de cocina Nikkei/peruana-japonesa).

**Stack:**
- Spring Boot 4.0.2, Java 17, Maven
- Spring Web Services (spring-boot-starter-webservices)
- WSDL4J + JAXB (Jakarta XML Binding)
- WS-Security con `Wss4jSecurityInterceptor`

**SOAP Endpoint:**
- **URL WSDL:** `http://localhost:8082/ws/perukai.wsdl`
- **Servlet path:** `/ws/*`
- **Namespace:** `http://das.ubp.edu.ar/soap`

**Operacion:**
- `consultarDisponibilidad`
  - **Request:** `tipoCocina` (String)
  - **Response:** `mensaje`, `sugerenciaChef`, `turnosDisponibles[]`
  - **Logica:** Verifica si la cocina es "Nikkei", retorna turnos disponibles (20:30, 22:00)

**Seguridad SOAP (WS-Security):**
- `UsernameToken` authentication
- Credenciales: user `admin` / password `123`

**Schemas:**
- XSD: `src/main/resources/xsd/perukai.xsd`
- WSDL generado: disponible en runtime

---

### 4. `ristorino-frontend` (Puerto 4200) - Frontend Angular

**Rol:** SPA (Single Page Application) que consume la API REST de `ristorino-backend`.

**Stack:**
- Angular 21.0.0, TypeScript 5.9.2, RxJS 7.8.0
- Bootstrap (UI)
- API Base: `http://localhost:8080`

**Modulos:**

| Modulo | Componentes | Descripcion |
|---|---|---|
| `AuthModule` | `LoginComponent`, `RegistroComponent` | Login y registro de usuarios |
| `PortalModule` | `RestauranteDetail`, `MisReservas` | Detalle de restaurante, formulario de reserva, historial |
| `CoreModule` | `AuthGuard`, `ErrorInterceptor` | Proteccion de rutas e interceptor global de errores |

**Services:**
- `AuthService` - Login/registro, sesion en `localStorage` (key: `usuario_ristorino`)
- `PortalService` - Disponibilidad y reservas
- `ReservaService` - Wrapper para crear reservas

**Credenciales demo:** `marcos@email.com` / `123456`

---

## Base de Datos (SQL Server)

### Tablas

| Tabla | Descripcion |
|---|---|
| `restaurantes` | Catalogo de sistemas externos (razon social, tipo conexion REST/SOAP, endpoint URL) |
| `clientes` | Usuarios del portal (nombre, apellido, correo, clave, telefono, direccion, habilitado) |
| `reservas_restaurantes` | Registros de reservas (cliente, restaurante, fecha, hora, adultos, menores, observaciones, estado) |
| `clicks_contenidos` | Tracking de clicks en contenido de marketing (deteccion anti-fraude por dia) |

### Stored Procedures

| SP | Descripcion |
|---|---|
| `sp_crear_reserva` | Crea reserva con OUTPUT parameter para nroReserva generado |
| `sp_registrar_click` | Registra click de marketing con validacion anti-fraude |

### Scripts SQL

- `SQLQuery1.sql` - Creacion de BD, tablas principales (restaurantes, clientes, reservas_restaurantes)
- `SQLQuery2.sql` - Tabla clicks_contenidos
- `SQLQuery3.sql` - SP sp_registrar_click
- `SQLQuery4.sql` - SP sp_crear_reserva

---

## Documentos de Diseno

- `RESTAURANTE-Logico.pdf` - Modelo logico de base de datos de restaurantes
- `RISTORINO-Logico.pdf` - Modelo logico de base de datos de Ristorino

---

## Estado de Componentes Tecnicos

Referencia detallada en `Componentes no usados proyecto.txt`.

### Implementado y Funcionando
- Spring Boot 4.0.2 + Java 17 + Maven en los 3 backends
- Proveedor SOAP con Spring-WS (perukai)
- Proveedores REST con Spring MVC (labellapizza, ristorino)
- Acceso a datos JDBC con stored procedures (ristorino)
- Cliente SOAP dinamico con JAXB marshalling (ristorino)
- Frontend Angular 21 con interceptors HTTP
- Autenticacion (login/registro) funcional
- CORS y negociacion de contenido
- WS-Security con Wss4jSecurityInterceptor (perukai)

### Presente pero Deshabilitado/Parcial
- OAuth2/JWT (infraestructura completa, deshabilitada con flag)
- WAR packaging (declarado en pom.xml, se ejecuta como JAR embebido)
- Jackson XML para respuestas XML (declarado, JSON es primario)
- Jersey REST client (declarado en pom, usado parcialmente)
- jaxws-maven-plugin para wsimport (configurado en ristorino)

### No Implementado (segun requerimientos de la catedra)
- `spring-boot-devtools`
- Negociacion de contenido explicita con `ContentNegotiationConfigurer`
- `@PutMapping` / `@PathVariable` / `produces` en algunos controllers

---

## Como Ejecutar

### Prerrequisitos
- Java 17+
- Maven 3.8+
- SQL Server (local, puerto 1433)
- Node.js 18+ y Angular CLI (para el frontend)

### Orden de Inicio

1. **SQL Server** - Ejecutar scripts `SQLQuery1.sql` a `SQLQuery4.sql` en orden
2. **labellapizza-backend** (puerto 8081):
   ```bash
   cd labellapizza-backend && mvn spring-boot:run
   ```
3. **perukai-backend** (puerto 8082):
   ```bash
   cd perukai-backend && mvn spring-boot:run
   ```
4. **ristorino-backend** (puerto 8080):
   ```bash
   cd ristorino-backend && mvn spring-boot:run
   ```
5. **ristorino-frontend** (puerto 4200):
   ```bash
   cd ristorino-frontend && ng serve
   ```

---

## Estructura de Directorios

```
DAS/
├── labellapizza-backend/
│   ├── pom.xml
│   └── src/main/java/.../labellapizza/
│       ├── LabellapizzaBackendApplication.java
│       ├── config/
│       │   ├── WebConfig.java
│       │   ├── SucursalArgumentResolver.java
│       │   └── RistorinoInterceptor.java
│       ├── controller/
│       │   └── LaBellaPizzaController.java
│       ├── model/
│       │   ├── Sucursal.java
│       │   └── Promocion.java
│       └── repository/
│           ├── SucursalRepository.java
│           └── PromocionRepository.java
│
├── perukai-backend/
│   ├── pom.xml
│   └── src/main/java/.../perukai/
│       ├── PerukaiBackendApplication.java
│       ├── config/
│       │   └── SoapConfig.java
│       ├── endpoint/
│       │   ├── PerukaiEndpoint.java
│       │   └── PerukaiJaxWsEndpoint.java
│       └── soap/
│           ├── ConsultarDisponibilidadRequest.java
│           └── ConsultarDisponibilidadResponse.java
│
├── ristorino-backend/
│   ├── pom.xml
│   └── src/main/java/.../ristorinobackend/
│       ├── RistorinoBackendApplication.java
│       ├── config/
│       │   ├── SecurityConfig.java
│       │   ├── NoSecurityConfig.java
│       │   ├── ContentNegotiationConfig.java
│       │   └── CorsConfig.java
│       ├── controllers/
│       │   ├── AuthController.java
│       │   ├── ReservaController.java
│       │   ├── PortalController.java
│       │   ├── MarketingController.java
│       │   ├── IAController.java
│       │   └── PerukaiClienteController.java
│       ├── service/
│       │   ├── ReservaService.java
│       │   ├── PortalService.java
│       │   ├── MarketingService.java
│       │   └── JerseyRestClientService.java
│       ├── repositories/
│       │   ├── ReservaRepository.java
│       │   ├── ClienteRepository.java
│       │   ├── RestauranteRepository.java
│       │   └── ClickRepository.java
│       ├── model/
│       │   ├── ReservaBean.java
│       │   ├── ClienteBean.java
│       │   └── RestauranteBean.java
│       └── utils/
│           └── SOAPClient.java
│
├── ristorino-frontend/
│   ├── package.json
│   └── src/
│       ├── main.ts
│       └── app/
│           ├── app.module.ts
│           ├── app.component.ts
│           ├── auth/
│           │   ├── auth.module.ts
│           │   ├── auth.service.ts
│           │   ├── login/
│           │   └── registro/
│           ├── portal/
│           │   ├── portal.module.ts
│           │   ├── portal.service.ts
│           │   ├── reserva.service.ts
│           │   ├── restaurante-detail/
│           │   └── mis-reservas/
│           └── core/
│               ├── auth.guard.ts
│               └── error.interceptor.ts
│
├── SQLQuery1.sql          # BD + tablas principales
├── SQLQuery2.sql          # Tabla clicks
├── SQLQuery3.sql          # SP registrar_click
├── SQLQuery4.sql          # SP crear_reserva
├── RESTAURANTE-Logico.pdf # Modelo logico
├── RISTORINO-Logico.pdf   # Modelo logico
├── Componentes no usados proyecto.txt
└── PROYECTO.md            # (este archivo)
```
