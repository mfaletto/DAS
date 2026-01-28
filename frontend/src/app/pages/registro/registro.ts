// src/app/pages/registro/registro.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms'; // ⬅️ Necesario para [(ngModel)]

// Módulos de Material
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';

// Servicio y Modelos
import { Api } from '../../services/api';
import { RegistroRequest } from '../../models/promocion.model';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, // Para formularios
    MatCardModule, MatFormFieldModule, MatInputModule, 
    MatSelectModule, MatButtonModule
  ],
  templateUrl: './registro.html',
  styleUrls: ['./registro.css']
})
export class Registro implements OnInit {

    // Modelo de datos para el Two-Way Binding
    registroData: RegistroRequest = {
        nombre: '', 
        apellido: '', 
        correo: '', 
        clave: '', 
        telefonos: '',
        // Aquí no incluimos Preferencias ni Address porque simplificamos
        // la interfaz RegistroRequest
    };
    
    cargando = false;
    registroExitoso = false;
    errorRegistro: string | null = null;
    
    constructor(private api: Api, private router: Router) { }

    ngOnInit(): void { }

    // Función llamada cuando el usuario presiona el botón "Register"
    onSubmitRegistro(): void {
        // ⚠️ En un proyecto real, aquí iría la validación del formulario
        if (!this.registroData.correo || !this.registroData.clave) {
            this.errorRegistro = "El correo y la contraseña son obligatorios.";
            return;
        }

        this.cargando = true;
        this.errorRegistro = null;
        this.registroExitoso = false;
        
        this.api.registrarUsuario(this.registroData).subscribe({
            next: () => {
                this.registroExitoso = true;
                this.cargando = false;
                // Redirigir a Login o Home después de un registro exitoso (Buena Práctica)
                alert("¡Registro exitoso! Serás redirigido para iniciar sesión.");
                this.router.navigate(['/login']); 
            },
            error: (err) => {
                // Manejo de errores (ej: correo ya en uso)
                if (err.status === 400) {
                    this.errorRegistro = 'Error: El correo electrónico ya está registrado o el formato es inválido.';
                } else {
                    this.errorRegistro = 'Ocurrió un error en el servidor. Intenta de nuevo.';
                }
                this.cargando = false;
            }
        });
    }
}