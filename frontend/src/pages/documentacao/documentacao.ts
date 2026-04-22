import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DocumentationService } from '../../services/domain/documentation.service';

@Component({
  selector: 'app-documentacao',
  standalone: true,
  templateUrl: 'documentacao.html',
  styleUrl: 'documentacao.scss'
})
export class DocumentacaoPage implements OnInit {
  titulo = '';
  texto = '';

  constructor(
    private router: Router,
    private documentationService: DocumentationService
  ) {}

  ngOnInit() {
    this.documentationService.findDocumentation().subscribe({
      next: res => {
        this.titulo = res.title.rendered;
        this.texto = res.content.rendered;
      },
      error: () => {}
    });
  }

  voltar() {
    this.router.navigate(['/home']);
  }
}
