import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategorieArticle } from '../categorie-article.model';
import { CategorieArticleService } from '../service/categorie-article.service';

@Injectable({ providedIn: 'root' })
export class CategorieArticleRoutingResolveService implements Resolve<ICategorieArticle | null> {
  constructor(protected service: CategorieArticleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorieArticle | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categorieArticle: HttpResponse<ICategorieArticle>) => {
          if (categorieArticle.body) {
            return of(categorieArticle.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
