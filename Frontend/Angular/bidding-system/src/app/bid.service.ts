import { Injectable } from '@angular/core';
import {Settings} from './settings';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BidService {

  private bids = new BehaviorSubject([]);
  currentBids = this.bids.asObservable();
  socket;
  settings: Settings;

  constructor() {
    this.settings = new Settings();
    this.socket = new WebSocket('ws://' + this.settings.defaultUrl + '/api/bid/');

    // Listen for messages
    this.socket.addEventListener('message', (event) => {
      const data = JSON.parse(event.data);
      console.log(data);
      const bids = data['bids'];
      let list = [];
      for (const bid of bids) {
        list.push(bid);
      }
      this.bids.next(list);
    });
  }

  bid(bid) {
    this.socket.send(JSON.stringify(bid));
  }
}
