import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http'

@Component({
  selector: 'app-customer-add',
  templateUrl: './customer-add.component.html',
  styleUrls: ['./customer-add.component.css']
})
export class CustomerAddComponent implements OnInit {

  // Node/Express API
  REST_API: string = 'http://localhost:8080/api';

  addressList: any;
  filteredaddressList: any[] = [];
  searchBoxTxt: any = "";

  customerForm = new FormGroup({
    facebookName: new FormControl(''),
    facebookUrl: new FormControl(''),
    name: new FormControl(''),
    addressDetail: new FormControl(''),
    address: new FormControl(''),
    phoneNumber1: new FormControl(''),
    phoneNumber2: new FormControl(''),
  })

  constructor(
    private crudService: CrudService,
    private router: Router,
    private ngZone: NgZone,
    private formBuilder: FormBuilder,
    private http: HttpClient
  ) {
    this.customerForm = this.formBuilder.group({
      facebookName: [''],
      facebookUrl: [''],
      name: [''],
      addressDetail: [''],
      address: [''],
      phoneNumber1: [''],
      phoneNumber2: [''],
    })
  }

  ngOnInit(): void {
    this.crudService.getAddressAll().subscribe(res => {
      this.addressList = res;
      this.filteredaddressList = this.addressList
      console.log(this.addressList)
    })
  }

  searchAddress(key: String): any {
    if (key == null || key == "") {
      this.filteredaddressList = this.addressList
    } else {
      this.crudService.getAddressByKey(key).subscribe(res => {
        this.filteredaddressList = res;
      })
    }
  }

  selectClear(): void {
    this.searchBoxTxt = "";
  }

  onSubmit(): any {
    let API_URL = `${this.REST_API}/customer/add`;
    return this.http.post(API_URL, this.customerForm.value)
      .subscribe(() => {
        console.log("Data customer added successfully.", this.customerForm.value);
        this.ngZone.run(() => this.router.navigateByUrl('customer-list'))
      }, (err) => {
        console.log(err)
      })
  }

  clearData(): void {
    this.customerForm = new FormGroup({
      facebookName: new FormControl(''),
      facebookUrl: new FormControl(''),
      name: new FormControl(''),
      addressDetail: new FormControl(''),
      address: new FormControl(''),
      phoneNumber1: new FormControl(''),
      phoneNumber2: new FormControl(''),
    })
  }

}
