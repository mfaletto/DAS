import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReservaService } from '../../services/reserva';
import { AuthService } from '../../../auth/services/auth'; // Ajustar ruta si es necesario

@Component({
  selector: 'app-restaurante-detail',
  templateUrl: './restaurante-detail.html',
  styleUrls: ['./restaurante-detail.css'],
  standalone: false
})
export class RestauranteDetail implements OnInit {

  datos: any;
  idRestaurante: number = 0;
  
  // Datos normalizados para la vista
  mensaje: string = '';
  turnos: string[] = [];
  
  // Formulario
  turnoSeleccionado: string = '';
  cantAdultos: number = 2;
  cantMenores: number = 0;
  observaciones: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private reservaService: ReservaService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    // 1. Obtener ID de la URL
    this.idRestaurante = Number(this.route.snapshot.paramMap.get('id'));

    // 2. Obtener datos DEL RESOLVER (¡Ya están listos, no hay subscribe!)
    this.datos = this.route.snapshot.data['datosDisponibilidad'];

    // 3. Normalizar (Porque REST y SOAP devuelven campos distintos)
    if (this.datos.turnosDisponibles) {
      // Caso SOAP (Perukai)
      this.turnos = this.datos.turnosDisponibles;
      this.mensaje = this.datos.mensaje + ' - Chef: ' + this.datos.sugerenciaChef;
    } else if (this.datos.turnos_libres) {
      // Caso REST (La Bella Pizza)
      this.turnos = this.datos.turnos_libres;
      this.mensaje = `Sucursal: ${this.datos.sucursal_real} - ${this.datos.direccion}`;
    }
  }

  confirmarReserva(): void {
    if (!this.turnoSeleccionado) {
      alert('Por favor seleccione un horario');
      return;
    }

    const usuario = this.authService.usuarioActual; // Obtenemos el ID del usuario logueado

    const nuevaReserva = {
      nroCliente: usuario ? usuario.nroCliente : 1, // Fallback a 1 si no hay login
      nroRestaurante: this.idRestaurante,
      fechaReserva: new Date().toISOString().split('T')[0], // Fecha actual YYYY-MM-DD
      horaReserva: this.turnoSeleccionado,
      cantAdultos: this.cantAdultos,
      cantMenores: this.cantMenores,
      observaciones: this.observaciones
    };

    this.reservaService.crearReserva(nuevaReserva).subscribe({
      next: (resp) => {
        alert(`¡Reserva Confirmada! Nro: ${resp.nro_reserva}`);
        this.router.navigate(['/portal']);
      },
      error: (err) => {
        alert('Error al reservar: ' + err.message);
      }
    });
  }
}