import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Settings } from './settings';
import { Socket } from 'ngx-socket-io';
import {Bid} from './bid';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  http;
  settings;
  currentBid = this.socket.fromEvent<Bid>('bid');
  bids = this.socket.fromEvent<string[]>('bids');

  constructor(http: HttpClient, private socket: Socket) {
    this.http = http;
    this.settings = new Settings();
  }

  getProductData(id: string) {
    const params = new HttpParams().set('id', id);
    const url: string = 'http://' + this.settings.defaultUrl + '/api/product/' + id;
    return this.http.get(url);
  }

  createProduct(product: string) {
    const url: string = 'http://' + this.settings.defaultUrl + '/api/product/' + product;
    return this.http.post(url);
  }

  bid(bid) {
    this.socket.emit('bid', bid);
    // Send request! And catch in a MQ
  }
}
