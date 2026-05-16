import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

// IMPORTAMOS LUCIDE Y SUS ÍCONOS
import { 
  LucideAngularModule, 
  Users, 
  ShieldAlert, 
  MessageSquare, 
  CreditCard, 
  Dumbbell, 
  Apple, 
  Megaphone 
} from 'lucide-angular';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {
  adminNombre: string = 'Admin Inicial';
  adminEmail: string = 'admin@gym.com';
  
  isMenuOpen: boolean = false;

  // DECLARAMOS LOS ÍCONOS MAPEADOS
  readonly icons = {
    Users,
    ShieldAlert,
    MessageSquare,
    CreditCard,
    Dumbbell,
    Apple,
    Megaphone
  };

  stats = {
    sociosActivos: 124,
    pagosPendientes: 8,
    chatsSinResponder: 3
  };

  constructor(private router: Router) {}

  ngOnInit(): void {
    const nombreGuardado = localStorage.getItem('nombre');
    const emailGuardado = localStorage.getItem('email');
    
    if (nombreGuardado) this.adminNombre = nombreGuardado;
    if (emailGuardado) this.adminEmail = emailGuardado;
  }

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}