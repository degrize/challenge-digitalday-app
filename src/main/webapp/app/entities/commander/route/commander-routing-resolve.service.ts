import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICommander } from '../commander.model';
import { CommanderService } from '../service/commander.service';

@Injectable({ providedIn: 'root' })
export class CommanderRoutingResolveService implements Resolve<ICommander | null> {
  constructor(protected service: CommanderService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommander | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((commander: HttpResponse<ICommander>) => {
          if (commander.body) {
            return of(commander.body);
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
