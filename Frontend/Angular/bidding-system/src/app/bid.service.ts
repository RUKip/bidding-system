import { Injectable } from '@angular/core';
import {Bid} from './bid';
import { Socket } from 'ngx-socket-io';
import {Product} from './product';

@Injectable({
  providedIn: 'root'
})
export class BidService {

  currentProduct = this.socket.fromEvent<Product>('product');
  bids = this.socket.fromEvent<Bid[]>('bids');

  constructor(private socket: Socket) { }

  bid(bid) {
    this.socket.emit('bid', JSON.stringify(bid));
    // Send request! Backend posts it in MQ to share over replicas
  }
}
