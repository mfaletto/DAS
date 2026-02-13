import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {

  private apiUrl = 'http://localhost:8080/api/reservas';

  constructor(private http: HttpClient) { }

  crearReserva(reserva: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear`, reserva);
  }
}