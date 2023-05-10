import { IArticle } from 'app/entities/article/article.model';

export interface ICategorieArticle {
  id: number;
  nom?: string | null;
  description?: string | null;
  articles?: Pick<IArticle, 'id'>[] | null;
}

export type NewCategorieArticle = Omit<ICategorieArticle, 'id'> & { id: null };
