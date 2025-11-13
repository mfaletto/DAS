// src/app/pages/restaurante-detalle/restaurante-detalle.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router'; // ⬅️ Para leer el ID de la URL y navegar
import { Api } from '../../services/api';
// ... (imports de Material necesarios) ...

// ⬅️ Importar los módulos de Material para el formulario
import { MatTabsModule } from '@angular/material/tabs'; // Para las pestañas (Description, Menu, etc.)
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon'; // Para el icono de flecha

@Component({
  selector: 'app-restaurante-detalle',
  standalone: true,
  imports: [CommonModule, 
    // Añadir todos los módulos de Material
    MatTabsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './restaurante-detalle.html',
  styleUrls: ['./restaurante-detalle.css']
})
export class RestauranteDetalle implements OnInit {
  restauranteId: number = 0;
  restaurante: any = null; // Usamos 'any' por ahora, idealmente usar una Interfaz
  cargando: boolean = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute, // Para acceder a la URL
    private router: Router, // Para navegar
    private api: Api
  ) {}

  ngOnInit(): void {
    // 1. Intentar obtener datos del Resolver (precargados)
    const restauranteResuelto = this.route.snapshot.data['restaurante'];
    
    if (restauranteResuelto) {
      // Los datos ya vienen del Resolver
      this.restaurante = restauranteResuelto;
      this.restauranteId = this.restaurante.nroRestaurante;
      this.cargando = false;
      this.error = null;
    } else {
      // Fallback: cargar manualmente si el resolver no funcionó
      this.route.paramMap.subscribe(params => {
        this.restauranteId = Number(params.get('id'));
        this.cargarDetalles();
      });
    }
  }

  cargarDetalles(): void {
    this.cargando = true;
    this.api.getRestauranteDetalle(this.restauranteId).subscribe({
      next: (data) => {
        this.restaurante = data;
        this.cargando = false;
        this.error = null;
      },
      error: (err) => {
        this.error = `Error al cargar el restaurante ${this.restauranteId}. Asegúrate que el restaurante exista en la DB.`;
        this.cargando = false;
        console.error('Error:', err);
      }
    });
  }

  // Método para volver al home
  volverAlHome(): void {
    this.router.navigate(['/']);
  }
}