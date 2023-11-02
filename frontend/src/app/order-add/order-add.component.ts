import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http'
import Swal from 'sweetalert2';

@Component({
  selector: 'app-order-add',
  templateUrl: './order-add.component.html',
  styleUrls: ['./order-add.component.css']
})
export class OrderAddComponent implements OnInit {

  orderForm = new FormGroup({
    customerName: new FormControl(''),
    address: new FormControl(''),
    // purchasePrice: new FormControl(''),
    // sellingPrice: new FormControl(''),
    // remaining: new FormControl(''),
    // type: new FormControl(''),
    // description: new FormControl(''),
  })

  constructor(
    private crudService: CrudService,
    private router: Router,
    private ngZone: NgZone,
    private formBuilder: FormBuilder,
    private http: HttpClient
  ) {
    this.orderForm = this.formBuilder.group({
      customerName: [''],
      address: [''],
      // purchasePrice: [''],
      // sellingPrice: [''],
      // remaining: [''],
      // type: [''],
      // description: [''],
    })
  }

  ngOnInit(): void { }
}
