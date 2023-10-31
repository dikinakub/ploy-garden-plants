import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from "@angular/router";
import { CrudService } from '../service/crud.service';
import { MatTableDataSource } from '@angular/material/table';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-stock-list',
  templateUrl: './stock-list.component.html',
  styleUrls: ['./stock-list.component.css']
})
export class StockListComponent implements OnInit {

  displayedColumns: string[] = ['no', 'stockName', 'stockPurchasePrice', 'stockSellingPrice', 'stockRemaining', 'stockDescription', 'edit', 'delete'];
  getName: String = "";
  dataSource: any;
  dataSourceBox: any;

  constructor(private crudService: CrudService, private ngZone: NgZone, private router: Router) { }

  ngOnInit(): void {
    this.crudService.getStockByType("TREE").subscribe(res => {
      this.dataSource = new MatTableDataSource(res);
      // console.log(this.dataSource.data)
    })

    this.crudService.getStockByType("BOX").subscribe(res => {
      this.dataSourceBox = new MatTableDataSource(res);
      // console.log(this.dataSource.data)
    })
  }

  onSearch(name: String): any {
    this.crudService.getStockByName(name).subscribe(res => {
      this.dataSource = new MatTableDataSource(res);
      // console.log(this.dataSource.data)
    })
  }

  clearData(): void {
    this.getName = "";
    this.crudService.getStockByType("TREE").subscribe(res => {
      // console.log(res)
      this.dataSource = new MatTableDataSource(res);
      // console.log(this.dataSource.data)
    })
  }

  onEdit(id: any) {
    this.ngZone.run(() => this.router.navigateByUrl('stock-edit'))
  }

  onDelete(id: any) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'This process is irreversible.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, go ahead.',
      cancelButtonText: 'No, let me think',
    }).then((result) => {
      if (result.value) {
        this.crudService.deleteStock(id).subscribe(() => {
          console.log("Stock removed successfully.");
          Swal.fire('Removed!', 'Stock removed successfully.', 'success');
          window.location.reload();
        })
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        this.ngZone.run(() => this.router.navigateByUrl('stock-list'))
      }
    });
  }
}
