import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategorieArticle, NewCategorieArticle } from '../categorie-article.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategorieArticle for edit and NewCategorieArticleFormGroupInput for create.
 */
type CategorieArticleFormGroupInput = ICategorieArticle | PartialWithRequiredKeyOf<NewCategorieArticle>;

type CategorieArticleFormDefaults = Pick<NewCategorieArticle, 'id' | 'articles'>;

type CategorieArticleFormGroupContent = {
  id: FormControl<ICategorieArticle['id'] | NewCategorieArticle['id']>;
  nom: FormControl<ICategorieArticle['nom']>;
  description: FormControl<ICategorieArticle['description']>;
  articles: FormControl<ICategorieArticle['articles']>;
};

export type CategorieArticleFormGroup = FormGroup<CategorieArticleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategorieArticleFormService {
  createCategorieArticleFormGroup(categorieArticle: CategorieArticleFormGroupInput = { id: null }): CategorieArticleFormGroup {
    const categorieArticleRawValue = {
      ...this.getFormDefaults(),
      ...categorieArticle,
    };
    return new FormGroup<CategorieArticleFormGroupContent>({
      id: new FormControl(
        { value: categorieArticleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nom: new FormControl(categorieArticleRawValue.nom, {
        validators: [Validators.required],
      }),
      description: new FormControl(categorieArticleRawValue.description),
      articles: new FormControl(categorieArticleRawValue.articles ?? []),
    });
  }

  getCategorieArticle(form: CategorieArticleFormGroup): ICategorieArticle | NewCategorieArticle {
    return form.getRawValue() as ICategorieArticle | NewCategorieArticle;
  }

  resetForm(form: CategorieArticleFormGroup, categorieArticle: CategorieArticleFormGroupInput): void {
    const categorieArticleRawValue = { ...this.getFormDefaults(), ...categorieArticle };
    form.reset(
      {
        ...categorieArticleRawValue,
        id: { value: categorieArticleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CategorieArticleFormDefaults {
    return {
      id: null,
      articles: [],
    };
  }
}
