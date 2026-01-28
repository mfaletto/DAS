# Restaurante 1: La Bella Pizza (API)

Este proyecto es una API REST independiente construida con Spring Boot para recibir notificaciones de clicks en promociones.

## Tecnologías
- Java 17
- Spring Boot 3.5.7
- Maven
- Spring Data JDBC (SQL explícito)
- SQL Server

## Configuración de la Base de Datos

La API utiliza SQL Server. Por defecto, intenta conectarse a `localhost:1433` con la base de datos `restaurante1_db`.

### Crear Base de Datos
Si la base de datos no existe, créela manualmente con el siguiente comando SQL:
```sql
CREATE DATABASE restaurante1_db;
GO
```

Al iniciar la aplicación, el archivo `src/main/resources/schema.sql` creará automáticamente la tabla `dbo.clicks_notificados` si no existe.

## Cómo Correr

Desde la raíz del proyecto (`restaurante1-api`):
```bash
mvn spring-boot:run
```
La API estará disponible en `http://localhost:8081/api/v1`.

## Endpoints

### 1. Ping
`GET /api/v1/ping`
- Verifica que la API esté activa.

### 2. Notificar Click
`POST /api/v1/notificaciones/clicks`
- **Cuerpo (JSON):**
```json
{
  "nroClick": 12345,
  "nroRestaurante": 1,
  "nroIdioma": 1,
  "nroContenido": 50,
  "fechaHoraRegistro": "2026-01-28T12:00:00",
  "nroCliente": 999,
  "costoClick": 0.50,
  "notificado": true
}
```

- **Ejemplo cURL:**
```bash
curl -X POST http://localhost:8081/api/v1/notificaciones/clicks \
     -H "Content-Type: application/json" \
     -d '{
          "nroClick": 12345,
          "nroRestaurante": 1,
          "nroIdioma": 1,
          "nroContenido": 50,
          "fechaHoraRegistro": "2026-01-28T12:00:00",
          "nroCliente": 999,
          "costoClick": 0.50,
          "notificado": true
         }'
```

## Verificación en DB
Para verificar que los datos se están persistiendo, ejecute:
```sql
USE restaurante1_db;
SELECT TOP 10 * FROM dbo.clicks_notificados ORDER BY fecha_hora_recibido DESC;
```
