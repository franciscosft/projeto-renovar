import { HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError(error => {
      let errorObj = error;
      if (errorObj.error) {
        errorObj = errorObj.error;
      }
      console.error('Erro detectado pelo interceptor:', errorObj);
      return throwError(() => errorObj);
    })
  );
};
