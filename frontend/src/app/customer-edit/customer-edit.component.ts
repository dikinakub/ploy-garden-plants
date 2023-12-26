import { Component, OnInit, NgZone } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http'
import swal from 'sweetalert2';
import { ReplaySubject } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';

export interface IAddressList {
  id: string;
  nameTh: string;
}
export interface ITambonsList {
  tambonId: string;
  nameTh: string;
}
export interface AddressDTO {
  id: number;
  address: string;
}

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

  addressDataSource: any;
  public addressFilterCtrl: FormControl = new FormControl();
  public filteredAddress: ReplaySubject<AddressDTO[]> = new ReplaySubject<AddressDTO[]>();

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
        provincesId: res['provincesId'],
        amphuresId: res['amphuresId'],
        tambonsId: res['tambonsId'],
        zipCode: res['zipCode'],
        phoneNumber1: res['phoneNumber1'],
        phoneNumber2: res['phoneNumber2'],
      })
      // console.log(this.updateForm.value)
    })

    this.updateForm = this.formBuilder.group({
      profileID: [''],
      profileName: [{ value: '', disabled: true }],
      profileUrl: [''],
      addressName: [''],
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
      // console.log(res)
      this.addressDataSource = new MatTableDataSource(res);
      this.addressDataSource.filterPredicate = (data: AddressDTO, filter: string) => {
        return data.address.includes(filter);
      };
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

    this.updateForm.valueChanges.subscribe(() => {
      // เขต/อำเภอ
      if (this.updateForm.value.provincesId) {
        if (this.provincesSelected != this.updateForm.value.provincesId) {
          this.crudService.getAmphuresByProvincesId(this.updateForm.value.provincesId).subscribe(res => {
            // console.log(res)
            this.amphuresList = res;
            this.filteredamphures.next(res.slice());
          });
        }
        this.provincesSelected = this.updateForm.value.provincesId;
        this.amphuresFilterCtrl.valueChanges
          .subscribe(() => {
            this.searchAmphures(this.provincesSelected, this.amphuresFilterCtrl.value);
          });
      }
      // แขวง/ตำบล
      if (this.updateForm.value.amphuresId) {
        if (this.amphuresSelected != this.updateForm.value.amphuresId) {
          this.crudService.getTambonsByAmphureId(this.updateForm.value.amphuresId).subscribe(res => {
            // console.log(res)
            this.tambonsList = res;
            this.filteredtambons.next(res.slice());
          });
        }
        this.amphuresSelected = this.updateForm.value.amphuresId;
        // รหัสไปรษณีย์
        if (this.updateForm.value.tambonsId && this.tambonsSelected != this.updateForm.value.tambonsId) {
          this.crudService.getTambonsById(this.updateForm.value.tambonsId).subscribe(res => {
            // console.log(res)
            this.updateForm.controls['zipCode'].setValue(res['zipCode']);
          });
        }
        this.tambonsSelected = this.updateForm.value.tambonsId;

        this.tambonsFilterCtrl.valueChanges
          .subscribe(() => {
            this.searchTambons(this.amphuresSelected, this.tambonsFilterCtrl.value);
          });
      }
    });
  }

  searchAddress(key: String): any {
    this.addressDataSource.filter = key;
    this.filteredAddress.next(this.addressDataSource.filteredData);
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

  getSessionStorage() {
    return sessionStorage.getItem('BackToPage');
  }

}
