import { Component, OnInit, NgZone } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http'
import Swal from 'sweetalert2';
import { Clipboard } from '@angular/cdk/clipboard';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit {

  orderRef: any;
  orderDetailRes: any;

  hide = true;
  copyTip: any = "Copy Order No.";

  orderDetailForm = new FormGroup({
    status_desc: new FormControl(''),
    // customerId: new FormControl(''),
    // facebookUrl: new FormControl(''),
    // address: new FormControl(''),
    // deposit: new FormControl(''),
    // orderDetail: this.formBuilder.array([]),
  })

  constructor(
    private crudService: CrudService,
    private router: Router,
    private ngZone: NgZone,
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private activeatedRoute: ActivatedRoute,
    private clipboard: Clipboard
  ) {
    this.orderRef = this.activeatedRoute.snapshot.paramMap.get('orderRef');
  }

  ngOnInit(): void {
    this.crudService.getOrderDetail(this.orderRef).subscribe(res => {
      this.orderDetailRes = res;
      // console.log(this.orderDetailRes)
    })
  }

  copyOrderRef() {
    this.clipboard.copy(this.orderRef);
    if (this.hide) {
      this.copyTip = "Done"
    } else {
      this.copyTip = "Copy Order No."
    }
  }

  addComma(value: any) {
    return value.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",")
  }
}
