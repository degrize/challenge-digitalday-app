import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategorieArticleComponent } from '../list/categorie-article.component';
import { CategorieArticleDetailComponent } from '../detail/categorie-article-detail.component';
import { CategorieArticleUpdateComponent } from '../update/categorie-article-update.component';
import { CategorieArticleRoutingResolveService } from './categorie-article-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const categorieArticleRoute: Routes = [
  {
    path: '',
    component: CategorieArticleComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategorieArticleDetailComponent,
    resolve: {
      categorieArticle: CategorieArticleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategorieArticleUpdateComponent,
    resolve: {
      categorieArticle: CategorieArticleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategorieArticleUpdateComponent,
    resolve: {
      categorieArticle: CategorieArticleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categorieArticleRoute)],
  exports: [RouterModule],
})
export class CategorieArticleRoutingModule {}
