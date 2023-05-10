import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CommanderFormService } from './commander-form.service';
import { CommanderService } from '../service/commander.service';
import { ICommander } from '../commander.model';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/service/fournisseur.service';
import { IArticle } from 'app/entities/article/article.model';
import { ArticleService } from 'app/entities/article/service/article.service';

import { CommanderUpdateComponent } from './commander-update.component';

describe('Commander Management Update Component', () => {
  let comp: CommanderUpdateComponent;
  let fixture: ComponentFixture<CommanderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let commanderFormService: CommanderFormService;
  let commanderService: CommanderService;
  let fournisseurService: FournisseurService;
  let articleService: ArticleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CommanderUpdateComponent],
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
      .overrideTemplate(CommanderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommanderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    commanderFormService = TestBed.inject(CommanderFormService);
    commanderService = TestBed.inject(CommanderService);
    fournisseurService = TestBed.inject(FournisseurService);
    articleService = TestBed.inject(ArticleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Fournisseur query and add missing value', () => {
      const commander: ICommander = { id: 456 };
      const fournisseur: IFournisseur = { id: 27914 };
      commander.fournisseur = fournisseur;

      const fournisseurCollection: IFournisseur[] = [{ id: 60136 }];
      jest.spyOn(fournisseurService, 'query').mockReturnValue(of(new HttpResponse({ body: fournisseurCollection })));
      const additionalFournisseurs = [fournisseur];
      const expectedCollection: IFournisseur[] = [...additionalFournisseurs, ...fournisseurCollection];
      jest.spyOn(fournisseurService, 'addFournisseurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commander });
      comp.ngOnInit();

      expect(fournisseurService.query).toHaveBeenCalled();
      expect(fournisseurService.addFournisseurToCollectionIfMissing).toHaveBeenCalledWith(
        fournisseurCollection,
        ...additionalFournisseurs.map(expect.objectContaining)
      );
      expect(comp.fournisseursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Article query and add missing value', () => {
      const commander: ICommander = { id: 456 };
      const article: IArticle = { id: 87961 };
      commander.article = article;

      const articleCollection: IArticle[] = [{ id: 42027 }];
      jest.spyOn(articleService, 'query').mockReturnValue(of(new HttpResponse({ body: articleCollection })));
      const additionalArticles = [article];
      const expectedCollection: IArticle[] = [...additionalArticles, ...articleCollection];
      jest.spyOn(articleService, 'addArticleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ commander });
      comp.ngOnInit();

      expect(articleService.query).toHaveBeenCalled();
      expect(articleService.addArticleToCollectionIfMissing).toHaveBeenCalledWith(
        articleCollection,
        ...additionalArticles.map(expect.objectContaining)
      );
      expect(comp.articlesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const commander: ICommander = { id: 456 };
      const fournisseur: IFournisseur = { id: 52906 };
      commander.fournisseur = fournisseur;
      const article: IArticle = { id: 27627 };
      commander.article = article;

      activatedRoute.data = of({ commander });
      comp.ngOnInit();

      expect(comp.fournisseursSharedCollection).toContain(fournisseur);
      expect(comp.articlesSharedCollection).toContain(article);
      expect(comp.commander).toEqual(commander);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommander>>();
      const commander = { id: 123 };
      jest.spyOn(commanderFormService, 'getCommander').mockReturnValue(commander);
      jest.spyOn(commanderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commander });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commander }));
      saveSubject.complete();

      // THEN
      expect(commanderFormService.getCommander).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(commanderService.update).toHaveBeenCalledWith(expect.objectContaining(commander));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommander>>();
      const commander = { id: 123 };
      jest.spyOn(commanderFormService, 'getCommander').mockReturnValue({ id: null });
      jest.spyOn(commanderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commander: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: commander }));
      saveSubject.complete();

      // THEN
      expect(commanderFormService.getCommander).toHaveBeenCalled();
      expect(commanderService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICommander>>();
      const commander = { id: 123 };
      jest.spyOn(commanderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ commander });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(commanderService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFournisseur', () => {
      it('Should forward to fournisseurService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fournisseurService, 'compareFournisseur');
        comp.compareFournisseur(entity, entity2);
        expect(fournisseurService.compareFournisseur).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareArticle', () => {
      it('Should forward to articleService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(articleService, 'compareArticle');
        comp.compareArticle(entity, entity2);
        expect(articleService.compareArticle).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
