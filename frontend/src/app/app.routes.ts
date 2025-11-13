// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { restauranteResolver } from './resolvers/restaurante.resolver';

export const routes: Routes = [
  {
    path: 'home',
    loadComponent: () => import('./pages/home/home').then(m => m.Home)
  },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {
    path: 'restaurante/:id',
    loadComponent: () => import('./pages/restaurante-detalle/restaurante-detalle').then(m => m.RestauranteDetalle),
    resolve: { restaurante: restauranteResolver }
  },
  {
    path: 'registro',
    loadComponent: () => import('./pages/registro/registro').then(m => m.Registro)
  },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login').then(m => m.Login)
  },
  { path: '**', redirectTo: '/home' }
];