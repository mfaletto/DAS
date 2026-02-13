import { ResolveFn } from '@angular/router';
import { inject } from '@angular/core';
import { PortalService } from '../services/portal'; // Ajustar nombre
import { catchError, of } from 'rxjs';

export const availabilityResolver: ResolveFn<any> = (route, state) => {
  const portalService = inject(PortalService);
  
  // 1. Capturamos el ID de la URL (ej: /portal/restaurante/1)
  const idRestaurante = Number(route.paramMap.get('id'));
  
  // 2. Definimos criterios por defecto (para el examen)
  const criterios = {
    fecha: '2023-12-01',
    tipoCocina: 'Nikkei' // Para que funcione Perukai
  };

  // 3. Retornamos el Observable. Angular ESPERARÁ a que esto termine
  // antes de mostrar el componente.
  return portalService.obtenerDisponibilidad(idRestaurante, criterios).pipe(
    catchError(error => {
      console.error('Error en resolver', error);
      return of({ error: 'No se pudo cargar la disponibilidad' });
    })
  );
};