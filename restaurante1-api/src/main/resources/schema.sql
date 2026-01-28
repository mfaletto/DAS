-- Nota: La base de datos 'restaurante1_db' debe existir previamente o ser creada manualmente.
-- SQL Server no permite CREATE DATABASE dentro de una transacción o script simple de inicialización en algunos contextos de Spring Boot.

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[clicks_notificados]') AND type in (N'U'))
BEGIN
    CREATE TABLE dbo.clicks_notificados (
        id INT IDENTITY(1,1) PRIMARY KEY,
        nro_click INT NOT NULL,
        nro_restaurante INT NOT NULL,
        nro_idioma INT NOT NULL,
        nro_contenido INT NOT NULL,
        fecha_hora_registro DATETIME2 NOT NULL,
        nro_cliente INT NULL,
        costo_click DECIMAL(10,2) NULL,
        notificado BIT NOT NULL,
        fecha_hora_recibido DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
        CONSTRAINT UQ_clicks_notificados_nro_click UNIQUE (nro_click)
    );
END
