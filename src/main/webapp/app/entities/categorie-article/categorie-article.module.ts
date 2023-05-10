import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CategorieArticleComponent } from './list/categorie-article.component';
import { CategorieArticleDetailComponent } from './detail/categorie-article-detail.component';
import { CategorieArticleUpdateComponent } from './update/categorie-article-update.component';
import { CategorieArticleDeleteDialogComponent } from './delete/categorie-article-delete-dialog.component';
import { CategorieArticleRoutingModule } from './route/categorie-article-routing.module';

@NgModule({
  imports: [SharedModule, CategorieArticleRoutingModule],
  declarations: [
    CategorieArticleComponent,
    CategorieArticleDetailComponent,
    CategorieArticleUpdateComponent,
    CategorieArticleDeleteDialogComponent,
  ],
})
export class CategorieArticleModule {}
