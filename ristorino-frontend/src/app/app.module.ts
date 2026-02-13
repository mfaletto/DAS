import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module'; // Ajustado a guiones
import { App } from './app'; // Ojo: Revisá si este es .app. o -app-

// 1. Importar funciones HTTP modernas y el Token para Interceptores
import { provideHttpClient, withInterceptorsFromDi, HTTP_INTERCEPTORS } from '@angular/common/http';

// 2. Importar el Interceptor (CON GUIONES, como se generó en tu PC)
import { ErrorInterceptor } from './core/interceptors/error-interceptor';

@NgModule({
  declarations: [
    App
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
    // Ya no va HttpClientModule aquí
  ],
  providers: [
    // 3. Configuración nueva para HTTP en Angular 17/18+
    provideHttpClient(withInterceptorsFromDi()),

    // 4. Registro del Interceptor
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    }
  ],
  bootstrap: [App]
})
export class AppModule { }