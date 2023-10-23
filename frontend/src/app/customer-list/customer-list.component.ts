import { Component, OnInit, NgZone } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';

export interface Apps {
  empCodeIDxx: number;
  employeeMasterCustomerCode: String;
}

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})
export class CustomerListComponent implements OnInit {

  displayedColumns: string[] = ['no', 'profileName', 'addressName', 'addressDetail', 'phoneNumber1', 'phoneNumber2', 'edit'];
  getName: String = "";
  forms: FormGroup;
  dataSource: any;
  constructor(
    private crudService: CrudService,
    private router: Router,
    private ngZone: NgZone,
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {

    this.forms = this.formBuilder.group({
      no: [''],
      profileName: [''],
      profileUrl: [''],
      addressName: [''],
      addressDetail: [''],
      phoneNumber1: [''],
      phoneNumber2: ['']
    })
  }

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
      this.ngZone.run(() => this.router.navigateByUrl('customer-list'))
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
