import { Component, OnInit, ChangeDetectorRef } from '@angular/core'; // <--- 1. IMPORTAR ESTO
import { PortalService } from '../../services/portal';
import { AuthService } from '../../../auth/services/auth';

@Component({
  selector: 'app-mis-reservas',
  standalone: false,
  templateUrl: './mis-reservas.html',
  styleUrls: ['./mis-reservas.css']
})
export class MisReservasComponent implements OnInit {

  reservas: any[] = [];
  loading: boolean = true;

  constructor(
    private portalService: PortalService,
    private authService: AuthService,
    private cd: ChangeDetectorRef // <--- 2. INYECTARLO ACÁ
  ) { }

  ngOnInit(): void {
    const usuario = this.authService.getUsuarioActual();

    if (usuario && usuario.nroCliente) {
      this.portalService.obtenerMisReservas(usuario.nroCliente).subscribe({
        next: (data) => {
          console.log("Datos recibidos:", data);
          this.reservas = data;
          this.loading = false; 
          
          this.cd.detectChanges(); // <--- 3. ¡EL DESPERTADOR! ⏰
          // Esto obliga a Angular a quitar el spinner y mostrar la lista YA.
        },
        error: (err) => {
          console.error(err);
          this.loading = false;
          this.cd.detectChanges(); // <--- También si hay error
        }
      });
    } else {
      this.loading = false;
    }
  }
}