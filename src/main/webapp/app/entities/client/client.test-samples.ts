import { Sexe } from 'app/entities/enumerations/sexe.model';

import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 71655,
  nom: 'hacking',
  prenom: 'primary Poitou-Charentes Concrete',
};

export const sampleWithPartialData: IClient = {
  id: 51037,
  nom: 'programming homogeneous',
  prenom: 'Bedfordshire',
  contact: "d'Azur synthesize",
};

export const sampleWithFullData: IClient = {
  id: 588,
  nom: 'input',
  prenom: 'Autriche',
  contact: 'index Assistant',
  adresse: 'seamless initiatives',
  sexe: Sexe['M'],
};

export const sampleWithNewData: NewClient = {
  nom: 'Automotive Shirt Soft',
  prenom: 'definition Synergized a',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
