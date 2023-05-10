import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../commander.test-samples';

import { CommanderFormService } from './commander-form.service';

describe('Commander Form Service', () => {
  let service: CommanderFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommanderFormService);
  });

  describe('Service methods', () => {
    describe('createCommanderFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCommanderFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dateCommande: expect.any(Object),
            montantAchat: expect.any(Object),
            aCredit: expect.any(Object),
            fournisseur: expect.any(Object),
            article: expect.any(Object),
          })
        );
      });

      it('passing ICommander should create a new form with FormGroup', () => {
        const formGroup = service.createCommanderFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dateCommande: expect.any(Object),
            montantAchat: expect.any(Object),
            aCredit: expect.any(Object),
            fournisseur: expect.any(Object),
            article: expect.any(Object),
          })
        );
      });
    });

    describe('getCommander', () => {
      it('should return NewCommander for default Commander initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCommanderFormGroup(sampleWithNewData);

        const commander = service.getCommander(formGroup) as any;

        expect(commander).toMatchObject(sampleWithNewData);
      });

      it('should return NewCommander for empty Commander initial value', () => {
        const formGroup = service.createCommanderFormGroup();

        const commander = service.getCommander(formGroup) as any;

        expect(commander).toMatchObject({});
      });

      it('should return ICommander', () => {
        const formGroup = service.createCommanderFormGroup(sampleWithRequiredData);

        const commander = service.getCommander(formGroup) as any;

        expect(commander).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICommander should not enable id FormControl', () => {
        const formGroup = service.createCommanderFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCommander should disable id FormControl', () => {
        const formGroup = service.createCommanderFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
