import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerListComponent } from './customer-list/customer-list.component';
import { CustomerAddComponent } from './customer-add/customer-add.component';
import { OrderListComponent } from './order-list/order-list.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'customer-list' },
  { path: 'order-list', component: OrderListComponent },
  { path: 'customer-list', component: CustomerListComponent },
  { path: 'customer-add', component: CustomerAddComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
