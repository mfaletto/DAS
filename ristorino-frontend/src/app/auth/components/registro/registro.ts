import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth'; // Asegurate que la ruta sea correcta

@Component({
  selector: 'app-registro',
  templateUrl: './registro.html',
  styleUrls: ['./registro.css'],
  standalone: false
})
export class RegistroComponent {
  // ... tu lógica del componente ...
    usuario = {
    nombre: '',
    apellido: '',
    email: '',
    password: '',
    telefono: ''
  };
  mensajeError: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  registrar() {
    // Mapeamos los datos para que coincidan EXACTO con el ClienteBean de Java
    const clienteParaBackend = {
      nombre: this.usuario.nombre,
      apellido: this.usuario.apellido,
      correo: this.usuario.email,     // Angular 'email' -> Java 'correo'
      clave: this.usuario.password,   // Angular 'password' -> Java 'clave'
      telefonos: this.usuario.telefono
    };

    this.authService.registrar(clienteParaBackend).subscribe({
      next: (res) => {
        alert('¡Registro exitoso! Ahora podés iniciar sesión.');
        this.router.navigate(['/auth/login']);
      },
      error: (err) => {
        console.error(err);
        this.mensajeError = err.error?.mensaje || 'Error al registrarse. Intente nuevamente.';
      }
    });
  }
}