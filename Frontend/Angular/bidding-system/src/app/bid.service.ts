import { Injectable } from '@angular/core';
import {Bid} from './bid';
import { Socket } from 'ngx-socket-io';

@Injectable({
  providedIn: 'root'
})
export class BidService {

  currentBid = this.socket.fromEvent<Bid>('bid');
  bids = this.socket.fromEvent<string[]>('bids');

  constructor(private socket: Socket) { }

  bid(bid) {
    this.socket.emit('bid', bid);
    // Send request! And catch in a MQ
  }
}
