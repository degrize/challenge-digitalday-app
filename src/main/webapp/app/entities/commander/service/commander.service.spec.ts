import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICommander } from '../commander.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../commander.test-samples';

import { CommanderService, RestCommander } from './commander.service';

const requireRestSample: RestCommander = {
  ...sampleWithRequiredData,
  dateCommande: sampleWithRequiredData.dateCommande?.toJSON(),
};

describe('Commander Service', () => {
  let service: CommanderService;
  let httpMock: HttpTestingController;
  let expectedResult: ICommander | ICommander[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CommanderService);
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

    it('should create a Commander', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const commander = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(commander).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Commander', () => {
      const commander = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(commander).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Commander', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Commander', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Commander', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCommanderToCollectionIfMissing', () => {
      it('should add a Commander to an empty array', () => {
        const commander: ICommander = sampleWithRequiredData;
        expectedResult = service.addCommanderToCollectionIfMissing([], commander);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commander);
      });

      it('should not add a Commander to an array that contains it', () => {
        const commander: ICommander = sampleWithRequiredData;
        const commanderCollection: ICommander[] = [
          {
            ...commander,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCommanderToCollectionIfMissing(commanderCollection, commander);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Commander to an array that doesn't contain it", () => {
        const commander: ICommander = sampleWithRequiredData;
        const commanderCollection: ICommander[] = [sampleWithPartialData];
        expectedResult = service.addCommanderToCollectionIfMissing(commanderCollection, commander);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commander);
      });

      it('should add only unique Commander to an array', () => {
        const commanderArray: ICommander[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const commanderCollection: ICommander[] = [sampleWithRequiredData];
        expectedResult = service.addCommanderToCollectionIfMissing(commanderCollection, ...commanderArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const commander: ICommander = sampleWithRequiredData;
        const commander2: ICommander = sampleWithPartialData;
        expectedResult = service.addCommanderToCollectionIfMissing([], commander, commander2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(commander);
        expect(expectedResult).toContain(commander2);
      });

      it('should accept null and undefined values', () => {
        const commander: ICommander = sampleWithRequiredData;
        expectedResult = service.addCommanderToCollectionIfMissing([], null, commander, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(commander);
      });

      it('should return initial array if no Commander is added', () => {
        const commanderCollection: ICommander[] = [sampleWithRequiredData];
        expectedResult = service.addCommanderToCollectionIfMissing(commanderCollection, undefined, null);
        expect(expectedResult).toEqual(commanderCollection);
      });
    });

    describe('compareCommander', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCommander(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCommander(entity1, entity2);
        const compareResult2 = service.compareCommander(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCommander(entity1, entity2);
        const compareResult2 = service.compareCommander(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCommander(entity1, entity2);
        const compareResult2 = service.compareCommander(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
