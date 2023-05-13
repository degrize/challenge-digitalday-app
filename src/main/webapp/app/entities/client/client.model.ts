import { IQuartier } from 'app/entities/quartier/quartier.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';

export interface IClient {
  id: number;
  nom?: string | null;
  prenom?: string | null;
  contact?: string | null;
  adresse?: string | null;
  sexe?: Sexe | null;
  fidelite?: number | null;
  quartier?: Pick<IQuartier, 'id' | 'nom'> | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
