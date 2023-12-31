import { Component, OnInit, NgZone } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http'
import swal from 'sweetalert2';
import { Clipboard } from '@angular/cdk/clipboard';
import { MatTableDataSource } from '@angular/material/table';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';

export interface IStatus {
  statusCode: string;
  statusDescEn: string;
  statusDescTh: string;
}

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit {

  orderRef: any;
  orderId: any;
  orderDetailRes: any;
  statusAll: any;
  statusSelect: any;
  statusOld: any;
  statusDataSource: any;

  updateStatusFrom = new FormGroup({
    orderId: new FormControl(''),
    statusCode: new FormControl(''),
  })

  constructor(
    private crudService: CrudService,
    private router: Router,
    private ngZone: NgZone,
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private activeatedRoute: ActivatedRoute,
    private clipboard: Clipboard,
    private snackBar: MatSnackBar
  ) {
    this.orderRef = this.activeatedRoute.snapshot.paramMap.get('orderRef');
    this.updateStatusFrom = this.formBuilder.group({
      orderId: [''],
      statusCode: [''],
    })

  }

  ngOnInit(): void {
    this.crudService.getOrderDetail(this.orderRef).subscribe(res => {
      this.orderDetailRes = res;
      this.statusSelect = this.orderDetailRes.status_code
      this.statusOld = this.orderDetailRes.status_code
      this.orderId = this.orderDetailRes.order_id
      console.log(this.orderDetailRes)
    })
    this.crudService.getStatusUpdate().subscribe(res => {
      this.statusAll = res;
      this.statusDataSource = new MatTableDataSource(res);
      this.statusDataSource.filterPredicate = (data: IStatus, filter: string) => {
        return data.statusCode == filter;
      };
    })
  }

  updateStatus() {
    this.updateStatusFrom = this.formBuilder.group({
      orderId: [this.orderId],
      statusCode: [this.statusSelect],
    })
    this.statusDataSource.filter = this.statusSelect;
    var message = this.statusDataSource.filteredData[0].statusDescEn + " (" + this.statusDataSource.filteredData[0].statusDescTh + ")"
    swal.fire({
      title: 'Update order status ?',
      text: message,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Confirm',
      cancelButtonText: 'Cancel',
    }).then((result) => {
      if (result.value) {
        // this.crudService.deleteCustomer(id).subscribe(() => {
        //   console.log("Customer removed successfully.");
        //   swal.fire('Removed!', 'Customer removed successfully.', 'success');
        //   window.location.reload();
        // })
      } else if (result.dismiss === swal.DismissReason.cancel) {
        this.statusSelect = this.statusOld
      }
    });
  }

  copyOrderRef() {
    this.clipboard.copy(this.orderRef);
    this.openSnackBar()
  }

  addComma(value: any) {
    return value.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",")
  }

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'bottom';
  openSnackBar() {
    this.snackBar.open('Copy Order No. succeed', 'Done', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      duration: 1500,
    });
  }
}
