import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  pages = [
    { title: 'Home', path: '/home' },
    { title: 'Coleta', path: '/reading' },
    { title: 'Documentação', path: '/documentation' },
  ];
}
