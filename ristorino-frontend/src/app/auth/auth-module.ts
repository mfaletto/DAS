import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Importante para [(ngModel)]

import { AuthRoutingModule } from './auth-routing-module';
import { LoginComponent } from './components/login/login';

// NO importes 'Auth' (el servicio) aquí para declararlo.

@NgModule({
  declarations: [
    LoginComponent // Solo componentes aquí
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    FormsModule
  ]
})
export class AuthModule { }