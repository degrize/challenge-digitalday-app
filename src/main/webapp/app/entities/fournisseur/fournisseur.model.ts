import { IQuartier } from 'app/entities/quartier/quartier.model';

export interface IFournisseur {
  id: number;
  nom?: string | null;
  prenom?: string | null;
  contact?: string | null;
  quartier?: Pick<IQuartier, 'id' | 'nom'> | null;
}

export type NewFournisseur = Omit<IFournisseur, 'id'> & { id: null };
