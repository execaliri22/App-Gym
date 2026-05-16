import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string = '';

  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  constructor() {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4)]]
    });
  }

onSubmit(): void {
    if (this.loginForm.invalid) {
      const emailControl = this.loginForm.get('email');
      const passwordControl = this.loginForm.get('password');

      if (emailControl?.invalid) {
        this.errorMessage = 'Por favor, ingresa un formato de correo electrónico válido (ejemplo@gym.com).';
      } else if (passwordControl?.invalid) {
        this.errorMessage = 'La contraseña debe tener un mínimo de 4 caracteres.';
      } else {
        this.errorMessage = 'Por favor, completa todos los campos correctamente.';
      }
      return; 
    }

    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
        console.log('Login exitoso, token guardado:', response);
        
        if (response.email === 'admin@gym.com') {
          this.router.navigate(['/admin/dashboard']); // Si es el admin, va a su panel
        } else {
          this.router.navigate(['/socio/dashboard']); // Si es cualquier otro socio, va al suyo
        }
      },
      error: (err) => {
        this.errorMessage = err.error?.message || err.error || 'Credenciales inválidas. Inténtalo de nuevo.';
      }
    });
  }
}