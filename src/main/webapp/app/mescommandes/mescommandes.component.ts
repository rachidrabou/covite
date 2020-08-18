import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INotification } from 'app/shared/model/notification.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { NotificationService } from '../entities/notification/notification.service';
import { NotificationDeleteDialogComponent } from '../entities/notification/notification-delete-dialog.component';

import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-mescommandes',
  templateUrl: './mescommandes.component.html',
  styleUrls: ['./mescommandes.component.scss']
})
export class MescommandesComponent implements OnInit {
  notifications: INotification[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  account!: Account;
  authSubscription?: Subscription;

  constructor(
    protected notificationService: NotificationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    private accountService: AccountService
  ) {
    this.notifications = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.notificationService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<INotification[]>) => this.paginateNotifications(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.notifications = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNotifications();
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account as Account));
  }

  trackId(index: number, item: INotification): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNotifications(): void {
    this.eventSubscriber = this.eventManager.subscribe('notificationListModification', () => this.reset());
  }

  delete(notification: INotification): void {
    const modalRef = this.modalService.open(NotificationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.notification = notification;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateNotifications(data: INotification[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.notifications.push(data[i]);
      }
    }
  }
}
