import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CommanderDetailComponent } from './commander-detail.component';

describe('Commander Management Detail Component', () => {
  let comp: CommanderDetailComponent;
  let fixture: ComponentFixture<CommanderDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CommanderDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ commander: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CommanderDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CommanderDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load commander on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.commander).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
