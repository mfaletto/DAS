// src/app/models/promocion.model.ts

/**
 * Esta es la interfaz para la Clave Compuesta (el objeto "id" anidado)
 * que vimos en tu JSON de la API.
 */
export interface PromocionId {
  nroRestaurante: number;
  nroIdioma: number;
  nroContenido: number;
}

/**
 * Interfaz principal (el "molde") para la Promoción.
 * Coincide exactamente con el JSON que devuelve tu API
 * (GET /api/v1/promociones).
 */
export interface Promocion {
  id: PromocionId; // El ID es un objeto anidado
  contenidoPromocional: string;
  imagenPromocionalUrl: string;
  imagenPromocionalHash: string;
  fechaIniVigencia: string; // JSON convierte las fechas a strings
  fechaFinVigencia: string | null; // Puede ser nulo
  costoClick: number;
}

/**
 * Interfaz (DTO) para los datos que ENVIAMOS al backend
 * al registrar un clic.
 * (POST /api/v1/promociones/clic)
 */
export interface ClickRequest {
  nroRestaurante: number;
  nroIdioma: number;
  nroContenido: number;
  nroCliente?: number;
}

/**
 * Interfaz (DTO) para enviar los datos de registro de usuario (RF 1)
 * Debe coincidir con el DTO del Backend (RegistroRequest.java)
 */
export interface RegistroRequest {
  nombre: string;
  apellido: string;
  correo: string;
  clave: string;
  telefonos: string;
  // Omitimos preferencias y dirección por simplicidad de conexión inicial
}

export interface LoginRequest {
  correo: string;
  clave: string;
}

export interface LoginResponse {
  token: string;
  tokenType: string;
  expiresIn: number;
}