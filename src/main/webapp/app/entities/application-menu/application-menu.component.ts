import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IApplicationMenu } from 'app/shared/model/application-menu.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ApplicationMenuService } from './application-menu.service';
import { ApplicationMenuDeleteDialogComponent } from './application-menu-delete-dialog.component';

@Component({
  selector: 'jhi-application-menu',
  templateUrl: './application-menu.component.html'
})
export class ApplicationMenuComponent implements OnInit, OnDestroy {
  applicationMenus: IApplicationMenu[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected applicationMenuService: ApplicationMenuService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.applicationMenus = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.applicationMenuService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IApplicationMenu[]>) => this.paginateApplicationMenus(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.applicationMenus = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInApplicationMenus();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IApplicationMenu): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInApplicationMenus(): void {
    this.eventSubscriber = this.eventManager.subscribe('applicationMenuListModification', () => this.reset());
  }

  delete(applicationMenu: IApplicationMenu): void {
    const modalRef = this.modalService.open(ApplicationMenuDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.applicationMenu = applicationMenu;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateApplicationMenus(data: IApplicationMenu[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.applicationMenus.push(data[i]);
      }
    }
  }
}
