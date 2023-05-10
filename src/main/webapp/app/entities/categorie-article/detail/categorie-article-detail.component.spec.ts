import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CategorieArticleDetailComponent } from './categorie-article-detail.component';

describe('CategorieArticle Management Detail Component', () => {
  let comp: CategorieArticleDetailComponent;
  let fixture: ComponentFixture<CategorieArticleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategorieArticleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ categorieArticle: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CategorieArticleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CategorieArticleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load categorieArticle on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.categorieArticle).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
