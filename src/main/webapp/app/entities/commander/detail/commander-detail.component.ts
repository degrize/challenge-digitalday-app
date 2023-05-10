import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommander } from '../commander.model';

@Component({
  selector: 'jhi-commander-detail',
  templateUrl: './commander-detail.component.html',
})
export class CommanderDetailComponent implements OnInit {
  commander: ICommander | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commander }) => {
      this.commander = commander;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
