// src/app/app.config.ts
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';

// 1. IMPORTAR Animaciones
import { provideAnimations } from '@angular/platform-browser/animations';

// 2. IMPORTAR el Cliente HTTP
import { provideHttpClient } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    
    // 3. AÑADIR ambos proveedores
    provideAnimations(),
    provideHttpClient() 
  ]
};