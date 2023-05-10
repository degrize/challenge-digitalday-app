import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CommanderFormService, CommanderFormGroup } from './commander-form.service';
import { ICommander } from '../commander.model';
import { CommanderService } from '../service/commander.service';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/service/fournisseur.service';
import { IArticle } from 'app/entities/article/article.model';
import { ArticleService } from 'app/entities/article/service/article.service';

@Component({
  selector: 'jhi-commander-update',
  templateUrl: './commander-update.component.html',
})
export class CommanderUpdateComponent implements OnInit {
  isSaving = false;
  commander: ICommander | null = null;

  fournisseursSharedCollection: IFournisseur[] = [];
  articlesSharedCollection: IArticle[] = [];

  editForm: CommanderFormGroup = this.commanderFormService.createCommanderFormGroup();

  constructor(
    protected commanderService: CommanderService,
    protected commanderFormService: CommanderFormService,
    protected fournisseurService: FournisseurService,
    protected articleService: ArticleService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFournisseur = (o1: IFournisseur | null, o2: IFournisseur | null): boolean => this.fournisseurService.compareFournisseur(o1, o2);

  compareArticle = (o1: IArticle | null, o2: IArticle | null): boolean => this.articleService.compareArticle(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commander }) => {
      this.commander = commander;
      if (commander) {
        this.updateForm(commander);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commander = this.commanderFormService.getCommander(this.editForm);
    if (commander.id !== null) {
      this.subscribeToSaveResponse(this.commanderService.update(commander));
    } else {
      this.subscribeToSaveResponse(this.commanderService.create(commander));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommander>>): void {
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

  protected updateForm(commander: ICommander): void {
    this.commander = commander;
    this.commanderFormService.resetForm(this.editForm, commander);

    this.fournisseursSharedCollection = this.fournisseurService.addFournisseurToCollectionIfMissing<IFournisseur>(
      this.fournisseursSharedCollection,
      commander.fournisseur
    );
    this.articlesSharedCollection = this.articleService.addArticleToCollectionIfMissing<IArticle>(
      this.articlesSharedCollection,
      commander.article
    );
  }

  protected loadRelationshipsOptions(): void {
    this.fournisseurService
      .query()
      .pipe(map((res: HttpResponse<IFournisseur[]>) => res.body ?? []))
      .pipe(
        map((fournisseurs: IFournisseur[]) =>
          this.fournisseurService.addFournisseurToCollectionIfMissing<IFournisseur>(fournisseurs, this.commander?.fournisseur)
        )
      )
      .subscribe((fournisseurs: IFournisseur[]) => (this.fournisseursSharedCollection = fournisseurs));

    this.articleService
      .query()
      .pipe(map((res: HttpResponse<IArticle[]>) => res.body ?? []))
      .pipe(map((articles: IArticle[]) => this.articleService.addArticleToCollectionIfMissing<IArticle>(articles, this.commander?.article)))
      .subscribe((articles: IArticle[]) => (this.articlesSharedCollection = articles));
  }
}
