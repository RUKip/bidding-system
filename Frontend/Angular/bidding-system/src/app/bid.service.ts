import { Injectable } from '@angular/core';
import {Bid} from './bid';
import {Settings} from './settings';

@Injectable({
  providedIn: 'root'
})
export class BidService {

  socket;
  bids: Bid[] = [];
  settings: Settings;

  constructor() {
    this.settings = new Settings();
    this.socket = new WebSocket('ws://' + this.settings.defaultUrl + '/api/bid/');

    // Listen for messages
    this.socket.addEventListener('message', function(event) {
      console.log(JSON.parse(event));
    });
  }

  bid(bid) {
    this.socket.send(JSON.stringify(bid));
  }
}
