import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CategorieArticleFormService, CategorieArticleFormGroup } from './categorie-article-form.service';
import { ICategorieArticle } from '../categorie-article.model';
import { CategorieArticleService } from '../service/categorie-article.service';

@Component({
  selector: 'jhi-categorie-article-update',
  templateUrl: './categorie-article-update.component.html',
})
export class CategorieArticleUpdateComponent implements OnInit {
  isSaving = false;
  categorieArticle: ICategorieArticle | null = null;

  editForm: CategorieArticleFormGroup = this.categorieArticleFormService.createCategorieArticleFormGroup();

  constructor(
    protected categorieArticleService: CategorieArticleService,
    protected categorieArticleFormService: CategorieArticleFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieArticle }) => {
      this.categorieArticle = categorieArticle;
      if (categorieArticle) {
        this.updateForm(categorieArticle);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorieArticle = this.categorieArticleFormService.getCategorieArticle(this.editForm);
    if (categorieArticle.id !== null) {
      this.subscribeToSaveResponse(this.categorieArticleService.update(categorieArticle));
    } else {
      this.subscribeToSaveResponse(this.categorieArticleService.create(categorieArticle));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorieArticle>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(categorieArticle: ICategorieArticle): void {
    this.categorieArticle = categorieArticle;
    this.categorieArticleFormService.resetForm(this.editForm, categorieArticle);
  }
}
