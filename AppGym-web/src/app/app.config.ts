import { ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http'; // <-- Agregado para habilitar peticiones HTTP

import { routes } from './app.routes';
import { authInterceptor } from './interceptors/auth.interceptor'; // <-- Importamos tu interceptor de JWT

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    
    provideHttpClient(
      withInterceptors([authInterceptor])
    )
  ]
};