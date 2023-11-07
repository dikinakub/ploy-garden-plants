import { Component, OnInit, NgZone, ViewChild, ElementRef } from '@angular/core';
import { Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder, FormControl, FormArray, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http'
import Swal from 'sweetalert2';

@Component({
  selector: 'app-order-add',
  templateUrl: './order-add.component.html',
  styleUrls: ['./order-add.component.css']
})
export class OrderAddComponent implements OnInit {

  orderSelect: any[];
  getName: String = "";
  dataSource: any;
  checkOrderDetail: any;

  orderForm = new FormGroup({
    customerName: new FormControl(''),
    facebookUrl: new FormControl(''),
    orderDetail: this.formBuilder.array([]),
    // purchasePrice: new FormControl(''),
    // sellingPrice: new FormControl(''),
    // remaining: new FormControl(''),
    // type: new FormControl(''),
    // description: new FormControl(''),
  })

  constructor(
    private crudService: CrudService,
    private router: Router,
    private ngZone: NgZone,
    private formBuilder: FormBuilder,
    private http: HttpClient
  ) {
    this.orderForm = this.formBuilder.group({
      customerName: [''],
      facebookUrl: [''],
      orderDetail: this.formBuilder.array([]),
      // purchasePrice: [''],
      // sellingPrice: [''],
      // remaining: [''],
      // type: [''],
      // description: [''],
    })
  }

  ngOnInit(): void {
    this.addLesson();
    this.crudService.getStockByType("TREE").subscribe(res => {
      this.orderSelect = res;
      // console.log(this.orderList)
    })

    // this.orderDetail.valueChanges
    //   .subscribe(() => {
    //     console.log(this.orderForm.value);
    //   });
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
    })
  }

  addLesson() {
    this.orderDetail.push(this.neworderDetail());
  }

  deleteLesson(index: number) {
    this.orderDetail.removeAt(index);
  }

  checkOrderData(): any {
    let API_URL = `${this.crudService.REST_API}/order/checkOrderData`;
    return this.http.post(API_URL, this.orderForm.value)
      .subscribe((res) => {
        this.checkOrderDetail = res;
        console.log(this.checkOrderDetail);
        // swal.fire('SUCCESS', 'Add customer successfully.', 'success');
        // this.ngZone.run(() => this.router.navigateByUrl('customer-list'))
      }, (err) => {
        console.log(err)
        // swal.fire('ERROR', err.error, 'warning');
      })

  }

}
