import { Component, OnInit } from '@angular/core';
import { IArticle } from '../entities/article/article.model';
import { IClient } from '../entities/client/client.model';
import { ClientService } from '../entities/client/service/client.service';
import { ArticleService } from '../entities/article/service/article.service';
import { map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { IVente } from '../entities/vente/vente.model';
import * as FileSaver from 'file-saver';

@Component({
  selector: 'jhi-preoccupation',
  templateUrl: './preoccupation.component.html',
  styleUrls: ['./preoccupation.component.scss'],
})
export class PreoccupationComponent implements OnInit {
  vente: IVente | null = null;
  selectedArticle: IArticle | undefined;
  selectedArticleMoinsVendus: IArticle | undefined;
  selectedClient: IClient | undefined;

  clientsSharedCollection: IClient[] = [];
  articlesSharedCollection: IArticle[] = [];
  articlesMoinsVendusSharedCollection: IArticle[] = [];

  cols: any[] = [];
  colsArticle: any[] = [];

  exportColumns: any[] = [];
  exportColumnsArticle: any[] = [];
  exportColumnsArticleMoinsVendu: any[] = [];
  constructor(protected clientService: ClientService, protected articleService: ArticleService) {}

  ngOnInit(): void {
    this.loadRelationshipsOptions();

    this.cols = [
      { field: 'id', header: 'ID', customExportHeader: 'Code Client' },
      { field: 'nom', header: 'Nom' },
      { field: 'prenom', header: 'Prenom' },
      { field: 'contact', header: 'contact' },
      { field: 'quartier', header: 'quartier' },
      { field: 'sexe', header: 'Sexe' },
      { field: 'fidelite', header: "Nombre d'article Achete" },
    ];

    this.colsArticle = [
      { field: 'id', header: 'ID', customExportHeader: 'Code Article' },
      { field: 'nom', header: 'Nom' },
      { field: 'prixVente', header: 'prix de vente' },
      { field: 'qte', header: 'Quantite' },
      { field: 'emplacement', header: 'emplacement' },
      { field: 'prixAchat', header: "prix d'achat" },
      { field: 'dateExpiration', header: 'date Expiration' },
      { field: 'dateFabrication', header: 'date Fabrication' },
    ];

    this.exportColumns = this.cols.map(col => ({ title: col.header, dataKey: col.field }));
    this.exportColumnsArticle = this.colsArticle.map(col => ({ title: col.header, dataKey: col.field }));
    this.exportColumnsArticleMoinsVendu = this.colsArticle.map(col => ({ title: col.header, dataKey: col.field }));
  }

  exportExcel() {
    import('xlsx').then(xlsx => {
      const worksheet = xlsx.utils.json_to_sheet(this.clientsSharedCollection);
      const workbook = { Sheets: { data: worksheet }, SheetNames: ['data'] };
      const excelBuffer: any = xlsx.write(workbook, { bookType: 'xlsx', type: 'array' });
      this.saveAsExcelFile(excelBuffer, 'products');
    });
  }

  saveAsExcelFile(buffer: any, fileName: string): void {
    let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
    let EXCEL_EXTENSION = '.xlsx';
    const data: Blob = new Blob([buffer], {
      type: EXCEL_TYPE,
    });
    FileSaver.saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
  }

  protected loadRelationshipsOptions(): void {
    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing<IClient>(clients, this.vente?.client)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));

    this.articleService
      .query()
      .pipe(map((res: HttpResponse<IArticle[]>) => res.body ?? []))
      .pipe(map((articles: IArticle[]) => this.articleService.addArticleToCollectionIfMissing<IArticle>(articles, this.vente?.article)))
      .subscribe((articles: IArticle[]) => {
        this.articlesSharedCollection = articles;
        this.articlesMoinsVendusSharedCollection = articles.reverse();
      });
  }
}
