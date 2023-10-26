import { Component, OnInit, NgZone } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder, FormControl, Form, Validators } from '@angular/forms';

@Component({
  selector: 'app-customer-add',
  templateUrl: './customer-add.component.html',
  styleUrls: ['./customer-add.component.css']
})
export class CustomerAddComponent implements OnInit {

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
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {
    // this.customerForm = this.formBuilder.group({
    //   facebookName: [''],
    //   facebookUrl: [''],
    //   name: [''],
    //   addressDetail: [''],
    //   address: [''],
    //   phoneNumber1: [''],
    //   phoneNumber2: [''],
    // })
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
    console.warn(this.customerForm.value);
    // this.crudService.addCustomer(this.customer.value).subscribe(res => {
    //   this.addressList = res;
    //   this.filteredaddressList = this.addressList
    //   console.log(this.addressList)
    // })

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
