import { Component, OnInit, NgZone } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http'
import swal from 'sweetalert2';

@Component({
  selector: 'app-customer-edit',
  templateUrl: './customer-edit.component.html',
  styleUrls: ['./customer-edit.component.css']
})
export class CustomerEditComponent implements OnInit {

  getId: any;
  updateForm: FormGroup;
  addressList: any;
  filteredaddressList: any[] = [];
  searchBoxTxt: any = "";

  constructor(
    private crudService: CrudService,
    private router: Router,
    private ngZone: NgZone,
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private activeatedRoute: ActivatedRoute
  ) {
    this.getId = this.activeatedRoute.snapshot.paramMap.get('profileID');
    this.crudService.getCustomer(this.getId).subscribe(res => {
      this.updateForm.setValue({
        profileID: res['profileID'],
        profileName: res['profileName'],
        profileUrl: res['profileUrl'],
        addressName: res['addressName'],
        addressDetail: res['addressDetail'],
        address: res['address'],
        phoneNumber1: res['phoneNumber1'],
        phoneNumber2: res['phoneNumber2'],
      })
      // console.log(this.updateForm.value)
    })

    this.updateForm = this.formBuilder.group({
      profileID: [''],
      profileName: [{value: '', disabled: true}],
      profileUrl: [''],
      addressName: [''],
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
      // console.log(this.addressList)
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

  onUpdate(): any {
    let API_URL = `${this.crudService.REST_API}/customer/update`;
    return this.http.post(API_URL, this.updateForm.value)
      .subscribe(() => {
        console.log("Update customer successfully.", this.updateForm.value);
        swal.fire('SUCCESS', 'Update customer successfully.', 'success');
        this.ngZone.run(() => this.router.navigateByUrl('customer-list'))
      }, (err) => {
        console.log(err)
        swal.fire('ERROR', err.error, 'warning');
      })
  }

  selectClear(): void {
    this.searchBoxTxt = "";
  }

  clearData(): void {
    this.updateForm = this.formBuilder.group({
      profileUrl: [''],
      addressDetail: [''],
      addressName: [''],
      address: [''],
      phoneNumber1: [''],
      phoneNumber2: [''],
    })
  }

}
