import { IQuartier, NewQuartier } from './quartier.model';

export const sampleWithRequiredData: IQuartier = {
  id: 36622,
  nom: 'a',
};

export const sampleWithPartialData: IQuartier = {
  id: 16405,
  nom: 'Wooden',
};

export const sampleWithFullData: IQuartier = {
  id: 57192,
  nom: 'input Concrete withdrawal',
};

export const sampleWithNewData: NewQuartier = {
  nom: 'calculate red Oberkampf',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
