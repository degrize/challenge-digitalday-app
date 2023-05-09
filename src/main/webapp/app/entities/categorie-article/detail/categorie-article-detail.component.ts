import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorieArticle } from '../categorie-article.model';

@Component({
  selector: 'jhi-categorie-article-detail',
  templateUrl: './categorie-article-detail.component.html',
})
export class CategorieArticleDetailComponent implements OnInit {
  categorieArticle: ICategorieArticle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieArticle }) => {
      this.categorieArticle = categorieArticle;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
