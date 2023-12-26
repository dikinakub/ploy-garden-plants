import { Component, OnInit, NgZone, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import Swal from 'sweetalert2';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatSnackBar, } from '@angular/material/snack-bar';
import { HttpClient } from '@angular/common/http'
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {

  displayedColumns: string[] = ['no', 'reference_no', 'customer_name', 'purchase_price', 'selling_price', 'shipping_price', 'discount_price', 'deposit', 'amount', 'status_desc'];
  dataSource: any;

  searchFrom = new FormGroup({
    page: new FormControl(''),
    pageSize: new FormControl(''),
    field: new FormControl(''),
    order: new FormControl(''),
  })

  constructor(
    private crudService: CrudService,
    private ngZone: NgZone,
    private router: Router,
    private snackBar: MatSnackBar,
    private formBuilder: FormBuilder,
    private http: HttpClient) {
    this.searchFrom = this.formBuilder.group({
      page: ['1'],
      pageSize: ['10'],
      field: [''],
      order: [''],
    })
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  ngOnInit(): void {
    this.searchOrderList();
  }

  searchOrderList() {
    this.crudService.searchOrderList(this.searchFrom.value).subscribe(res => {
      console.log(res)
      this.dataSource = new MatTableDataSource(res.items);

      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    })
  }

  sortChange() {
    // console.log(this.paginator)
    // console.log(this.paginator.pageIndex)
    // console.log(this.paginator.pageSize)

    console.log(this.sort.active)
    console.log(this.sort.direction)

    this.searchFrom.patchValue({
      field: this.sort.active,
      order: this.sort.direction
    })
    // this.searchOrderList();
  }

  addComma(value: any) {
    return value.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",")
  }

  setSessionStorage() {
    sessionStorage.setItem('BackToPage', '/order-list');
  }

}
