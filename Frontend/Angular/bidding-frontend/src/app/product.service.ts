import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  productId;
  http;
  constructor(http: HttpClient, productId: number) {
    this.http = http;
    this.productId = productId;
  }

  getProductData(id) {
    const params = new HttpParams().set('id', id);
      return this.http.get('http://localhost/api/product/get/${id}', {responseType: 'json', params});
  }

  bid(productId, price) {
    // Send request!
  }
}
