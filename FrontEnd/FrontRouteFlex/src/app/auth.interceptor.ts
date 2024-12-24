import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Verificamos si la solicitud es de login o usuario
  if (req.url.includes('/login') || req.url.includes('/usuario') || req.url.includes('/conductor')) {
    return next(req); // No agregar token en la solicitud de login
  }

  const token = localStorage.getItem('token');
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(req);
};
