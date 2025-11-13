// src/app/app.config.ts
import { ApplicationConfig, ErrorHandler } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';

// 1. IMPORTAR Animaciones
import { provideAnimations } from '@angular/platform-browser/animations';

// 2. IMPORTAR el Cliente HTTP con Interceptor
import { provideHttpClient, withInterceptors } from '@angular/common/http';

// 3. IMPORTAR Interceptor y Error Handler
import { httpLoaderInterceptor } from './core/interceptors/http-loader.interceptor';
import { GlobalErrorHandler } from './core/error/global-error-handler';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    
    // 4. AÑADIR proveedores
    provideAnimations(),
    
    // HTTP Client con Interceptor
    provideHttpClient(
      withInterceptors([httpLoaderInterceptor])
    ),
    
    // Error Handler global
    { provide: ErrorHandler, useClass: GlobalErrorHandler }
  ]
};