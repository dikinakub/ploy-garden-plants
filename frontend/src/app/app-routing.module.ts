import { NgModule } from '@angular/core';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { CustomerListComponent } from './customer-list/customer-list.component';
import { CustomerAddComponent } from './customer-add/customer-add.component';
import { OrderListComponent } from './order-list/order-list.component';
import { CustomerEditComponent } from './customer-edit/customer-edit.component';
import { StockListComponent } from './stock-list/stock-list.component';
import { StockAddComponent } from './stock-add/stock-add.component';
import { StockEditComponent } from './stock-edit/stock-edit.component';
import { OrderAddComponent } from './order-add/order-add.component';
import { OrderDetailComponent } from './order-detail/order-detail.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'order-list' },
  { path: 'order-list', component: OrderListComponent },
  { path: 'order-detail/:orderRef', component: OrderDetailComponent },
  { path: 'order-add', component: OrderAddComponent },
  { path: 'customer-list', component: CustomerListComponent },
  { path: 'customer-add', component: CustomerAddComponent },
  { path: 'customer-edit/:profileID', component: CustomerEditComponent },
  { path: 'stock-list', component: StockListComponent },
  { path: 'stock-add', component: StockAddComponent },
  { path: 'stock-edit/:stockId', component: StockEditComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [{ provide: LocationStrategy, useClass: HashLocationStrategy }],
})
export class AppRoutingModule { }
