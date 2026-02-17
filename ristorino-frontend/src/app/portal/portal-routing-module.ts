import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Portal } from './portal'; // La pantalla principal
import { RestauranteDetail } from './components/restaurante-detail/restaurante-detail'; // Ajustar
import { availabilityResolver } from './resolvers/availability-resolver'; // Importar el resolver
// 1. IMPORTAR EL COMPONENTE NUEVO
import { MisReservasComponent } from './components/mis-reservas/mis-reservas';
// 2. IMPORTAR EL GUARDIA
import { authGuard } from '../core/guards/auth-guard';

const routes: Routes = [
  { path: '', component: Portal }, // La home del portal
  { 
    path: 'restaurante/:id', // URL con parámetro
    component: RestauranteDetail,
    resolve: {
      datosDisponibilidad: availabilityResolver // <--- AQUÍ SE APLICA EL RESOLVER
    }
  },
  { 
    path: 'mis-reservas', 
    component: MisReservasComponent, 
    canActivate: [authGuard] // <--- Ahora sí funciona porque lo importamos arriba
  }
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PortalRoutingModule { }