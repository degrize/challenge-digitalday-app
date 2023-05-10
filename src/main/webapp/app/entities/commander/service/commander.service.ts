import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICommander, NewCommander } from '../commander.model';

export type PartialUpdateCommander = Partial<ICommander> & Pick<ICommander, 'id'>;

type RestOf<T extends ICommander | NewCommander> = Omit<T, 'dateCommande'> & {
  dateCommande?: string | null;
};

export type RestCommander = RestOf<ICommander>;

export type NewRestCommander = RestOf<NewCommander>;

export type PartialUpdateRestCommander = RestOf<PartialUpdateCommander>;

export type EntityResponseType = HttpResponse<ICommander>;
export type EntityArrayResponseType = HttpResponse<ICommander[]>;

@Injectable({ providedIn: 'root' })
export class CommanderService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/commanders');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(commander: NewCommander): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commander);
    return this.http
      .post<RestCommander>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(commander: ICommander): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commander);
    return this.http
      .put<RestCommander>(`${this.resourceUrl}/${this.getCommanderIdentifier(commander)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(commander: PartialUpdateCommander): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commander);
    return this.http
      .patch<RestCommander>(`${this.resourceUrl}/${this.getCommanderIdentifier(commander)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCommander>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCommander[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCommanderIdentifier(commander: Pick<ICommander, 'id'>): number {
    return commander.id;
  }

  compareCommander(o1: Pick<ICommander, 'id'> | null, o2: Pick<ICommander, 'id'> | null): boolean {
    return o1 && o2 ? this.getCommanderIdentifier(o1) === this.getCommanderIdentifier(o2) : o1 === o2;
  }

  addCommanderToCollectionIfMissing<Type extends Pick<ICommander, 'id'>>(
    commanderCollection: Type[],
    ...commandersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const commanders: Type[] = commandersToCheck.filter(isPresent);
    if (commanders.length > 0) {
      const commanderCollectionIdentifiers = commanderCollection.map(commanderItem => this.getCommanderIdentifier(commanderItem)!);
      const commandersToAdd = commanders.filter(commanderItem => {
        const commanderIdentifier = this.getCommanderIdentifier(commanderItem);
        if (commanderCollectionIdentifiers.includes(commanderIdentifier)) {
          return false;
        }
        commanderCollectionIdentifiers.push(commanderIdentifier);
        return true;
      });
      return [...commandersToAdd, ...commanderCollection];
    }
    return commanderCollection;
  }

  protected convertDateFromClient<T extends ICommander | NewCommander | PartialUpdateCommander>(commander: T): RestOf<T> {
    return {
      ...commander,
      dateCommande: commander.dateCommande?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCommander: RestCommander): ICommander {
    return {
      ...restCommander,
      dateCommande: restCommander.dateCommande ? dayjs(restCommander.dateCommande) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCommander>): HttpResponse<ICommander> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCommander[]>): HttpResponse<ICommander[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
