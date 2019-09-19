import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.products = this.requestProducts();
  }

  requestProducts() {
    return this.http.get('backend/products/get');
  }

}
