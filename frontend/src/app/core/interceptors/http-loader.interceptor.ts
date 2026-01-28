import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, finalize, throwError } from 'rxjs';
import { LoaderService } from '../services/loader.service';

export const httpLoaderInterceptor: HttpInterceptorFn = (req, next) => {
  const loaderService = inject(LoaderService);

  loaderService.show();

  const token = localStorage.getItem('ristorino_token');
  const authReq = token
    ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
    : req;

  return next(authReq).pipe(
    finalize(() => loaderService.hide()),
    catchError((error: HttpErrorResponse) => {
      console.error('Error HTTP:', error);
      return throwError(() => error);
    })
  );
};
