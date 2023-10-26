import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { CustomerListComponent } from './customer-list/customer-list.component';
import { OrderListComponent } from './order-list/order-list.component';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatSortModule } from '@angular/material/sort';
import { FormsModule , ReactiveFormsModule} from '@angular/forms';
import { CustomerAddComponent } from './customer-add/customer-add.component';

@NgModule({
  declarations: [
    AppComponent,
    CustomerListComponent,
    OrderListComponent,
    CustomerAddComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    RouterModule,
    HttpClientModule,
    MatTableModule,
    CommonModule,
    MatInputModule,
    MatSelectModule,
    MatCardModule,
    MatSortModule,
    FormsModule,
    ReactiveFormsModule     
  ],
  exports: [
    MatTableModule,
    MatInputModule,
    MatSelectModule,
    MatCardModule,
    MatSortModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
