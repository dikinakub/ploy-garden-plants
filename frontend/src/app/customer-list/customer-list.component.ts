import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { MatTableDataSource } from '@angular/material/table';
import swal from 'sweetalert2';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})
export class CustomerListComponent implements OnInit {

  // displayedColumns: string[] = ['no', 'profileName', 'addressName', 'addressDetail', 'phoneNumber1', 'phoneNumber2', 'edit', 'delete'];
  getName: String = "";
  // dataSource: any;
  customerList: any;
  selectedOption: number;

  constructor(private crudService: CrudService, private ngZone: NgZone, private router: Router, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.crudService.getCustomers().subscribe(res => {
      // this.dataSource = new MatTableDataSource(res);
      this.customerList = res;
      // console.log(this.customerList)
    })
  }

  onSearch(name: String): any {
    this.crudService.getCustomerByName(name).subscribe(res => {
      // this.dataSource = new MatTableDataSource(res);
      this.customerList = res;
      // console.log(this.dataSource.data)
    })
  }

  clearData(): void {
    this.getName = "";
    this.crudService.getCustomers().subscribe(res => {
      // this.dataSource = new MatTableDataSource(res);
      this.customerList = res;
      // console.log(this.dataSource.data)
    })
  }

  onEdit(id: any) {
    this.ngZone.run(() => this.router.navigateByUrl('customer-edit'))
  }

  onDelete(id: any) {
    swal.fire({
      title: 'Are you sure?',
      text: 'This process is irreversible.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, go ahead.',
      cancelButtonText: 'No, let me think',
    }).then((result) => {
      if (result.value) {
        this.crudService.deleteCustomer(id).subscribe(() => {
          console.log("Customer removed successfully.");
          swal.fire('Removed!', 'Customer removed successfully.', 'success');
          window.location.reload();
        })
      } else if (result.dismiss === swal.DismissReason.cancel) {
        this.ngZone.run(() => this.router.navigateByUrl('customer-list'))
      }
    });
  }

  updateCustomerDefaultFlag(addressId: any): void {
    this.crudService.updateCustomerDefaultFlag(addressId.value)
      .subscribe(() => {
        console.log("Update customer default flag successfully.", addressId.value);
        this.openSnackBar();
      })
  }

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'bottom';
  openSnackBar() {
    this.snackBar.open('เปลี่ยนที่อยู่สำเร็จ', 'Done', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      duration: 2 * 1000,
    });
  }

  setSessionStorage() {
    sessionStorage.setItem('BackToPage', '/customer-list');
  }
}
