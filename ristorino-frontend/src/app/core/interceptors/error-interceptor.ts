import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        let mensajeError = '';
        
        if (error.error instanceof ErrorEvent) {
          // Error del lado del cliente (ej: red)
          mensajeError = `Error Cliente: ${error.error.message}`;
        } else {
          // Error del lado del servidor (ej: 404, 500)
          mensajeError = `Error Servidor (Código ${error.status}): ${error.message}`;
          // Aquí podrías redirigir al Login si es un 401
          if (error.status === 401) {
             console.warn('Usuario no autorizado. Redirigir a login...');
          }
        }

        console.error('🚨 Interceptor atrapó un error:', mensajeError);
        // alert(mensajeError); // Descomentar si querés que salte un popup molesto
        return throwError(() => new Error(mensajeError));
      })
    );
  }
}