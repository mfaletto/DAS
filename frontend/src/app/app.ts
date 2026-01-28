// src/app/app.ts
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

// 1. CORRECCIÓN: La ruta no debe incluir ".component"
import { Header } from './components/layout/header/header';
import { LoaderComponent } from './components/loader/loader.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Header, LoaderComponent], // ⬅️ Incluir LoaderComponent
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class AppComponent {
  title = 'frontend'; 
}