import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { VenteFormService, VenteFormGroup } from './vente-form.service';
import { IVente } from '../vente.model';
import { VenteService } from '../service/vente.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { IArticle } from 'app/entities/article/article.model';
import { ArticleService } from 'app/entities/article/service/article.service';

@Component({
  selector: 'jhi-vente-update',
  templateUrl: './vente-update.component.html',
})
export class VenteUpdateComponent implements OnInit {
  isSaving = false;
  vente: IVente | null = null;

  clientsSharedCollection: IClient[] = [];
  articlesSharedCollection: IArticle[] = [];
  targetArticle: IArticle[];

  qteArticlesAcheter: Array<{ idArticle: number; qte: number | undefined }> = [
    { idArticle: 1, qte: undefined },
    { idArticle: 2, qte: undefined },
  ];

  creditStateOptions: any[] = [
    { label: 'Non', value: false },
    { label: 'Oui', value: true },
  ];

  montantAchat: number | null = 0;

  editForm: VenteFormGroup = this.venteFormService.createVenteFormGroup();

  constructor(
    protected venteService: VenteService,
    protected venteFormService: VenteFormService,
    protected clientService: ClientService,
    protected articleService: ArticleService,
    protected activatedRoute: ActivatedRoute
  ) {
    this.targetArticle = [];
  }

  compareClient = (o1: IClient | null, o2: IClient | null): boolean => this.clientService.compareClient(o1, o2);

  compareArticle = (o1: IArticle | null, o2: IArticle | null): boolean => this.articleService.compareArticle(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vente }) => {
      this.vente = vente;
      if (vente) {
        this.updateForm(vente);
      }

      this.loadRelationshipsOptions();
    });

    this.prepareQte();
  }

  previousState(): void {
    window.history.back();
  }

  validationAchat(): void {
    if (this.targetArticle[0]) {
      const vente = this.venteFormService.getVente(this.editForm);
      // @ts-ignore
      this.montantAchat = this.targetArticle[0].prixVente * vente.qte;
    }
  }
  save(): void {
    this.isSaving = true;
    const vente = this.venteFormService.getVente(this.editForm);
    if (this.targetArticle) {
      vente.article = this.targetArticle[0];
    }
    if (vente.id !== null) {
      this.subscribeToSaveResponse(this.venteService.update(vente));
    } else {
      this.subscribeToSaveResponse(this.venteService.create(vente));
    }
  }

  getSeverity(status: string): string {
    switch (status) {
      case 'INSTOCK':
        return 'success';
      case 'LOWSTOCK':
        return 'warning';
      case 'OUTOFSTOCK':
        return 'danger';
      default:
        return '';
    }
  }

  prepareQte(): void {
    this.articlesSharedCollection.forEach(article => {
      this.qteArticlesAcheter.push({ idArticle: article.id, qte: undefined });
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVente>>): void {
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

  protected updateForm(vente: IVente): void {
    this.vente = vente;
    this.venteFormService.resetForm(this.editForm, vente);

    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing<IClient>(this.clientsSharedCollection, vente.client);
    this.articlesSharedCollection = this.articleService.addArticleToCollectionIfMissing<IArticle>(
      this.articlesSharedCollection,
      vente.article
    );
  }

  protected loadRelationshipsOptions(): void {
    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing<IClient>(clients, this.vente?.client)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));

    this.articleService
      .query()
      .pipe(map((res: HttpResponse<IArticle[]>) => res.body ?? []))
      .pipe(map((articles: IArticle[]) => this.articleService.addArticleToCollectionIfMissing<IArticle>(articles, this.vente?.article)))
      .subscribe((articles: IArticle[]) => (this.articlesSharedCollection = articles));
  }
}
