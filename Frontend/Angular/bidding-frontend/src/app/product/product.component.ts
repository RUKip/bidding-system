import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ProductService} from '../product.service';
import {Product} from '../product';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  bids = [];
  productId;
  product: Product;
  constructor(private route: ActivatedRoute, private productSerivce: ProductService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.productId = params.get('productId');
      console.log(this.productId);
    });

    this.productSerivce.getProductData(this.productId)
      .subscribe((data: Object) => {
        console.log(data);
        this.product = {
            id: data['id'],
            name: data['name'],
            description: data['description']
        };
      });

    //TODO: get Bids by id on start, from the web socket

  }

}
