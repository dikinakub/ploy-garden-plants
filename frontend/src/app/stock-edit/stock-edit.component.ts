import { Component, OnInit, NgZone } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http'
import Swal from 'sweetalert2';

@Component({
  selector: 'app-stock-edit',
  templateUrl: './stock-edit.component.html',
  styleUrls: ['./stock-edit.component.css']
})
export class StockEditComponent implements OnInit {
  // Node/Express API
  REST_API: string = 'http://localhost:8080/api';

  getId: any;
  updateForm: FormGroup;

  constructor(
    private crudService: CrudService,
    private router: Router,
    private ngZone: NgZone,
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private activeatedRoute: ActivatedRoute
  ) {
    this.getId = this.activeatedRoute.snapshot.paramMap.get('stockId');
    this.crudService.getStock(this.getId).subscribe(res => {
      // console.log(res);
      this.updateForm.setValue({
        stockId: res[0]['stockId'],
        name: res[0]['stockName'],
        purchasePrice: res[0]['stockPurchasePrice'],
        sellingPrice: res[0]['stockSellingPrice'],
        remaining: res[0]['stockRemaining'],
        type: res[0]['stockType'],
        description: res[0]['stockDescription'],
      })
    })

    this.updateForm = new FormGroup({
      stockId: new FormControl(''),
      name: new FormControl(''),
      purchasePrice: new FormControl(''),
      sellingPrice: new FormControl(''),
      remaining: new FormControl(''),
      type: new FormControl(''),
      description: new FormControl(''),
    })
  }

  ngOnInit(): void { }

  onUpdate(): any {
    let API_URL = `${this.REST_API}/stock/update`;
    return this.http.post(API_URL, this.updateForm.value)
      .subscribe(() => {
        console.log("Update stock successfully.", this.updateForm.value);
        Swal.fire('SUCCESS', 'Update stock successfully.', 'success');
        this.ngZone.run(() => this.router.navigateByUrl('stock-list'))
      }, (err) => {
        console.log(err)
        Swal.fire('ERROR', err.error, 'warning');
      })
  }

  clearData(): void {
    this.updateForm = new FormGroup({
      purchasePrice: new FormControl(''),
      sellingPrice: new FormControl(''),
      remaining: new FormControl(''),
      description: new FormControl(''),
    })
  }

}
