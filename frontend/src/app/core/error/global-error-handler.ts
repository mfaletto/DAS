import { ErrorHandler, Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {
  handleError(error: Error | HttpErrorResponse): void {
    if (error instanceof HttpErrorResponse) {
      console.error('Error HTTP:', {
        status: error.status,
        statusText: error.statusText,
        message: error.message,
        url: error.url
      });
      return;
    }

    console.error('Error de JavaScript:', {
      message: error.message,
      stack: error.stack
    });
  }
}
