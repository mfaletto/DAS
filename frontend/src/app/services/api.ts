// src/app/services/api.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Importamos los "moldes" que creamos en la Fase 3
import { Promocion, ClickRequest, RegistroRequest } from '../models/promocion.model';

@Injectable({
  providedIn: 'root'
})
export class Api { // El nombre de la clase es 'Api'

  // La URL base de tu API de Spring Boot (la que probamos en el navegador)
  private apiUrl = 'http://localhost:8080/api/v1';

  // 1. Inyectamos el HttpClient (que configuramos en app.config.ts)
  constructor(private http: HttpClient) { }

  /**
   * FASE 4 - REQUISITO 1: Visualizar promociones
   * Llama a: GET /api/v1/promociones
   */
  getPromociones(): Observable<Promocion[]> {
    return this.http.get<Promocion[]>(`${this.apiUrl}/promociones`);
  }

  /**
   * FASE 4 - REQUISITO 2: Registrar clic de monetización
   * Llama a: POST /api/v1/promociones/clic
   */
  registrarClick(clickData: ClickRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/promociones/clic`, clickData);
  }

  /**
     * FASE 4 - REQUISITO 3: Obtener detalles del restaurante
     * Llama a: GET /api/v1/restaurantes/{id}
     */
    getRestauranteDetalle(id: number): Observable<any> { // Usamos 'any' por simplicidad
        return this.http.get<any>(`${this.apiUrl}/restaurantes/${id}`);
    }

  /**
   * FASE 4 - NUEVO REQUISITO: Dar de alta usuario (RF 1)
   * Llama a: POST /api/v1/auth/register
   */
  registrarUsuario(data: RegistroRequest): Observable<any> {
    const url = `${this.apiUrl}/auth/register`;
    // El backend espera: { nombre, correo, clave, etc. }
    return this.http.post(url, data);
  }
}