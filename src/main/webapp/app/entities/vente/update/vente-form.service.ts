import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVente, NewVente } from '../vente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVente for edit and NewVenteFormGroupInput for create.
 */
type VenteFormGroupInput = IVente | PartialWithRequiredKeyOf<NewVente>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IVente | NewVente> = Omit<T, 'dateVente'> & {
  dateVente?: string | null;
};

type VenteFormRawValue = FormValueOf<IVente>;

type NewVenteFormRawValue = FormValueOf<NewVente>;

type VenteFormDefaults = Pick<NewVente, 'id' | 'dateVente' | 'aCredit' | 'qte'>;

type VenteFormGroupContent = {
  id: FormControl<VenteFormRawValue['id'] | NewVente['id']>;
  dateVente: FormControl<VenteFormRawValue['dateVente']>;
  remiseRabais: FormControl<VenteFormRawValue['remiseRabais']>;
  montantRecu: FormControl<VenteFormRawValue['montantRecu']>;
  aCredit: FormControl<VenteFormRawValue['aCredit']>;
  qte: FormControl<VenteFormRawValue['qte']>;
  client: FormControl<VenteFormRawValue['client']>;
  article: FormControl<VenteFormRawValue['article']>;
};

export type VenteFormGroup = FormGroup<VenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VenteFormService {
  createVenteFormGroup(vente: VenteFormGroupInput = { id: null }): VenteFormGroup {
    const venteRawValue = this.convertVenteToVenteRawValue({
      ...this.getFormDefaults(),
      ...vente,
    });
    return new FormGroup<VenteFormGroupContent>({
      id: new FormControl(
        { value: venteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dateVente: new FormControl(venteRawValue.dateVente),
      remiseRabais: new FormControl(venteRawValue.remiseRabais),
      montantRecu: new FormControl(venteRawValue.montantRecu),
      aCredit: new FormControl(venteRawValue.aCredit),
      client: new FormControl(venteRawValue.client),
      qte: new FormControl(venteRawValue.qte),
      article: new FormControl(venteRawValue.article),
    });
  }

  getVente(form: VenteFormGroup): IVente | NewVente {
    return this.convertVenteRawValueToVente(form.getRawValue() as VenteFormRawValue | NewVenteFormRawValue);
  }

  resetForm(form: VenteFormGroup, vente: VenteFormGroupInput): void {
    const venteRawValue = this.convertVenteToVenteRawValue({ ...this.getFormDefaults(), ...vente });
    form.reset(
      {
        ...venteRawValue,
        id: { value: venteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): VenteFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateVente: currentTime,
      aCredit: false,
      qte: 1,
    };
  }

  private convertVenteRawValueToVente(rawVente: VenteFormRawValue | NewVenteFormRawValue): IVente | NewVente {
    return {
      ...rawVente,
      dateVente: dayjs(rawVente.dateVente, DATE_TIME_FORMAT),
    };
  }

  private convertVenteToVenteRawValue(
    vente: IVente | (Partial<NewVente> & VenteFormDefaults)
  ): VenteFormRawValue | PartialWithRequiredKeyOf<NewVenteFormRawValue> {
    return {
      ...vente,
      dateVente: vente.dateVente ? vente.dateVente.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
