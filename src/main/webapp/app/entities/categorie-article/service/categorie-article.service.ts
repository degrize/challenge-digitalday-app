import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategorieArticle, NewCategorieArticle } from '../categorie-article.model';

export type PartialUpdateCategorieArticle = Partial<ICategorieArticle> & Pick<ICategorieArticle, 'id'>;

export type EntityResponseType = HttpResponse<ICategorieArticle>;
export type EntityArrayResponseType = HttpResponse<ICategorieArticle[]>;

@Injectable({ providedIn: 'root' })
export class CategorieArticleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categorie-articles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(categorieArticle: NewCategorieArticle): Observable<EntityResponseType> {
    return this.http.post<ICategorieArticle>(this.resourceUrl, categorieArticle, { observe: 'response' });
  }

  update(categorieArticle: ICategorieArticle): Observable<EntityResponseType> {
    return this.http.put<ICategorieArticle>(
      `${this.resourceUrl}/${this.getCategorieArticleIdentifier(categorieArticle)}`,
      categorieArticle,
      { observe: 'response' }
    );
  }

  partialUpdate(categorieArticle: PartialUpdateCategorieArticle): Observable<EntityResponseType> {
    return this.http.patch<ICategorieArticle>(
      `${this.resourceUrl}/${this.getCategorieArticleIdentifier(categorieArticle)}`,
      categorieArticle,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorieArticle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieArticle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategorieArticleIdentifier(categorieArticle: Pick<ICategorieArticle, 'id'>): number {
    return categorieArticle.id;
  }

  compareCategorieArticle(o1: Pick<ICategorieArticle, 'id'> | null, o2: Pick<ICategorieArticle, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategorieArticleIdentifier(o1) === this.getCategorieArticleIdentifier(o2) : o1 === o2;
  }

  addCategorieArticleToCollectionIfMissing<Type extends Pick<ICategorieArticle, 'id'>>(
    categorieArticleCollection: Type[],
    ...categorieArticlesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categorieArticles: Type[] = categorieArticlesToCheck.filter(isPresent);
    if (categorieArticles.length > 0) {
      const categorieArticleCollectionIdentifiers = categorieArticleCollection.map(
        categorieArticleItem => this.getCategorieArticleIdentifier(categorieArticleItem)!
      );
      const categorieArticlesToAdd = categorieArticles.filter(categorieArticleItem => {
        const categorieArticleIdentifier = this.getCategorieArticleIdentifier(categorieArticleItem);
        if (categorieArticleCollectionIdentifiers.includes(categorieArticleIdentifier)) {
          return false;
        }
        categorieArticleCollectionIdentifiers.push(categorieArticleIdentifier);
        return true;
      });
      return [...categorieArticlesToAdd, ...categorieArticleCollection];
    }
    return categorieArticleCollection;
  }
}
