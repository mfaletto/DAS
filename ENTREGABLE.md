# Entregable - Portal Ristorino

## Resumen de Implementación

Este documento describe la implementación del portal Ristorino según los requisitos del entregable.

## ✅ Requisitos Cumplidos

### 1. Versión Funcional del Portal
- ✅ **Frontend**: Aplicación Angular 20.3.0 con diseño moderno y responsive
- ✅ **Backend**: API REST en Spring Boot 3.5.7
- ✅ **Integración**: Comunicación efectiva entre cliente y servidor mediante HTTP/REST

### 2. Página de Inicio
- ✅ **Visualización de Promociones**: La página home muestra todas las promociones obtenidas desde el backend
- ✅ **Diseño Moderno**: Hero section, cards animadas, efectos visuales
- ✅ **Endpoint**: `GET /api/v1/promociones`

### 3. Vista de Detalle del Restaurante
- ✅ **Navegación**: Al hacer clic en una promoción, se navega a la vista de detalle
- ✅ **Información del Restaurante**: Muestra datos del restaurante obtenidos del backend
- ✅ **Endpoint**: `GET /api/v1/restaurantes/{id}`

### 4. Registro de Clics para Monetización
- ✅ **Registro Automático**: Al hacer clic en una promoción, se registra en la base de datos
- ✅ **Sin Notificaciones Inmediatas**: Los clics se guardan con `notificado = false`
- ✅ **Endpoint**: `POST /api/v1/promociones/clic`
- ✅ **Tabla**: `clicks_contenidos_restaurantes`

### 5. Proceso Independiente de Notificación
- ✅ **Proceso Programado**: Tarea que se ejecuta cada 30 segundos automáticamente
- ✅ **Integración REST**: Notifica a servicios REST de restaurantes (simulado)
- ✅ **Marcado de Notificados**: Actualiza el campo `notificado = true` después de notificar exitosamente
- ✅ **Logging**: Registro completo de todas las operaciones

## Arquitectura de la Solución

### Frontend (Angular)
```
frontend/
├── src/app/
│   ├── pages/
│   │   ├── home/              # Página principal con promociones
│   │   ├── login/              # Autenticación
│   │   ├── registro/           # Registro de usuarios
│   │   └── restaurante-detalle/ # Detalle del restaurante
│   ├── components/
│   │   └── layout/header/      # Header con navegación
│   ├── services/
│   │   └── api.ts              # Servicio HTTP para comunicación con backend
│   └── models/
│       └── promocion.model.ts # Modelos de datos
```

### Backend (Spring Boot)
```
backend/src/main/java/ar/edu/ubp/das/ristorinobackend/
├── controller/
│   ├── PromocionController.java        # Endpoints de promociones
│   ├── RestauranteController.java      # Endpoints de restaurantes
│   ├── NotificacionController.java     # Control de proceso de notificación
│   └── RestauranteMockController.java  # Servicio REST simulado de restaurante
├── service/
│   ├── PromocionService.java           # Lógica de negocio de promociones
│   ├── RestauranteService.java         # Lógica de negocio de restaurantes
│   ├── ProcesoNotificacionService.java # Proceso programado de notificación
│   └── RestauranteNotificacionService.java # Servicio de notificación REST
├── repository/
│   └── ClicksContenidosRepository.java # Repositorio con query para clics pendientes
└── entity/
    └── ClicksContenidosRestaurantes.java # Entidad con campo 'notificado'
```

## Flujo de Monetización

### 1. Usuario hace clic en promoción
```
Frontend (home.ts) 
  → onPromocionClick()
  → API.registrarClick()
  → POST /api/v1/promociones/clic
```

### 2. Backend registra el clic
```
PromocionController.registrarClic()
  → PromocionService.registrarClic()
  → Guarda en BD con notificado = false
  → Navega a vista de detalle
```

