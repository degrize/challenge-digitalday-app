import { IFournisseur, NewFournisseur } from './fournisseur.model';

export const sampleWithRequiredData: IFournisseur = {
  id: 12366,
  nom: 'Hryvnia reinvent radical',
  prenom: 'b compress c',
};

export const sampleWithPartialData: IFournisseur = {
  id: 20523,
  nom: 'Automotive Cambridgeshire',
  prenom: 'HDD interactive Monsieur-le-Prince',
  contact: 'Euro withdrawal',
};

export const sampleWithFullData: IFournisseur = {
  id: 15490,
  nom: 'b navigating Executif',
  prenom: 'overriding Alsace User-centric',
  contact: 'withdrawal SSL RSS',
};

export const sampleWithNewData: NewFournisseur = {
  nom: 'Venezuela Frozen',
  prenom: 'Dollar) payment',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
