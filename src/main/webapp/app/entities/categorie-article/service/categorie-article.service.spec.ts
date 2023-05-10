import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategorieArticle } from '../categorie-article.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../categorie-article.test-samples';

import { CategorieArticleService } from './categorie-article.service';

const requireRestSample: ICategorieArticle = {
  ...sampleWithRequiredData,
};

describe('CategorieArticle Service', () => {
  let service: CategorieArticleService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategorieArticle | ICategorieArticle[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CategorieArticleService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CategorieArticle', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const categorieArticle = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(categorieArticle).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CategorieArticle', () => {
      const categorieArticle = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(categorieArticle).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CategorieArticle', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CategorieArticle', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CategorieArticle', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCategorieArticleToCollectionIfMissing', () => {
      it('should add a CategorieArticle to an empty array', () => {
        const categorieArticle: ICategorieArticle = sampleWithRequiredData;
        expectedResult = service.addCategorieArticleToCollectionIfMissing([], categorieArticle);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categorieArticle);
      });

      it('should not add a CategorieArticle to an array that contains it', () => {
        const categorieArticle: ICategorieArticle = sampleWithRequiredData;
        const categorieArticleCollection: ICategorieArticle[] = [
          {
            ...categorieArticle,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategorieArticleToCollectionIfMissing(categorieArticleCollection, categorieArticle);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CategorieArticle to an array that doesn't contain it", () => {
        const categorieArticle: ICategorieArticle = sampleWithRequiredData;
        const categorieArticleCollection: ICategorieArticle[] = [sampleWithPartialData];
        expectedResult = service.addCategorieArticleToCollectionIfMissing(categorieArticleCollection, categorieArticle);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categorieArticle);
      });

      it('should add only unique CategorieArticle to an array', () => {
        const categorieArticleArray: ICategorieArticle[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categorieArticleCollection: ICategorieArticle[] = [sampleWithRequiredData];
        expectedResult = service.addCategorieArticleToCollectionIfMissing(categorieArticleCollection, ...categorieArticleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categorieArticle: ICategorieArticle = sampleWithRequiredData;
        const categorieArticle2: ICategorieArticle = sampleWithPartialData;
        expectedResult = service.addCategorieArticleToCollectionIfMissing([], categorieArticle, categorieArticle2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categorieArticle);
        expect(expectedResult).toContain(categorieArticle2);
      });

      it('should accept null and undefined values', () => {
        const categorieArticle: ICategorieArticle = sampleWithRequiredData;
        expectedResult = service.addCategorieArticleToCollectionIfMissing([], null, categorieArticle, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categorieArticle);
      });

      it('should return initial array if no CategorieArticle is added', () => {
        const categorieArticleCollection: ICategorieArticle[] = [sampleWithRequiredData];
        expectedResult = service.addCategorieArticleToCollectionIfMissing(categorieArticleCollection, undefined, null);
        expect(expectedResult).toEqual(categorieArticleCollection);
      });
    });

    describe('compareCategorieArticle', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategorieArticle(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCategorieArticle(entity1, entity2);
        const compareResult2 = service.compareCategorieArticle(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCategorieArticle(entity1, entity2);
        const compareResult2 = service.compareCategorieArticle(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCategorieArticle(entity1, entity2);
        const compareResult2 = service.compareCategorieArticle(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
