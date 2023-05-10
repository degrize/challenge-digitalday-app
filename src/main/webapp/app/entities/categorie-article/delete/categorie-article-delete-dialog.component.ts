import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategorieArticle } from '../categorie-article.model';
import { CategorieArticleService } from '../service/categorie-article.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './categorie-article-delete-dialog.component.html',
})
export class CategorieArticleDeleteDialogComponent {
  categorieArticle?: ICategorieArticle;

  constructor(protected categorieArticleService: CategorieArticleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categorieArticleService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
