import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FournisseurFormService } from './fournisseur-form.service';
import { FournisseurService } from '../service/fournisseur.service';
import { IFournisseur } from '../fournisseur.model';
import { IQuartier } from 'app/entities/quartier/quartier.model';
import { QuartierService } from 'app/entities/quartier/service/quartier.service';

import { FournisseurUpdateComponent } from './fournisseur-update.component';

describe('Fournisseur Management Update Component', () => {
  let comp: FournisseurUpdateComponent;
  let fixture: ComponentFixture<FournisseurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fournisseurFormService: FournisseurFormService;
  let fournisseurService: FournisseurService;
  let quartierService: QuartierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FournisseurUpdateComponent],
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
      .overrideTemplate(FournisseurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FournisseurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fournisseurFormService = TestBed.inject(FournisseurFormService);
    fournisseurService = TestBed.inject(FournisseurService);
    quartierService = TestBed.inject(QuartierService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Quartier query and add missing value', () => {
      const fournisseur: IFournisseur = { id: 456 };
      const quartier: IQuartier = { id: 41409 };
      fournisseur.quartier = quartier;

      const quartierCollection: IQuartier[] = [{ id: 63824 }];
      jest.spyOn(quartierService, 'query').mockReturnValue(of(new HttpResponse({ body: quartierCollection })));
      const additionalQuartiers = [quartier];
      const expectedCollection: IQuartier[] = [...additionalQuartiers, ...quartierCollection];
      jest.spyOn(quartierService, 'addQuartierToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      expect(quartierService.query).toHaveBeenCalled();
      expect(quartierService.addQuartierToCollectionIfMissing).toHaveBeenCalledWith(
        quartierCollection,
        ...additionalQuartiers.map(expect.objectContaining)
      );
      expect(comp.quartiersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fournisseur: IFournisseur = { id: 456 };
      const quartier: IQuartier = { id: 32960 };
      fournisseur.quartier = quartier;

      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      expect(comp.quartiersSharedCollection).toContain(quartier);
      expect(comp.fournisseur).toEqual(fournisseur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFournisseur>>();
      const fournisseur = { id: 123 };
      jest.spyOn(fournisseurFormService, 'getFournisseur').mockReturnValue(fournisseur);
      jest.spyOn(fournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fournisseur }));
      saveSubject.complete();

      // THEN
      expect(fournisseurFormService.getFournisseur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fournisseurService.update).toHaveBeenCalledWith(expect.objectContaining(fournisseur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFournisseur>>();
      const fournisseur = { id: 123 };
      jest.spyOn(fournisseurFormService, 'getFournisseur').mockReturnValue({ id: null });
      jest.spyOn(fournisseurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fournisseur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fournisseur }));
      saveSubject.complete();

      // THEN
      expect(fournisseurFormService.getFournisseur).toHaveBeenCalled();
      expect(fournisseurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFournisseur>>();
      const fournisseur = { id: 123 };
      jest.spyOn(fournisseurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fournisseur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fournisseurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareQuartier', () => {
      it('Should forward to quartierService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(quartierService, 'compareQuartier');
        comp.compareQuartier(entity, entity2);
        expect(quartierService.compareQuartier).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