### 3. Proceso programado notifica (cada 30 segundos)
```
@Scheduled(fixedDelay = 30000)
ProcesoNotificacionService.procesarClicsPendientes()
  → Busca clics con notificado = false
  → Para cada clic:
    → RestauranteNotificacionService.notificarRestaurante()
    → Llama a servicio REST del restaurante
    → Si exitoso: marca notificado = true
```

## Endpoints Implementados

### Frontend → Backend
- `GET /api/v1/promociones` - Obtener todas las promociones
- `POST /api/v1/promociones/clic` - Registrar clic de monetización
- `GET /api/v1/restaurantes/{id}` - Obtener detalle del restaurante
- `POST /api/v1/auth/register` - Registrar nuevo usuario

### Backend → Restaurante (Mock)
- `POST /api/v1/api/notificaciones/click` - Recibir notificación de clic

### Control y Monitoreo
- `POST /api/v1/notificaciones/procesar` - Ejecutar proceso de notificación manualmente

## Buenas Prácticas Aplicadas

### Angular
- ✅ Componentes standalone
- ✅ Servicios inyectables
- ✅ TypeScript con tipado fuerte
- ✅ Manejo de errores con try/catch
- ✅ Observables de RxJS para operaciones asíncronas
- ✅ Separación de responsabilidades (services, components, models)
- ✅ Diseño responsive y accesible

### Spring Boot
- ✅ Arquitectura en capas (Controller → Service → Repository)
- ✅ Inyección de dependencias
- ✅ Manejo de excepciones
- ✅ Logging con SLF4J
- ✅ Tareas programadas con @Scheduled
- ✅ Integración REST con RestTemplate
- ✅ JPA/Hibernate para persistencia

## Base de Datos

### Tablas Utilizadas
- `contenidos_restaurantes` - Almacena las promociones
- `clicks_contenidos_restaurantes` - Registra los clics con campo `notificado`
- `restaurantes` - Información de restaurantes
- `clientes` - Usuarios del sistema

### Query Clave
```sql
SELECT * FROM clicks_contenidos_restaurantes 
WHERE notificado = false
```

## Cómo Probar el Entregable

### 1. Iniciar Backend
```bash
cd backend
./mvnw spring-boot:run
```

### 2. Iniciar Frontend
```bash
cd frontend
npm start
```

### 3. Probar el Flujo
1. Abrir `http://localhost:4200`
2. Ver promociones en la página home
3. Hacer clic en una promoción
4. Verificar en logs del backend que se registró el clic
5. Esperar 30 segundos (o ejecutar manualmente `POST /api/v1/notificaciones/procesar`)
6. Verificar en logs que se notificó al restaurante
7. Verificar en BD que `notificado = true`

### 4. Verificar Notificaciones
- Revisar logs del backend para ver las notificaciones
- El `RestauranteMockController` imprime en consola cada notificación recibida

## Notas Técnicas

### Proceso de Notificación
- Se ejecuta automáticamente cada 30 segundos
- Puede ejecutarse manualmente mediante endpoint
- Si falla la notificación, el clic queda pendiente para reintento
- Los logs muestran el estado de cada operación

### Servicio REST Simulado
- Para el entregable, el servicio del restaurante está en el mismo backend
- En producción, cada restaurante tendría su propio servidor
- El mock recibe notificaciones y las registra en logs

## Defensa del Entregable

### Puntos Clave a Destacar
1. **Integración Completa**: Frontend y backend comunicándose correctamente
2. **Proceso Asíncrono**: Notificaciones sin bloquear la experiencia del usuario
3. **Arquitectura Escalable**: Separación de responsabilidades y buenas prácticas
4. **Monitoreo**: Logging completo para debugging y auditoría
5. **Extensibilidad**: Fácil agregar más restaurantes o cambiar el método de notificación

### Mejoras Futuras
- Implementar notificaciones SOAP además de REST
- Agregar reintentos automáticos con backoff exponencial
- Implementar cola de mensajes (RabbitMQ, Kafka)
- Dashboard de monitoreo de notificaciones
- Notificaciones en tiempo real con WebSockets

