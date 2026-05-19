import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule, RouterOutlet } from '@angular/router';

// IMPORTAMOS TODOS LOS ÍCONOS SOLICITADOS
import { 
  LucideAngularModule, 
  LayoutDashboard, 
  Users, 
  ShieldAlert, 
  CreditCard, 
  Dumbbell, 
  Apple, 
  Megaphone, 
  MessageSquare, 
  DollarSign, 
  LogOut 
} from 'lucide-angular';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule, 
    RouterOutlet, 
    RouterModule, 
    LucideAngularModule
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.scss']
})
export class AppComponent implements OnInit {
  adminNombre: string = 'Admin Inicial';
  adminEmail: string = 'admin@gym.com';
  isMenuOpen: boolean = false;

  // MAPEAMOS CADA ÍCONO CON SU NOMBRE PROFESIONAL
  readonly icons = {
    LayoutDashboard,
    Users,
    ShieldAlert,
    CreditCard,
    Dumbbell,
    Apple,
    Megaphone,
    MessageSquare,
    DollarSign,
    LogOut
  };

  private router = inject(Router);

  ngOnInit(): void {
    const nombreGuardado = localStorage.getItem('nombre');
    const emailGuardado = localStorage.getItem('email');
    if (nombreGuardado) this.adminNombre = nombreGuardado;
    if (emailGuardado) this.adminEmail = emailGuardado;
  }

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  isAdminRoute(): boolean {
    return this.router.url.startsWith('/admin');
  }

  // AGREGAMOS LOS TÍTULOS DE LAS NUEVAS SECCIONES EN EL NAVBAR
  getSectionTitle(): string {
    const currentUrl = this.router.url;
    if (currentUrl.includes('/admin/dashboard')) return 'Menu - Inicio';
    if (currentUrl.includes('/admin/usuarios')) return 'Menu - Gestión de Usuarios';
    if (currentUrl.includes('/admin/administradores')) return 'Menu - Gestión de Admins';
    if (currentUrl.includes('/admin/planes')) return 'Menu - Rutinas de Entrenamiento';
    if (currentUrl.includes('/admin/dietas')) return 'Menu - Plan de alimentación';
    if (currentUrl.includes('/admin/avisos')) return 'Menu - Gestión de Avisos';
    if (currentUrl.includes('/admin/chats')) return 'Menu - Chats';
    if (currentUrl.includes('/admin/pagos')) return 'Menu - Gestión de Pagos';
    return 'Menu - Panel';
  }

  logout(): void {
    localStorage.clear();
    this.isMenuOpen = false;
    this.router.navigate(['/login']);
  }
}