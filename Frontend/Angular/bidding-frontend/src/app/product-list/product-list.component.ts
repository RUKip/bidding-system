import { Component, OnInit } from '@angular/core';
import {ProductsService} from '../products.service';
import {Product} from '../product';

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
          this.products = []
          console.log(data);
          for (const entry of data['products']) {
            const product: Product = {
              id: entry['id'],
              name: entry['name'],
              description: entry['description']
            };
            this.products.push(product);
          }
        }
      );
  }

}
