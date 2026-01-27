USE ristorinoDB;
GO

CREATE OR ALTER PROCEDURE dbo.reg_click_promocion
    @nro_cliente     INT,
    @nro_restaurante INT,
    @nro_idioma      INT,
    @nro_contenido   INT,
    @costo_click     DECIMAL(12,2),
    @fecha_hora_registro DATETIME2,
    @notificado      BIT
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO dbo.clicks_contenidos_restaurantes
        (nro_cliente, nro_restaurante, nro_idioma, nro_contenido, costo_click, fecha_hora_registro, notificado)
    VALUES
        (@nro_cliente, @nro_restaurante, @nro_idioma, @nro_contenido, @costo_click, @fecha_hora_registro, @notificado);

    SELECT CAST(SCOPE_IDENTITY() AS INT) AS nro_click;
END;
GO
