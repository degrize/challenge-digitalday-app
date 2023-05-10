import { IVille } from 'app/entities/ville/ville.model';

export interface IQuartier {
  id: number;
  nom?: string | null;
  villes?: Pick<IVille, 'id' | 'nom'>[] | null;
}

export type NewQuartier = Omit<IQuartier, 'id'> & { id: null };
