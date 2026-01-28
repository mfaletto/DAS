import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { Api } from '../services/api';
import { Observable } from 'rxjs';

/**
 * Resolver que precarga los datos del restaurante antes de activar la ruta.
 * 
 * Este resolver se usa en app.routes.ts para garantizar que los datos
 * estén disponibles cuando el componente RestauranteDetalle se carga.
 * 
 * @param route Información de la ruta activa
 * @param state Estado del router
 * @returns Observable con los datos del restaurante
 */
export const restauranteResolver: ResolveFn<any> = (route, state) => {
  const api = inject(Api);
  const restauranteId = Number(route.paramMap.get('id'));
  
  // Retorna un Observable que el router esperará antes de activar la ruta
  return api.getRestauranteDetalle(restauranteId);
};

