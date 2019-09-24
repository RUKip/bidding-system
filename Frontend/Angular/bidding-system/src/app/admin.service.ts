import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Settings} from './settings';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  http;
  settings;
  constructor(http: HttpClient) {
    this.http = http;
    this.settings = new Settings();
  }

  requestStats() {
    const url = this.settings.dockerSocket + '/v1.24/containers/json'; //curl --unix-socket /var/run/docker.sock http:/v1.24/containers/json
    return this.http.get(url);
  }


}
