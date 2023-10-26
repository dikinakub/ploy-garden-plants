import { Component, OnInit } from '@angular/core';
import { CrudService } from '../service/crud.service';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})
export class CustomerListComponent implements OnInit {

  displayedColumns: string[] = ['no', 'profileName', 'addressName', 'addressDetail', 'phoneNumber1', 'phoneNumber2', 'edit'];
  getName: String = "";
  dataSource: any;

  constructor(private crudService: CrudService) { }

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
}
