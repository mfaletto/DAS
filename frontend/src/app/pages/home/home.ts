// src/app/pages/home/home.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Necesario para *ngFor y *ngIf
import { Router } from '@angular/router'; // Para navegar al detalle

// 1. Importamos el Servicio de API y los Modelos
import { Api } from '../../services/api';
import { Promocion, ClickRequest } from '../../models/promocion.model';

// 2. Importamos los Módulos de Angular Material para el Mockup
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'; // Para el loader

@Component({
  selector: 'app-home',
  standalone: true,
  // 3. Añadimos los módulos al array 'imports'
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class Home implements OnInit { // El nombre de la clase es 'Home'

  // 4. Propiedades para guardar el estado
  promociones: Promocion[] = [];
  cargando: boolean = true;
  error: string | null = null;

  // 5. Inyectamos el Servicio de API y el Router
  constructor(private api: Api, private router: Router) {}

  // 6. ngOnInit se ejecuta cuando el componente carga
  ngOnInit(): void {
    this.cargarPromociones();
  }

  cargarPromociones(): void {
    this.cargando = true; // Mostramos el loader
    this.error = null;

    this.api.getPromociones().subscribe({
      next: (data) => {
        // ¡Éxito! Guardamos los datos de la API
        this.promociones = data;
        this.cargando = false; // Ocultamos el loader
      },
      error: (err) => {
        // Si la API falla (ej: Spring Boot está apagado)
        this.error = "Error al cargar las promociones. Verifica que el backend esté funcionando.";
        this.cargando = false; // Ocultamos el loader
        console.error('Error fetching promociones:', err);
      }
    });
  }

  // 7. Esta es la función clave para el ENTREGABLE (Paso 4.3)
  onPromocionClick(promocion: Promocion): void {
    
    // Preparamos los datos para el POST (monetización)
    const clickData: ClickRequest = {
      nroRestaurante: promocion.id.nroRestaurante,
      nroIdioma: promocion.id.nroIdioma,
      nroContenido: promocion.id.nroContenido
    };

    // A. REQUISITO MONETIZACIÓN: Llama al POST
    this.api.registrarClick(clickData).subscribe({
      next: () => {
        console.log('Clic registrado con éxito en el backend.');
      },
      error: (err) => {
        console.error('Error al registrar el clic:', err);
        // Nota: Seguimos navegando aunque el clic falle (decisión de negocio)
      }
    });

    // B. REQUISITO NAVEGACIÓN: Va a la vista de detalle
    // (Aún no tenemos el ID del restaurante, usaremos el nroRestaurante como ID)
    this.router.navigate(['/restaurante', promocion.id.nroRestaurante]);
  }

  // Función para obtener la URL de la imagen con fallback
  private readonly apiBase = 'http://localhost:8080';

  getImagenUrl(promocion: Promocion): string {
    if (promocion.imagenPromocionalUrl) {
      return `${this.apiBase}${promocion.imagenPromocionalUrl}`;
    }
    return 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?ixlib=rb-4.1.0&...';
  }

  // Manejo de errores al cargar imágenes
  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    // Si falla la carga, usar imagen por defecto
    img.src = 'https://images.unsplash.com/photo-1517248135467-4c7edcad99c9?w=600&h=400&fit=crop&q=80';
  }
}