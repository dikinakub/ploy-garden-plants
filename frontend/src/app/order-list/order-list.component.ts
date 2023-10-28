import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { MatTableDataSource } from '@angular/material/table';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {

  displayedColumns: string[] = ['no', 'profileName', 'addressName', 'addressDetail', 'phoneNumber1', 'phoneNumber2', 'edit', 'delete'];
  getName: String = "";
  dataSource: any;

  constructor(private crudService: CrudService, private ngZone: NgZone, private router: Router) { }

  ngOnInit(): void {
    // this.crudService.getCustomers().subscribe(res => {
    //   console.log(res)
    //   this.dataSource = new MatTableDataSource(res);
    //   console.log(this.dataSource.data)
    // })
  }
}
