import dayjs from 'dayjs/esm';

import { ICommander, NewCommander } from './commander.model';

export const sampleWithRequiredData: ICommander = {
  id: 92707,
};

export const sampleWithPartialData: ICommander = {
  id: 26416,
  dateCommande: dayjs('2023-05-09T06:08'),
};

export const sampleWithFullData: ICommander = {
  id: 10056,
  dateCommande: dayjs('2023-05-09T01:41'),
  montantAchat: 47286,
  aCredit: true,
};

export const sampleWithNewData: NewCommander = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
