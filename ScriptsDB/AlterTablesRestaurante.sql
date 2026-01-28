USE restaurantes_ristorino;
GO

-- Soporte para notificaciones en clicks_contenidos
ALTER TABLE dbo.clicks_contenidos
ADD notificado BIT NOT NULL DEFAULT 0,
    fecha_hora_notificacion DATETIME2 NULL;
GO

-- Soporte para autenticación en clientes (si no existe)
IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.clientes') AND name = 'clave_hash')
BEGIN
    ALTER TABLE dbo.clientes ADD clave_hash VARCHAR(255) NULL;
END
GO
