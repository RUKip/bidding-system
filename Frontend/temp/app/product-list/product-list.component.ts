import { Component, OnInit } from '@angular/core';
import {ProductsService} from '../products.service';
import {Product} from '../product';
import * as angular from 'angular';

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
      .subscribe((data: Object) => {
          this.products = [];
          console.log(data);
          angular.forEach(data, (value, key) => {
            const product: Product = {
              id: value['id'],
              name: value['name'],
              description: value['description']
            };
            this.products.push(product);
          });
        }
      );
  }

}
