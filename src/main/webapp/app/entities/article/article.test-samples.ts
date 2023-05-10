import dayjs from 'dayjs/esm';

import { IArticle, NewArticle } from './article.model';

export const sampleWithRequiredData: IArticle = {
  id: 61675,
  nom: 'payment Upgradable',
  prixVente: 6828,
  qte: 30399,
  dateExpiration: dayjs('2023-05-09'),
  dateFabrication: dayjs('2023-05-09'),
  prixAchat: 35398,
};

export const sampleWithPartialData: IArticle = {
  id: 50781,
  nom: 'c infomediaries',
  prixVente: 92974,
  qte: 78522,
  dateExpiration: dayjs('2023-05-09'),
  dateFabrication: dayjs('2023-05-09'),
  prixAchat: 81166,
  photoPrincipale: '../fake-data/blob/hipster.png',
  photoPrincipaleContentType: 'unknown',
  photoSecondaire: '../fake-data/blob/hipster.png',
  photoSecondaireContentType: 'unknown',
};

export const sampleWithFullData: IArticle = {
  id: 1201,
  nom: 'Hat application quantifying',
  prixVente: 27371,
  qte: 18682,
  dateExpiration: dayjs('2023-05-09'),
  dateFabrication: dayjs('2023-05-08'),
  codeBar: 'application Aquitaine Market',
  prixAchat: 47221,
  seuilSecurite: 71462,
  seuilMinimal: 41992,
  seuilAlerte: 10925,
  emplacement: 'b system',
  description: 'cross-media',
  photoPrincipale: '../fake-data/blob/hipster.png',
  photoPrincipaleContentType: 'unknown',
  photoSecondaire: '../fake-data/blob/hipster.png',
  photoSecondaireContentType: 'unknown',
  photoTertaire: '../fake-data/blob/hipster.png',
  photoTertaireContentType: 'unknown',
};

export const sampleWithNewData: NewArticle = {
  nom: 'blue',
  prixVente: 98678,
  qte: 27942,
  dateExpiration: dayjs('2023-05-09'),
  dateFabrication: dayjs('2023-05-08'),
  prixAchat: 16994,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
