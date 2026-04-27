import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DocumentationService } from '../../services/domain/documentation.service';

@Component({
  selector: 'app-documentation',
  standalone: true,
  templateUrl: 'documentation.html',
  styleUrl: 'documentation.scss'
})
export class DocumentationPage implements OnInit {
  pageTitle = '';
  pageContent = '';

  constructor(
    private router: Router,
    private documentationService: DocumentationService
  ) {}

  ngOnInit() {
    this.documentationService.findDocumentation().subscribe({
      next: res => {
        this.pageTitle = res.title.rendered;
        this.pageContent = res.content.rendered;
      },
      error: () => {}
    });
  }

  goBack() {
    this.router.navigate(['/home']);
  }
}
