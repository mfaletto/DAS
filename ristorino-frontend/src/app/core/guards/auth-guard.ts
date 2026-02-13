import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../../auth/services/auth'; // Ajustá la ruta si es necesario

export const authGuard: CanActivateFn = (route, state) => {
  // Inyección de dependencias "moderna" (Angular 16+)
  const authService = inject(AuthService);
  const router = inject(Router);

  // Verificamos si hay usuario (usando el getter que ya creamos antes)
  if (authService.usuarioActual) {
    return true; // ¡Pase, señor!
  } else {
    // Si no hay nadie, lo mandamos al login
    console.warn('👮‍♂️ AuthGuard: Acceso denegado. Redirigiendo al Login...');
    router.navigate(['/auth/login']);
    return false;
  }
};