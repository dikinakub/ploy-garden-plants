import { Component, OnInit, NgZone, ViewChild, ElementRef } from '@angular/core';
import { Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder, FormControl, FormArray, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http'
import swal from 'sweetalert2';
import { MatTableDataSource } from '@angular/material/table';

export interface Stocks {
  no: any;
  stockId: any;
  stockName: any;
  stockPurchasePrice: number;
  stockSellingPrice: number;
  stockRemaining: number;
  stockDescription: any;
  stockType: any;
}

@Component({
  selector: 'app-order-add',
  templateUrl: './order-add.component.html',
  styleUrls: ['./order-add.component.css']
})
export class OrderAddComponent implements OnInit {

  displayColumns: string[] = ['orderId', 'count', 'shipping', 'discount', 'delete'];
  stockDisplayedColumns: string[] = ['no', 'stockName', 'stockPurchasePrice', 'stockSellingPrice', 'stockRemaining'];
  dataSourceStock: any;
  dataSourceShowStock: any;

  facebookName: any;
  orderSelect: any[];
  checkOrderDetail: any;
  searchStockKey: any;
  depositInput: any;
  totalAmount: any;
  totalAmountDeposit: any;
  curDate = new Date();

  orderForm = new FormGroup({
    customerName: new FormControl(''),
    customerId: new FormControl(''),
    facebookUrl: new FormControl(''),
    address: new FormControl(''),
    deposit: new FormControl(''),
    orderDetail: this.formBuilder.array([]),
  })

  constructor(
    private crudService: CrudService,
    private router: Router,
    private ngZone: NgZone,
    private formBuilder: FormBuilder,
    private http: HttpClient,
  ) {
    this.orderForm = this.formBuilder.group({
      customerName: [''],
      customerId: [''],
      facebookUrl: [''],
      address: [''],
      deposit: ['0'],
      orderDetail: this.formBuilder.array([]),
    })
  }

  ngOnInit(): void {
    this.crudService.getStockByType("TREE").subscribe(res => {
      this.dataSourceStock = new MatTableDataSource(res);
      this.dataSourceStock.filterPredicate = (data: Stocks, filter: string) => {
        return data.stockId == filter;
      };

      this.dataSourceShowStock = new MatTableDataSource(res);
      this.dataSourceShowStock.filterPredicate = (data: Stocks, filter: string) => {
        return data.stockName.includes(filter);
      };
      this.orderSelect = this.dataSourceShowStock.data;

      // console.log(res)
    })
    this.addLesson();
  }

  applyFilter(filterValue: string) {
    this.dataSourceStock.filter = filterValue;
  }

  stockFilter(filterValue: string) {
    this.dataSourceShowStock.filter = filterValue;
  }

  onSearchStock() {
    this.stockFilter(this.searchStockKey)
    this.orderSelect = this.dataSourceShowStock.filteredData;
  }

  clearDataSearchStock(): void {
    this.searchStockKey = "";
    this.stockFilter(this.searchStockKey)
    this.orderSelect = this.dataSourceShowStock.filteredData;
  }

  get orderDetail() {
    return this.orderForm.controls["orderDetail"] as FormArray;
  }

  neworderDetail(): FormGroup {
    return this.formBuilder.group({
      orderId: ['', Validators.required],
      count: [1],
      shipping: [0],
      discount: [0],
      stockName: [''],
      stockPurchasePrice: [''],
      stockSellingPrice: [''],
      amount: [''],
    })
  }

  addLesson() {
    this.orderDetail.push(this.neworderDetail());
  }

  deleteLesson(index: number) {
    this.orderDetail.removeAt(index);
  }

  addComma(value: any) {
    return value.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",")
  }

  checkOrderData(): any {
    let sumAmount = 0;
    for (let order of this.orderDetail.controls) {
      this.applyFilter(order.value.orderId)
      let stockName: String = this.dataSourceStock.filteredData[0].stockName
      let stockPurchasePrice: any = this.dataSourceStock.filteredData[0].stockPurchasePrice;
      let stockSellingPrice: any = this.dataSourceStock.filteredData[0].stockSellingPrice;
      let amount = stockSellingPrice * order.value.count + order.value.shipping - order.value.discount;
      sumAmount = sumAmount + amount
      order.patchValue({
        stockName: stockName,
        stockPurchasePrice: parseFloat(stockPurchasePrice),
        stockSellingPrice: parseFloat(stockSellingPrice),
        amount: amount,
      })
      // console.log(order.value)
      // console.log(this.orderForm.value);
    }
    this.totalAmount = sumAmount;
    this.curDate = new Date();
  }

  checkCustomer(): any {
    this.crudService.checkCustomerByName(this.orderForm.value.customerName).subscribe(res => {
      if (res[0]) {
        // console.log(res[0]);
        const add = res[0]['addressDetailList'][0];
        this.orderForm = this.formBuilder.group({
          customerName: [res[0]['profileName']],
          customerId: [res[0]['profileID']],
          facebookUrl: [res[0]['profileUrl']],
          address: [add['addressName'] + " " + add['addressDetail'] + " " + add['phoneNumber1']],
          deposit: ['0'],
          orderDetail: this.formBuilder.array([]),
        })
      } else {
        this.orderForm = this.formBuilder.group({
          customerName: [this.facebookName],
          customerId: [''],
          facebookUrl: [''],
          address: [''],
          deposit: ['0'],
          orderDetail: this.formBuilder.array([]),
        })
      }
      this.addLesson();
      // console.log(this.orderForm.value);
    })
  }

  sumDeposit(): any {
    this.totalAmountDeposit = this.totalAmount - this.depositInput;
  }

  onSubmit(): any {
    let API_URL = `${this.crudService.REST_API}/order/add`;
    this.orderForm.patchValue({
      deposit: this.depositInput
    })
    return this.http.post(API_URL, this.orderForm.value)
      .subscribe(() => {
        console.log("Add order successfully.", this.orderForm.value);
        swal.fire('SUCCESS', 'Add order successfully.', 'success');
        this.ngZone.run(() => this.router.navigateByUrl('order-list'))
      }, (err) => {
        console.log(err)
        swal.fire('ERROR', err.error, 'warning');
      })
  }

}
