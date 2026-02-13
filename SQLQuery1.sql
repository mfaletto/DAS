-- 1. Crear la Base de Datos
CREATE DATABASE ristorino_db;
GO

USE ristorino_db;
GO

-- 2. Tabla de Restaurantes (Catálogo de sistemas externos)
-- Sirve para saber a qué URL llamar cuando el usuario elige uno
CREATE TABLE restaurantes (
    nro_restaurante INT PRIMARY KEY IDENTITY(1,1),
    razon_social VARCHAR(100) NOT NULL,
    cuit VARCHAR(20),
    tipo_conexion VARCHAR(10) NOT NULL, -- 'REST' o 'SOAP'
    endpoint_url VARCHAR(255) NOT NULL -- La URL base para llamarlos
);
GO

-- 3. Tabla de Clientes (Usuarios del Portal)
--[cite_start]-- Basado en RISTORINO-Logico.pdf [cite: 1437-1445]
CREATE TABLE clientes (
    nro_cliente INT PRIMARY KEY IDENTITY(1,1),
    apellido VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    clave VARCHAR(100) NOT NULL, -- En producción real iría hasheada
    correo VARCHAR(150) NOT NULL UNIQUE,
    telefonos VARCHAR(100),
    habilitado BIT DEFAULT 1
);
GO

-- 4. Tabla de Reservas (La transacción principal)
--[cite_start]-- Basado en RISTORINO-Logico.pdf [cite: 1414-1428]
CREATE TABLE reservas_restaurantes (
    nro_reserva INT PRIMARY KEY IDENTITY(1,1),
    nro_cliente INT NOT NULL,
    nro_restaurante INT NOT NULL,
    nro_sucursal_externa INT NOT NULL, -- ID que viene del sistema externo
    fecha_hora_registro DATETIME DEFAULT GETDATE(),
    fecha_reserva DATE NOT NULL,
    hora_reserva TIME NOT NULL, -- Simplificado de FK a TIME directo
    cant_adultos INT DEFAULT 1,
    cant_menores INT DEFAULT 0,
    observaciones VARCHAR(255),
    estado VARCHAR(20) DEFAULT 'PENDIENTE', -- Simplificado de cod_estado
    
    CONSTRAINT FK_Reservas_Clientes FOREIGN KEY (nro_cliente) REFERENCES clientes(nro_cliente),
    CONSTRAINT FK_Reservas_Restaurantes FOREIGN KEY (nro_restaurante) REFERENCES restaurantes(nro_restaurante)
);
GO

-- 5. Insertar Datos Iniciales (Seed Data)

-- Los 4 Restaurantes que pide la cátedra
INSERT INTO restaurantes (razon_social, tipo_conexion, endpoint_url) VALUES 
('La Bella Pizza', 'REST', 'http://localhost:8081/api/v1/public'),
('Perukai', 'SOAP', 'http://localhost:8082/ws/'),
('La Fabrica Burger', 'REST', 'http://localhost:8080/mock/lafabricaburger'), -- Apuntamos al mock por ahora
('Sabores del Norte', 'SOAP', 'http://localhost:8080/ws'); -- Apuntamos al mock por ahora
GO

-- Un cliente de prueba
INSERT INTO clientes (apellido, nombre, clave, correo, telefonos, habilitado) VALUES 
('Faletto', 'Marcos', '123456', 'marcos@email.com', '351123456', 1);
GO