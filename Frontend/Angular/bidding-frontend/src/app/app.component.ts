import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private http: HttpClient) { }
  title = 'bidding-frontend';

  productsUrl = 'backend/products/get';
  requestProducts() {
    return this.http.get(this.productsUrl);
  }

  bid(productId, price) {
    // Send request!
  }
}
