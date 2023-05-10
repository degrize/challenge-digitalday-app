import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CategorieArticleFormService } from './categorie-article-form.service';
import { CategorieArticleService } from '../service/categorie-article.service';
import { ICategorieArticle } from '../categorie-article.model';

import { CategorieArticleUpdateComponent } from './categorie-article-update.component';

describe('CategorieArticle Management Update Component', () => {
  let comp: CategorieArticleUpdateComponent;
  let fixture: ComponentFixture<CategorieArticleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categorieArticleFormService: CategorieArticleFormService;
  let categorieArticleService: CategorieArticleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CategorieArticleUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CategorieArticleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategorieArticleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categorieArticleFormService = TestBed.inject(CategorieArticleFormService);
    categorieArticleService = TestBed.inject(CategorieArticleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const categorieArticle: ICategorieArticle = { id: 456 };

      activatedRoute.data = of({ categorieArticle });
      comp.ngOnInit();

      expect(comp.categorieArticle).toEqual(categorieArticle);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorieArticle>>();
      const categorieArticle = { id: 123 };
      jest.spyOn(categorieArticleFormService, 'getCategorieArticle').mockReturnValue(categorieArticle);
      jest.spyOn(categorieArticleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorieArticle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categorieArticle }));
      saveSubject.complete();

      // THEN
      expect(categorieArticleFormService.getCategorieArticle).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categorieArticleService.update).toHaveBeenCalledWith(expect.objectContaining(categorieArticle));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorieArticle>>();
      const categorieArticle = { id: 123 };
      jest.spyOn(categorieArticleFormService, 'getCategorieArticle').mockReturnValue({ id: null });
      jest.spyOn(categorieArticleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorieArticle: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categorieArticle }));
      saveSubject.complete();

      // THEN
      expect(categorieArticleFormService.getCategorieArticle).toHaveBeenCalled();
      expect(categorieArticleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorieArticle>>();
      const categorieArticle = { id: 123 };
      jest.spyOn(categorieArticleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorieArticle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categorieArticleService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
