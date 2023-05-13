import dayjs from 'dayjs/esm';
import { IClient } from 'app/entities/client/client.model';
import { IArticle } from 'app/entities/article/article.model';

export interface IVente {
  id: number;
  dateVente?: dayjs.Dayjs | null;
  remiseRabais?: number | null;
  montantRecu?: number | null;
  aCredit?: boolean | null;
  qte?: number | null;
  client?: Pick<IClient, 'id' | 'nom'> | null;
  article?: Pick<IArticle, 'id' | 'nom'> | null;
}

export type NewVente = Omit<IVente, 'id'> & { id: null };
