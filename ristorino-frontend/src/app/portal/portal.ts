import { Component } from '@angular/core';
import { Router } from '@angular/router';

// 1. CORRECCIÓN DE RUTAS (Basado en tu foto)
// Salimos de 'portal' (..) -> entramos a 'auth' -> 'services' -> 'auth'
import { AuthService } from '../auth/services/auth'; 

// Importamos el servicio del portal (asumiendo que está en la carpeta 'services' al lado de este archivo)
import { PortalService } from './services/portal'; // Ojo: verificá si tu archivo se llama 'portal.ts' o 'portal.service.ts'

@Component({
  selector: 'app-portal',
  standalone: false,
  templateUrl: './portal.html',
  styleUrl: './portal.css', // Ojo: Angular suele usar 'styleUrls' (plural/array), pero en versiones nuevas 'styleUrl' (singular) funciona.
})
export class Portal {
  
  constructor(
    private portalService: PortalService, // Ahora sí está importado arriba
    private authService: AuthService,     // Ahora sí está importado arriba
    private router: Router
  ) {}

  confirmarReserva() {
  // 1. VALIDACIÓN DE SEGURIDAD (REQ 15)
  if (!this.authService.estaLogueado()) { // O como se llame tu método, ej: getToken()
    alert('Para confirmar una reserva, necesitás iniciar sesión.');
    this.router.navigate(['/auth/login']);
    return; // Detiene la ejecución aquí
  }

}
}