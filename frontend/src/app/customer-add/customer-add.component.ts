import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http'
import swal from 'sweetalert2';
import { ReplaySubject } from 'rxjs';

export interface IAddressList {
  id: string;
  nameTh: string;
}
export interface ITambonsList {
  tambonId: string;
  nameTh: string;
}

@Component({
  selector: 'app-customer-add',
  templateUrl: './customer-add.component.html',
  styleUrls: ['./customer-add.component.css']
})
export class CustomerAddComponent implements OnInit {

  addressList: any[] = [];
  public addressFilterCtrl: FormControl = new FormControl();
  public filteredAddress: ReplaySubject<IAddressList[]> = new ReplaySubject<IAddressList[]>();

  provincesList: any[] = [];
  public provincesFilterCtrl: FormControl = new FormControl();
  public filteredprovinces: ReplaySubject<IAddressList[]> = new ReplaySubject<IAddressList[]>();
  provincesSelected: any;

  amphuresList: any[] = [];
  public amphuresFilterCtrl: FormControl = new FormControl();
  public filteredamphures: ReplaySubject<IAddressList[]> = new ReplaySubject<IAddressList[]>();
  amphuresSelected: any;

  tambonsList: any[] = [];
  public tambonsFilterCtrl: FormControl = new FormControl();
  public filteredtambons: ReplaySubject<ITambonsList[]> = new ReplaySubject<ITambonsList[]>();
  tambonsSelected: any;

  customerForm = new FormGroup({
    facebookName: new FormControl(''),
    facebookUrl: new FormControl(''),
    name: new FormControl(''),
    addressDetail: new FormControl(''),
    provincesId: new FormControl(''),
    amphuresId: new FormControl(''),
    tambonsId: new FormControl(''),
    zipCode: new FormControl(''),
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
      provincesId: [''],
      amphuresId: [''],
      tambonsId: [''],
      zipCode: [''],
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

    // จังหวัด
    this.crudService.getProvincesAll().subscribe(res => {
      this.provincesList = res;
      // console.log(this.provincesList)
      this.filteredprovinces.next(res.slice());
      this.provincesFilterCtrl.valueChanges
        .subscribe(() => {
          this.searchProvinces(this.provincesFilterCtrl.value);
        });
    })

    this.customerForm.valueChanges.subscribe(() => {
      // เขต/อำเภอ
      if (this.customerForm.value.provincesId) {
        if (this.provincesSelected != this.customerForm.value.provincesId) {
          this.crudService.getAmphuresByProvincesId(this.customerForm.value.provincesId).subscribe(res => {
            // console.log(res)
            this.amphuresList = res;
            this.filteredamphures.next(res.slice());
          });
        }
        this.provincesSelected = this.customerForm.value.provincesId;
        this.amphuresFilterCtrl.valueChanges
          .subscribe(() => {
            this.searchAmphures(this.provincesSelected, this.amphuresFilterCtrl.value);
          });
      }

      // แขวง/ตำบล
      if (this.customerForm.value.amphuresId) {
        if (this.amphuresSelected != this.customerForm.value.amphuresId) {
          this.crudService.getTambonsByAmphureId(this.customerForm.value.amphuresId).subscribe(res => {
            // console.log(res)
            this.tambonsList = res;
            this.filteredtambons.next(res.slice());
          });
        }
        this.amphuresSelected = this.customerForm.value.amphuresId;

        // รหัสไปรษณีย์
        if (this.customerForm.value.tambonsId && this.tambonsSelected != this.customerForm.value.tambonsId) {
          this.crudService.getTambonsById(this.customerForm.value.tambonsId).subscribe(res => {
            // console.log(res)
            this.customerForm.controls['zipCode'].setValue(res['zipCode']);
          });
        }
        this.tambonsSelected = this.customerForm.value.tambonsId;

        this.tambonsFilterCtrl.valueChanges
          .subscribe(() => {
            this.searchTambons(this.amphuresSelected, this.tambonsFilterCtrl.value);
          });
      }
    });
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
  searchProvinces(key: String): any {
    if (key == null || key == "") {
      this.filteredprovinces.next(this.provincesList.slice());
    } else {
      this.crudService.getProvincesByKey(key).subscribe(res => {
        this.filteredprovinces.next(res.slice());
      })
    }
  }
  searchAmphures(provincesId: number, key: String): any {
    if (key == null || key == "") {
      this.filteredamphures.next(this.amphuresList.slice());
    } else {
      this.crudService.getAmphuresByProvincesIdAndNameTh(provincesId, key).subscribe(res => {
        this.filteredamphures.next(res.slice());
      })
    }
  }
  searchTambons(amphureId: number, key: String): any {
    if (key == null || key == "") {
      this.filteredtambons.next(this.tambonsList.slice());
    } else {
      this.crudService.getTambonsByAmphureIdAndNameTh(amphureId, key).subscribe(res => {
        this.filteredtambons.next(res.slice());
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
      provincesId: new FormControl(''),
      amphuresId: new FormControl(''),
      tambonsId: new FormControl(''),
      zipCode: new FormControl(''),
      phoneNumber1: new FormControl(''),
      phoneNumber2: new FormControl(''),
    })
  }

}
