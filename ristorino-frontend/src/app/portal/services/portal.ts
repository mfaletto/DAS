import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PortalService {

  private apiUrl = 'http://localhost:8080/api/portal';

  constructor(private http: HttpClient) { }

  // Llama al orquestador Java (que decide si es SOAP o REST)
  obtenerDisponibilidad(idRestaurante: number, criterios: any): Observable<any> {
    const body = {
      idRestaurante: idRestaurante,
      criterios: criterios
    };
    return this.http.post(`${this.apiUrl}/disponibilidad`, body);
  }
}