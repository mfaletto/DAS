import { Component } from '@angular/core';
import { AuthService } from '../../services/auth'; // Ajustá el nombre si es auth-service
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.html', // Ajustá si es login-component.html
  styleUrls: ['./login.css'],   // Ajustá si es login-component.css
  standalone: false
})
export class LoginComponent {

  correo: string = 'marcos@email.com'; // Pre-cargado para probar rápido
  clave: string = '123456';
  mensajeError: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  iniciarSesion(): void {
    this.authService.login(this.correo, this.clave).subscribe({
      next: (resp) => {
        console.log('Login Exitoso:', resp);
        // Redirigimos al Portal (que haremos en el próximo paso)
        this.router.navigate(['/portal']); 
      },
      error: (err) => {
        // El ErrorInterceptor ya atrapa esto, pero aquí podemos mostrar un mensajito visual
        this.mensajeError = 'Credenciales inválidas';
      }
    });
  }
}