import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'article',
        data: { pageTitle: 'digitaldayApp.article.home.title' },
        loadChildren: () => import('./article/article.module').then(m => m.ArticleModule),
      },
      {
        path: 'client',
        data: { pageTitle: 'digitaldayApp.client.home.title' },
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule),
      },
      {
        path: 'vente',
        data: { pageTitle: 'digitaldayApp.vente.home.title' },
        loadChildren: () => import('./vente/vente.module').then(m => m.VenteModule),
      },
      {
        path: 'categorie-article',
        data: { pageTitle: 'digitaldayApp.categorieArticle.home.title' },
        loadChildren: () => import('./categorie-article/categorie-article.module').then(m => m.CategorieArticleModule),
      },
      {
        path: 'commander',
        data: { pageTitle: 'digitaldayApp.commander.home.title' },
        loadChildren: () => import('./commander/commander.module').then(m => m.CommanderModule),
      },
      {
        path: 'fournisseur',
        data: { pageTitle: 'digitaldayApp.fournisseur.home.title' },
        loadChildren: () => import('./fournisseur/fournisseur.module').then(m => m.FournisseurModule),
      },
      {
        path: 'ville',
        data: { pageTitle: 'digitaldayApp.ville.home.title' },
        loadChildren: () => import('./ville/ville.module').then(m => m.VilleModule),
      },
      {
        path: 'quartier',
        data: { pageTitle: 'digitaldayApp.quartier.home.title' },
        loadChildren: () => import('./quartier/quartier.module').then(m => m.QuartierModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
