USE ristorino_db;
GO

IF OBJECT_ID('sp_crear_reserva', 'P') IS NOT NULL
    DROP PROCEDURE sp_crear_reserva;
GO

CREATE PROCEDURE sp_crear_reserva
    @nro_cliente INT,
    @nro_restaurante INT,
    @fecha_reserva DATE,
    @hora_reserva TIME,
    @cant_adultos INT,
    @cant_menores INT,
    @observaciones VARCHAR(255),
    @id_reserva_generado INT OUTPUT -- Parámetro de salida para devolver el ID
AS
BEGIN
    SET NOCOUNT ON;

    -- Insertamos la reserva
    INSERT INTO reservas_restaurantes (
        nro_cliente, nro_restaurante, nro_sucursal_externa, 
        fecha_reserva, hora_reserva, cant_adultos, cant_menores, observaciones, estado
    )
    VALUES (
        @nro_cliente, @nro_restaurante, 0, -- 0 porque no estamos manejando sucursales externas en detalle aún
        @fecha_reserva, @hora_reserva, @cant_adultos, @cant_menores, @observaciones, 'CONFIRMADA'
    );

    -- Capturamos el ID generado
    SET @id_reserva_generado = SCOPE_IDENTITY();
END;
GO