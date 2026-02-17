import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from './core/guards/auth-guard'; // <--- 1. Importar el Guard

const routes: Routes = [
  { path: '', redirectTo: 'portal', pathMatch: 'full' },
  { 
    path: 'auth', 
    loadChildren: () => import('./auth/auth-module').then(m => m.AuthModule) 
  },
  { 
    path: 'portal', 
    loadChildren: () => import('./portal/portal-module').then(m => m.PortalModule)
    //canActivate: [authGuard] // <--- 2. ¡ACTIVAR EL ESCUDO AQUÍ!
  },
  // Agregamos la ruta directa al registro por si acaso
  // { path: 'register', component: RegistroComponent } (Esto ya lo maneja auth/register)
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }