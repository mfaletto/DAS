import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // <--- Agregalo si usas ngModel
import { RouterModule } from '@angular/router'; // <--- Agregalo si usas routerLink

import { PortalRoutingModule } from './portal-routing-module';
import { Portal } from './portal'; // Asumo que tu clase se llama 'Portal'
import { RestauranteDetail } from './components/restaurante-detail/restaurante-detail';
import { MisReservasComponent } from './components/mis-reservas/mis-reservas'; // Chequeá la ruta

@NgModule({
  declarations: [
    // ACÁ SOLO VAN TUS COMPONENTES
    Portal,
    RestauranteDetail,
    MisReservasComponent // <--- CORREGIDO (Tenías "MisReservas")
  ],
  imports: [
    // ACÁ VAN LOS MÓDULOS DE ANGULAR
    CommonModule, // <--- Este es el que arregla el error del Pipe 'date'
    PortalRoutingModule,
    FormsModule,
    RouterModule
  ]
})
export class PortalModule { }