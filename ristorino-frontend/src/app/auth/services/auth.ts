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

  // Método NUEVO para el Requerimiento 1
  registrar(usuario: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/registro`, usuario);
  }
  
  // Método útil para obtener el usuario guardado desde cualquier componente
  get usuarioActual(): any {
    const userStr = localStorage.getItem('usuario_ristorino');
    return userStr ? JSON.parse(userStr) : null;
  }

  // En auth.service.ts
  estaLogueado(): boolean {
    return !!localStorage.getItem('usuario_ristorino'); // O el nombre de tu clave
  }

  // AGREGAR ESTE MÉTODO:
  getUsuarioActual(): any {
    // 1. Buscamos el texto guardado en la memoria del navegador
    const usuarioJson = localStorage.getItem('usuario_ristorino'); 
    // (Ojo: si usaste otra clave como 'usuario_ristorino' en el login, poné esa acá)

    // 2. Si existe, lo convertimos de Texto a Objeto. Si no, devolvemos null.
    if (usuarioJson) {
      return JSON.parse(usuarioJson);
    }
    return null;
  }
}