// src/app/app.routes.ts
import { Routes } from '@angular/router';

// 1. CORRECCIÓN: Quitamos ".component" de todas las rutas de importación
import { Home } from './pages/home/home';
import { Registro } from './pages/registro/registro';
import { Login } from './pages/login/login';
import { RestauranteDetalle } from './pages/restaurante-detalle/restaurante-detalle';

// 2. Define el "mapa" de la aplicación (esto ya estaba bien)
export const routes: Routes = [
  { path: 'home', component: Home },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'restaurante/:id', component: RestauranteDetalle },
  { path: 'registro', component: Registro },
  { path: 'login', component: Login },
  { path: '**', redirectTo: '/home' }
];