import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Intentamos obtener el token JWT que guardó el AuthService desde el localStorage
  const token = localStorage.getItem('token');

  // Si el token existe, clonamos la petición y le añadimos la cabecera Authorization
  if (token) {
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    // Pasamos la petición clonada con el token al siguiente paso
    return next(clonedRequest);
  }

  // Si no hay token (como en el Login o Registro), la petición sigue su curso normal
  return next(req);
};