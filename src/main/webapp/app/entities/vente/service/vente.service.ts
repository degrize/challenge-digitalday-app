import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVente, NewVente } from '../vente.model';

export type PartialUpdateVente = Partial<IVente> & Pick<IVente, 'id'>;

type RestOf<T extends IVente | NewVente> = Omit<T, 'dateVente'> & {
  dateVente?: string | null;
};

export type RestVente = RestOf<IVente>;

export type NewRestVente = RestOf<NewVente>;

export type PartialUpdateRestVente = RestOf<PartialUpdateVente>;

export type EntityResponseType = HttpResponse<IVente>;
export type EntityArrayResponseType = HttpResponse<IVente[]>;

@Injectable({ providedIn: 'root' })
export class VenteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ventes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vente: NewVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vente);
    return this.http.post<RestVente>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(vente: IVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vente);
    return this.http
      .put<RestVente>(`${this.resourceUrl}/${this.getVenteIdentifier(vente)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(vente: PartialUpdateVente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vente);
    return this.http
      .patch<RestVente>(`${this.resourceUrl}/${this.getVenteIdentifier(vente)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestVente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestVente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVenteIdentifier(vente: Pick<IVente, 'id'>): number {
    return vente.id;
  }

  compareVente(o1: Pick<IVente, 'id'> | null, o2: Pick<IVente, 'id'> | null): boolean {
    return o1 && o2 ? this.getVenteIdentifier(o1) === this.getVenteIdentifier(o2) : o1 === o2;
  }

  addVenteToCollectionIfMissing<Type extends Pick<IVente, 'id'>>(
    venteCollection: Type[],
    ...ventesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ventes: Type[] = ventesToCheck.filter(isPresent);
    if (ventes.length > 0) {
      const venteCollectionIdentifiers = venteCollection.map(venteItem => this.getVenteIdentifier(venteItem)!);
      const ventesToAdd = ventes.filter(venteItem => {
        const venteIdentifier = this.getVenteIdentifier(venteItem);
        if (venteCollectionIdentifiers.includes(venteIdentifier)) {
          return false;
        }
        venteCollectionIdentifiers.push(venteIdentifier);
        return true;
      });
      return [...ventesToAdd, ...venteCollection];
    }
    return venteCollection;
  }

  protected convertDateFromClient<T extends IVente | NewVente | PartialUpdateVente>(vente: T): RestOf<T> {
    return {
      ...vente,
      dateVente: vente.dateVente?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restVente: RestVente): IVente {
    return {
      ...restVente,
      dateVente: restVente.dateVente ? dayjs(restVente.dateVente) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestVente>): HttpResponse<IVente> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestVente[]>): HttpResponse<IVente[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
