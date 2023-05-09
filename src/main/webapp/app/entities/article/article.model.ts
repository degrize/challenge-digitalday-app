import dayjs from 'dayjs/esm';
import { ICategorieArticle } from 'app/entities/categorie-article/categorie-article.model';

export interface IArticle {
  id: number;
  nom?: string | null;
  prixVente?: number | null;
  qte?: number | null;
  dateExpiration?: dayjs.Dayjs | null;
  dateFabrication?: dayjs.Dayjs | null;
  codeBar?: string | null;
  prixAchat?: number | null;
  seuilSecurite?: number | null;
  seuilMinimal?: number | null;
  seuilAlerte?: number | null;
  emplacement?: string | null;
  description?: string | null;
  photoPrincipale?: string | null;
  photoPrincipaleContentType?: string | null;
  photoSecondaire?: string | null;
  photoSecondaireContentType?: string | null;
  photoTertaire?: string | null;
  photoTertaireContentType?: string | null;
  categorieArticles?: Pick<ICategorieArticle, 'id' | 'nom'>[] | null;
}

export type NewArticle = Omit<IArticle, 'id'> & { id: null };
