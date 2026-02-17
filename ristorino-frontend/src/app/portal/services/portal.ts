import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PortalService {

  // URL base para el Portal (Consultas)
  private apiUrlPortal = 'http://localhost:8080/api/portal';
  // URL base para Reservas (Guardar)
  private apiUrlReservas = 'http://localhost:8080/api/reservas';

  constructor(private http: HttpClient) { }

  // 1. Obtener Disponibilidad (Ya lo tenías)
  obtenerDisponibilidad(idRestaurante: number, criterios: any): Observable<any> {
    const body = {
      idRestaurante: idRestaurante,
      criterios: criterios
    };
    return this.http.post(`${this.apiUrlPortal}/disponibilidad`, body);
  }

  // 2. NUEVO: Guardar Reserva (Lo conectamos al endpoint del Backend)
  confirmarReserva(reserva: any): Observable<any> {
    return this.http.post(`${this.apiUrlReservas}/crear`, reserva);
  }

  // 3. NUEVO: Listar Mis Reservas (Para el req 16)
  obtenerMisReservas(idCliente: number): Observable<any> {
    return this.http.get(`${this.apiUrlReservas}/usuario/${idCliente}`);
  }
}