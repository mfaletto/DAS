import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

// Módulos de Material
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu'; // Para el menú de usuario

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    RouterLink, 
    MatToolbarModule, 
    MatButtonModule, 
    MatIconModule,
    MatMenuModule
  ],
  templateUrl: './header.html',
  styleUrls: ['./header.css']
})
export class Header {
  // Simulación de estado de usuario
  isLoggedIn = false; 
}