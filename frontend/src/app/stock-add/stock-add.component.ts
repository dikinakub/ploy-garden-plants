import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http'
import Swal from 'sweetalert2';

@Component({
  selector: 'app-stock-add',
  templateUrl: './stock-add.component.html',
  styleUrls: ['./stock-add.component.css']
})
export class StockAddComponent implements OnInit {
  // Node/Express API
  REST_API: string = 'http://localhost:8080/api';

  stockList: any;

  stockForm = new FormGroup({
    stockId: new FormControl(''),
    name: new FormControl(''),
    purchasePrice: new FormControl(''),
    sellingPrice: new FormControl(''),
    remaining: new FormControl(''),
    type: new FormControl(''),
    description: new FormControl(''),
  })

  constructor(
    private crudService: CrudService,
    private router: Router,
    private ngZone: NgZone,
    private formBuilder: FormBuilder,
    private http: HttpClient
  ) {
    this.stockForm = this.formBuilder.group({
      stockId: [''],
      name: [''],
      purchasePrice: [''],
      sellingPrice: [''],
      remaining: [''],
      type: [''],
      description: [''],
    })
  }

  ngOnInit(): void { }

  onSubmit(): any {
    let API_URL = `${this.REST_API}/stock/add`;
    return this.http.post(API_URL, this.stockForm.value)
      .subscribe(() => {
        console.log("Add stock successfully.", this.stockForm.value);
        Swal.fire('SUCCESS', 'Add stock successfully.', 'success');
        this.ngZone.run(() => this.router.navigateByUrl('stock-list'))
      }, (err) => {
        console.log(err)
        Swal.fire('ERROR', err.error, 'warning');
      })
  }

  clearData(): void {
    this.stockForm = new FormGroup({
      stockId: new FormControl(''),
      name: new FormControl(''),
      purchasePrice: new FormControl(''),
      sellingPrice: new FormControl(''),
      remaining: new FormControl(''),
      type: new FormControl(''),
      description: new FormControl(''),
    })
  }
}
