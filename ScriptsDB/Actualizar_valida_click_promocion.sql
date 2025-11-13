USE ristorinoDB;
GO

CREATE OR ALTER PROCEDURE dbo.valida_click_promocion
    @nro_cliente     INT,
    @nro_restaurante INT,
    @nro_idioma      INT,
    @nro_contenido   INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        CASE
            WHEN EXISTS (
                SELECT 1
                FROM dbo.clicks_contenidos_restaurantes ccr
                WHERE ccr.nro_cliente = @nro_cliente
                  AND ccr.nro_restaurante = @nro_restaurante
                  AND ccr.nro_idioma = @nro_idioma
                  AND ccr.nro_contenido = @nro_contenido
                  AND ccr.fecha_hora_registro >= DATEADD(MINUTE, -5, SYSDATETIME())
            ) THEN CAST(1 AS BIT)
            ELSE CAST(0 AS BIT)
        END AS existe_click;
END;
GO
