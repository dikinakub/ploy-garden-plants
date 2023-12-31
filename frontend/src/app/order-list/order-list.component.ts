import { Component, OnInit, ViewChild } from '@angular/core';
import { CrudService } from '../service/crud.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { FormGroup, FormBuilder, FormControl, FormArray } from '@angular/forms';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {

  displayedColumns: string[] = ['no', 'reference_no', 'customer_name', 'purchase_price', 'selling_price', 'shipping_price', 'discount_price', 'deposit', 'amount', 'status_desc'];
  dataSource: any;
  statusAll: any;

  orderRef: any;
  customerName: any;
  status: any;

  searchFrom = new FormGroup({
    page: new FormControl(''),
    pageSize: new FormControl(''),
    field: new FormControl(''),
    order: new FormControl(''),
    searchList: this.formBuilder.array([]),
  })

  constructor(
    private crudService: CrudService,
    private formBuilder: FormBuilder) {
    this.searchFrom = this.formBuilder.group({
      page: ['1'],
      pageSize: ['10'],
      field: new FormControl(''),
      order: new FormControl(''),
      searchList: this.formBuilder.array([]),
    })
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  ngOnInit(): void {
    this.searchOrderByCriteria()
    this.crudService.getStatusAll().subscribe(res => {
      this.statusAll = res;
      // console.log(res)
    })
  }

  searchOrderByCriteria() {
    this.crudService.searchOrderList(this.searchFrom.value).subscribe(res => {
      // console.log(res)
      this.dataSource = new MatTableDataSource(res.items);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  searchOrderList() {
    if (this.orderRef) {
      this.searchList.push(this.formBuilder.group({
        field: ['reference_no'],
        value: [this.orderRef],
      }));
    }
    if (this.customerName) {
      this.searchList.push(this.formBuilder.group({
        field: ['customer_name'],
        value: [this.customerName],
      }));
    }
    if (this.status) {
      this.searchList.push(this.formBuilder.group({
        field: ['status_code'],
        value: [this.status],
      }));
    }
    // console.log(this.searchFrom.value)
    this.searchOrderByCriteria()
  }

  clearDateInput() {
    this.orderRef = ''
    this.customerName = ''
    this.status = ''
    this.searchFrom = this.formBuilder.group({
      page: ['1'],
      pageSize: ['10'],
      field: new FormControl(''),
      order: new FormControl(''),
      searchList: this.formBuilder.array([]),
    })
    this.searchOrderByCriteria()
  }

  newSearchList(): FormGroup {
    return this.formBuilder.group({
      field: [],
      value: [],
    })
  }

  get searchList() {
    return this.searchFrom.controls["searchList"] as FormArray;
  }


  addComma(value: any) {
    return value.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",")
  }

  setSessionStorage() {
    sessionStorage.setItem('BackToPage', '/order-list');
  }

}
