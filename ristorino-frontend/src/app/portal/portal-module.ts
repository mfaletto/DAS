import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// 1. IMPORTAR ESTOS DOS
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { PortalRoutingModule } from './portal-routing-module';
import { Portal } from './portal';
import { RestauranteDetail } from './components/restaurante-detail/restaurante-detail';
import { MisReservas } from './components/mis-reservas/mis-reservas';


@NgModule({
  declarations: [
    Portal,
    RestauranteDetail,
    MisReservas
  ],
  imports: [
    CommonModule,
    PortalRoutingModule,
    // 2. AGREGARLOS AQUÍ
    FormsModule,  // <--- Soluciona el error de ngModel
    RouterModule  // <--- Soluciona el error de routerLink (por seguridad)
  ]
})
export class PortalModule { }
