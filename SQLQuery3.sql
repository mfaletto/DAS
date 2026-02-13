USE ristorino_db;
GO

-- Si ya existía, lo borramos para crearlo de nuevo
IF OBJECT_ID('sp_registrar_click', 'P') IS NOT NULL
    DROP PROCEDURE sp_registrar_click;
GO

CREATE PROCEDURE sp_registrar_click
    @nro_restaurante INT,
    @nro_cliente INT,
    @titulo_contenido VARCHAR(100)
AS
BEGIN
    -- Buenas prácticas: No retornar el conteo de filas afectadas (mejora performance)
    SET NOCOUNT ON;

    -- Insertamos el clic usando los valores por defecto para fecha y costo
    INSERT INTO clicks_contenidos (nro_restaurante, nro_cliente, titulo_contenido)
    VALUES (@nro_restaurante, @nro_cliente, @titulo_contenido);
END;
GO