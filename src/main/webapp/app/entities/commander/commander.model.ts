import dayjs from 'dayjs/esm';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { IArticle } from 'app/entities/article/article.model';

export interface ICommander {
  id: number;
  dateCommande?: dayjs.Dayjs | null;
  montantAchat?: number | null;
  aCredit?: boolean | null;
  fournisseur?: Pick<IFournisseur, 'id'> | null;
  article?: Pick<IArticle, 'id'> | null;
}

export type NewCommander = Omit<ICommander, 'id'> & { id: null };
