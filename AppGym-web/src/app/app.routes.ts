import { Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { AdminPagoComponent } from './components/admin/admin-pago/admin-pago.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  
  // RUTA DEL DASHBOARD DE ADMINISTRADOR
  { path: 'admin/dashboard', component: AdminDashboardComponent },
  
  { path: 'admin/pagos', component: AdminPagoComponent },
  // Redirección de la raíz al login
  { path: '', redirectTo: 'login', pathMatch: 'full' }, 
  
  // Ruta comodín por si escriben cualquier otra URL inválida
  { path: '**', redirectTo: 'login' },
];