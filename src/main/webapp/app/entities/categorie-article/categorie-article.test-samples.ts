import { ICategorieArticle, NewCategorieArticle } from './categorie-article.model';

export const sampleWithRequiredData: ICategorieArticle = {
  id: 6324,
  nom: 'Market',
};

export const sampleWithPartialData: ICategorieArticle = {
  id: 8478,
  nom: 'Programmable orchid Huchette',
  description: 'invoice front-end',
};

export const sampleWithFullData: ICategorieArticle = {
  id: 19630,
  nom: 'invoice uniform',
  description: 'a',
};

export const sampleWithNewData: NewCategorieArticle = {
  nom: 'partnerships Ball Uruguayo',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
