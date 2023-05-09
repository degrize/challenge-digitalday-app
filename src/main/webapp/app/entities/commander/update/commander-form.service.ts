import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICommander, NewCommander } from '../commander.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICommander for edit and NewCommanderFormGroupInput for create.
 */
type CommanderFormGroupInput = ICommander | PartialWithRequiredKeyOf<NewCommander>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICommander | NewCommander> = Omit<T, 'dateCommande'> & {
  dateCommande?: string | null;
};

type CommanderFormRawValue = FormValueOf<ICommander>;

type NewCommanderFormRawValue = FormValueOf<NewCommander>;

type CommanderFormDefaults = Pick<NewCommander, 'id' | 'dateCommande' | 'aCredit'>;

type CommanderFormGroupContent = {
  id: FormControl<CommanderFormRawValue['id'] | NewCommander['id']>;
  dateCommande: FormControl<CommanderFormRawValue['dateCommande']>;
  montantAchat: FormControl<CommanderFormRawValue['montantAchat']>;
  aCredit: FormControl<CommanderFormRawValue['aCredit']>;
  fournisseur: FormControl<CommanderFormRawValue['fournisseur']>;
  article: FormControl<CommanderFormRawValue['article']>;
};

export type CommanderFormGroup = FormGroup<CommanderFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CommanderFormService {
  createCommanderFormGroup(commander: CommanderFormGroupInput = { id: null }): CommanderFormGroup {
    const commanderRawValue = this.convertCommanderToCommanderRawValue({
      ...this.getFormDefaults(),
      ...commander,
    });
    return new FormGroup<CommanderFormGroupContent>({
      id: new FormControl(
        { value: commanderRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dateCommande: new FormControl(commanderRawValue.dateCommande),
      montantAchat: new FormControl(commanderRawValue.montantAchat),
      aCredit: new FormControl(commanderRawValue.aCredit),
      fournisseur: new FormControl(commanderRawValue.fournisseur),
      article: new FormControl(commanderRawValue.article),
    });
  }

  getCommander(form: CommanderFormGroup): ICommander | NewCommander {
    return this.convertCommanderRawValueToCommander(form.getRawValue() as CommanderFormRawValue | NewCommanderFormRawValue);
  }

  resetForm(form: CommanderFormGroup, commander: CommanderFormGroupInput): void {
    const commanderRawValue = this.convertCommanderToCommanderRawValue({ ...this.getFormDefaults(), ...commander });
    form.reset(
      {
        ...commanderRawValue,
        id: { value: commanderRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CommanderFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateCommande: currentTime,
      aCredit: false,
    };
  }

  private convertCommanderRawValueToCommander(rawCommander: CommanderFormRawValue | NewCommanderFormRawValue): ICommander | NewCommander {
    return {
      ...rawCommander,
      dateCommande: dayjs(rawCommander.dateCommande, DATE_TIME_FORMAT),
    };
  }

  private convertCommanderToCommanderRawValue(
    commander: ICommander | (Partial<NewCommander> & CommanderFormDefaults)
  ): CommanderFormRawValue | PartialWithRequiredKeyOf<NewCommanderFormRawValue> {
    return {
      ...commander,
      dateCommande: commander.dateCommande ? commander.dateCommande.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
