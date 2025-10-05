import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-landing-page',
  imports: [ButtonModule],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.scss'
})
export class LandingPageComponent {

  openScheduling() {
    // TODO: Implementar navegação para página de agendamento
    alert('Funcionalidade de agendamento será implementada em breve!');
  }
}
