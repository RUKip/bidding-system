import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Settings} from './settings';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {
  settings;
  http;
  constructor(http: HttpClient) {
    this.http = http;
    this.settings = new Settings();
  }

  requestProducts() {
    const url: string = 'http://' + this.settings.defaultUrl + '/api/products';
    return this.http.get(url);
  }
}
