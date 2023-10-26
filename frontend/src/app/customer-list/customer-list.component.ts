import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { MatTableDataSource } from '@angular/material/table';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})
export class CustomerListComponent implements OnInit {

  displayedColumns: string[] = ['no', 'profileName', 'addressName', 'addressDetail', 'phoneNumber1', 'phoneNumber2', 'edit', 'delete'];
  getName: String = "";
  dataSource: any;

  constructor(private crudService: CrudService, private ngZone: NgZone, private router: Router) { }

  ngOnInit(): void {
    this.crudService.getCustomers().subscribe(res => {
      // console.log(res)
      this.dataSource = new MatTableDataSource(res);
      console.log(this.dataSource.data)
    })
  }

  onSearch(name: String): any {
    this.crudService.getCustomerByName(name).subscribe(res => {
      this.dataSource = new MatTableDataSource(res);
      console.log(this.dataSource.data)
      // this.ngZone.run(() => this.router.navigateByUrl('customer-list'))
    })
  }

  clearData(): void {
    this.getName = "";
    this.crudService.getCustomers().subscribe(res => {
      // console.log(res)
      this.dataSource = new MatTableDataSource(res);
      console.log(this.dataSource.data)
    })
  }

  onDelete(id: any) {
    Swal.fire({
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
          Swal.fire('Removed!', 'Customer removed successfully.', 'success');
          window.location.reload();
        })
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        this.ngZone.run(() => this.router.navigateByUrl('customer-list'))
      }
    });
  }
}
