import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // URL de tu Backend Java (Ristorino)
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient, private router: Router) { }

  login(correo: string, clave: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { correo, clave }).pipe(
      tap((usuario: any) => {
        // Guardamos al usuario en el navegador para saber que está logueado
        localStorage.setItem('usuario_ristorino', JSON.stringify(usuario));
      })
    );
  }

  logout(): void {
    localStorage.removeItem('usuario_ristorino');
    this.router.navigate(['/auth/login']);
  }

  // Método útil para obtener el usuario guardado desde cualquier componente
  get usuarioActual(): any {
    const userStr = localStorage.getItem('usuario_ristorino');
    return userStr ? JSON.parse(userStr) : null;
  }
}