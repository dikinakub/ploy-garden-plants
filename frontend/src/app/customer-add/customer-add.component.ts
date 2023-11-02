import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http'
import swal from 'sweetalert2';
import { ReplaySubject } from 'rxjs';

export interface IAddressList {
  id: string;
  address: string;
}

@Component({
  selector: 'app-customer-add',
  templateUrl: './customer-add.component.html',
  styleUrls: ['./customer-add.component.css']
})
export class CustomerAddComponent implements OnInit {

  addressList: any[] = [];

  public addressCtrl: FormControl = new FormControl();
  public addressFilterCtrl: FormControl = new FormControl();
  public filteredAddress: ReplaySubject<IAddressList[]> = new ReplaySubject<IAddressList[]>(1);

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
    private http: HttpClient,
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
      // console.log(this.addressList)
      this.filteredAddress.next(res.slice());
      this.addressFilterCtrl.valueChanges
        .subscribe(() => {
          this.searchAddress(this.addressFilterCtrl.value);
        });
    })
  }

  searchAddress(key: String): any {
    if (key == null || key == "") {
      this.filteredAddress.next(this.addressList.slice());
    } else {
      this.crudService.getAddressByKey(key).subscribe(res => {
        this.filteredAddress.next(res.slice());
      })
    }
  }

  onSubmit(): any {
    let API_URL = `${this.crudService.REST_API}/customer/add`;
    return this.http.post(API_URL, this.customerForm.value)
      .subscribe(() => {
        console.log("Add customer successfully.", this.customerForm.value);
        swal.fire('SUCCESS', 'Add customer successfully.', 'success');
        this.ngZone.run(() => this.router.navigateByUrl('customer-list'))
      }, (err) => {
        console.log(err)
        swal.fire('ERROR', err.error, 'warning');
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
