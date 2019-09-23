import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Settings } from './settings';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  productId;
  http;
  settings;
  constructor(http: HttpClient, productId: number) {
    this.http = http;
    this.productId = productId;
    this.settings = new Settings();
  }

  getProductData(id) {
    const params = new HttpParams().set('id', id);
    const url: string = 'http://' + this.settings.defaultUrl + '/api/product/${id}';
    return this.http.get(url, {responseType: 'json', params});
  }

  bid(productId, price) {
    // Send request!
  }
}
