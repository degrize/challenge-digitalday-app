import dayjs from 'dayjs/esm';

import { IVente, NewVente } from './vente.model';

export const sampleWithRequiredData: IVente = {
  id: 96992,
};

export const sampleWithPartialData: IVente = {
  id: 13697,
  dateVente: dayjs('2023-05-08T17:18'),
  remiseRabais: 58597,
  montantRecu: 98940,
  aCredit: false,
};

export const sampleWithFullData: IVente = {
  id: 41297,
  dateVente: dayjs('2023-05-08T21:58'),
  remiseRabais: 42177,
  montantRecu: 39835,
  aCredit: false,
};

export const sampleWithNewData: NewVente = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
