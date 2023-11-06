import { Component } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';

  constructor(
    private router: Router,
    private activeatedRoute: ActivatedRoute
  ) { }

  onTabChanged(event: MatTabChangeEvent): void {
    switch (event.index) {
      case 0: // index of the tab
        // this is our stub tab for link
        this.router.navigate(['/order-add']);
        break;
      case 1:
        this.router.navigate(['/customer-list']);
        break;
      case 2:
        this.router.navigate(['/stock-list']);
        break;
    }
  }
}
