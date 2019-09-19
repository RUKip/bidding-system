import { Component, OnInit } from '@angular/core';
import {ProductsService} from '../products.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products = [];

  constructor(private productsSerivce: ProductsService) { }

  ngOnInit() {
    this.getProducts();
  }

  getProducts() {
    this.productsSerivce.requestProducts()
      .subscribe((data: Response) => this.products.push({
        productName: data.json()['name'],
        productDescription:  data.json()['description']
      }));
    console.log(this.products);
  }

}
