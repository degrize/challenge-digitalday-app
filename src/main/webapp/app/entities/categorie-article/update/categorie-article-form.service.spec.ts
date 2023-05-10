import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../categorie-article.test-samples';

import { CategorieArticleFormService } from './categorie-article-form.service';

describe('CategorieArticle Form Service', () => {
  let service: CategorieArticleFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategorieArticleFormService);
  });

  describe('Service methods', () => {
    describe('createCategorieArticleFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategorieArticleFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            description: expect.any(Object),
            articles: expect.any(Object),
          })
        );
      });

      it('passing ICategorieArticle should create a new form with FormGroup', () => {
        const formGroup = service.createCategorieArticleFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            description: expect.any(Object),
            articles: expect.any(Object),
          })
        );
      });
    });

    describe('getCategorieArticle', () => {
      it('should return NewCategorieArticle for default CategorieArticle initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCategorieArticleFormGroup(sampleWithNewData);

        const categorieArticle = service.getCategorieArticle(formGroup) as any;

        expect(categorieArticle).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategorieArticle for empty CategorieArticle initial value', () => {
        const formGroup = service.createCategorieArticleFormGroup();

        const categorieArticle = service.getCategorieArticle(formGroup) as any;

        expect(categorieArticle).toMatchObject({});
      });

      it('should return ICategorieArticle', () => {
        const formGroup = service.createCategorieArticleFormGroup(sampleWithRequiredData);

        const categorieArticle = service.getCategorieArticle(formGroup) as any;

        expect(categorieArticle).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategorieArticle should not enable id FormControl', () => {
        const formGroup = service.createCategorieArticleFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategorieArticle should disable id FormControl', () => {
        const formGroup = service.createCategorieArticleFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
