import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IArticle, NewArticle } from '../article.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IArticle for edit and NewArticleFormGroupInput for create.
 */
type ArticleFormGroupInput = IArticle | PartialWithRequiredKeyOf<NewArticle>;

type ArticleFormDefaults = Pick<NewArticle, 'id' | 'categorieArticles'>;

type ArticleFormGroupContent = {
  id: FormControl<IArticle['id'] | NewArticle['id']>;
  nom: FormControl<IArticle['nom']>;
  prixVente: FormControl<IArticle['prixVente']>;
  qte: FormControl<IArticle['qte']>;
  dateExpiration: FormControl<IArticle['dateExpiration']>;
  dateFabrication: FormControl<IArticle['dateFabrication']>;
  codeBar: FormControl<IArticle['codeBar']>;
  prixAchat: FormControl<IArticle['prixAchat']>;
  seuilSecurite: FormControl<IArticle['seuilSecurite']>;
  seuilMinimal: FormControl<IArticle['seuilMinimal']>;
  seuilAlerte: FormControl<IArticle['seuilAlerte']>;
  emplacement: FormControl<IArticle['emplacement']>;
  description: FormControl<IArticle['description']>;
  photoPrincipale: FormControl<IArticle['photoPrincipale']>;
  photoPrincipaleContentType: FormControl<IArticle['photoPrincipaleContentType']>;
  photoSecondaire: FormControl<IArticle['photoSecondaire']>;
  photoSecondaireContentType: FormControl<IArticle['photoSecondaireContentType']>;
  photoTertaire: FormControl<IArticle['photoTertaire']>;
  photoTertaireContentType: FormControl<IArticle['photoTertaireContentType']>;
  categorieArticles: FormControl<IArticle['categorieArticles']>;
};

export type ArticleFormGroup = FormGroup<ArticleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ArticleFormService {
  createArticleFormGroup(article: ArticleFormGroupInput = { id: null }): ArticleFormGroup {
    const articleRawValue = {
      ...this.getFormDefaults(),
      ...article,
    };
    return new FormGroup<ArticleFormGroupContent>({
      id: new FormControl(
        { value: articleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nom: new FormControl(articleRawValue.nom, {
        validators: [Validators.required],
      }),
      prixVente: new FormControl(articleRawValue.prixVente, {
        validators: [Validators.required],
      }),
      qte: new FormControl(articleRawValue.qte, {
        validators: [Validators.required],
      }),
      dateExpiration: new FormControl(articleRawValue.dateExpiration, {
        validators: [Validators.required],
      }),
      dateFabrication: new FormControl(articleRawValue.dateFabrication, {
        validators: [Validators.required],
      }),
      codeBar: new FormControl(articleRawValue.codeBar),
      prixAchat: new FormControl(articleRawValue.prixAchat, {
        validators: [Validators.required],
      }),
      seuilSecurite: new FormControl(articleRawValue.seuilSecurite),
      seuilMinimal: new FormControl(articleRawValue.seuilMinimal),
      seuilAlerte: new FormControl(articleRawValue.seuilAlerte),
      emplacement: new FormControl(articleRawValue.emplacement),
      description: new FormControl(articleRawValue.description),
      photoPrincipale: new FormControl(articleRawValue.photoPrincipale),
      photoPrincipaleContentType: new FormControl(articleRawValue.photoPrincipaleContentType),
      photoSecondaire: new FormControl(articleRawValue.photoSecondaire),
      photoSecondaireContentType: new FormControl(articleRawValue.photoSecondaireContentType),
      photoTertaire: new FormControl(articleRawValue.photoTertaire),
      photoTertaireContentType: new FormControl(articleRawValue.photoTertaireContentType),
      categorieArticles: new FormControl(articleRawValue.categorieArticles ?? []),
    });
  }

  getArticle(form: ArticleFormGroup): IArticle | NewArticle {
    return form.getRawValue() as IArticle | NewArticle;
  }

  resetForm(form: ArticleFormGroup, article: ArticleFormGroupInput): void {
    const articleRawValue = { ...this.getFormDefaults(), ...article };
    form.reset(
      {
        ...articleRawValue,
        id: { value: articleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ArticleFormDefaults {
    return {
      id: null,
      categorieArticles: [],
    };
  }
}
