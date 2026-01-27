import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoaderService } from '../../core/services/loader.service';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

/**
 * Componente Loader que muestra un spinner de carga.
 * Se muestra automáticamente cuando hay peticiones HTTP en curso
 * gracias al Interceptor HTTP.
 */
@Component({
  selector: 'app-loader',
  standalone: true,
  imports: [CommonModule, MatProgressSpinnerModule],
  template: `
    <div *ngIf="loading$ | async" class="loader-overlay">
      <mat-spinner diameter="50"></mat-spinner>
    </div>
  `,
  styles: [`
    .loader-overlay {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.3);
      display: flex;
      justify-content: center;
      align-items: center;
      z-index: 9999;
    }
  `]
})
export class LoaderComponent {
  private loaderService = inject(LoaderService);
  loading$ = this.loaderService.loading$;
}

