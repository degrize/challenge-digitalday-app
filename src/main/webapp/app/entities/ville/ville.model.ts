import { IQuartier } from 'app/entities/quartier/quartier.model';

export interface IVille {
  id: number;
  nom?: string | null;
  quartiers?: Pick<IQuartier, 'id'>[] | null;
}

export type NewVille = Omit<IVille, 'id'> & { id: null };
