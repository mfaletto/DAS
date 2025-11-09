// src/app/pages/login/login.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms'; 

// Módulos de Material (Necesarios para el diseño de la tarjeta)
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

// Servicio y Modelos (Aunque la lógica de Login es trivial por ahora)
import { Api } from '../../services/api'; 
// import { LoginRequest } from '../../models/auth.model'; // Se crearía un modelo para login

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,  
    RouterLink, // Para usar el [routerLink] en el HTML
    FormsModule, 
    MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule
  ],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class Login implements OnInit { // ⬅️ Nombre de la clase: Login
    
    // Modelo de datos para el Two-Way Binding
    loginData = {
        correo: '',
        clave: ''
    };
    
    cargando = false;
    errorLogin: string | null = null;
    
    constructor(private api: Api, private router: Router) { }

    ngOnInit(): void { }

    // Función para simular el inicio de sesión (RF 2)
    onSubmitLogin(): void {
        this.cargando = true;
        this.errorLogin = null;
        
        // ⚠️ En un proyecto real, aquí se llamaría a this.api.iniciarSesion(this.loginData)
        
        console.log("Simulando inicio de sesión para:", this.loginData.correo);
        
        // Simulación de éxito (solo para pruebas)
        setTimeout(() => {
            this.cargando = false;
            alert("¡Inicio de sesión simulado exitoso!");
            this.router.navigate(['/home']); // Redirigir al home
        }, 1000);
    }
}